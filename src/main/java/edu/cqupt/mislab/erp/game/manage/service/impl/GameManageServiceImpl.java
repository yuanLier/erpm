package edu.cqupt.mislab.erp.game.manage.service.impl;

import edu.cqupt.mislab.erp.commons.response.ResponseVo;
import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameInitInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.dto.GameCreateDto;
import edu.cqupt.mislab.erp.game.manage.model.dto.GamesSearchDto;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo.GameBasicInfoBuilder;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameInitInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameStatus;
import edu.cqupt.mislab.erp.game.manage.model.vo.GameDetailInfoVo;
import edu.cqupt.mislab.erp.game.manage.service.GameManageService;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo.UserStudentInfoBuilder;
import edu.cqupt.mislab.erp.user.model.entity.UserTeacherInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

import static edu.cqupt.mislab.erp.commons.response.ResponseUtil.toFailResponseVo;
import static edu.cqupt.mislab.erp.commons.response.ResponseUtil.toSuccessResponseVo;

@Service
public class GameManageServiceImpl implements GameManageService {

    @Autowired
    private GameInitInfoRepository gameInitInfoRepository;
    @Autowired
    private GameBasicInfoRepository gameBasicInfoRepository;
    @Autowired
    private UserStudentRepository userStudentRepository;

    @Override
    public ResponseVo<String> deleteOneGame(Long gameId,Long userId){

        final GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);

        if(gameBasicInfo.getGameCreator().getId().equals(userId)){

            if(gameBasicInfo.getGameStatus() == GameStatus.CREATE){

                gameBasicInfoRepository.delete(gameId);

                return toSuccessResponseVo("删除比赛成功");
            }

            return toFailResponseVo(HttpStatus.BAD_REQUEST,"只能删除处于创建状态的比赛");
        }

        return toFailResponseVo(HttpStatus.BAD_REQUEST,"只有比赛创建者可以删除比赛");
    }

    @Override
    public GameDetailInfoVo getOneGameDetailVo(Long gameId){

        final GameBasicInfo basicInfo = gameBasicInfoRepository.findOne(gameId);

        if(basicInfo != null){

            GameDetailInfoVo detailInfoVo = new GameDetailInfoVo();

            EntityVoUtil.copyFieldsFromEntityToVo(basicInfo,detailInfoVo);

            return detailInfoVo;
        }

        return null;
    }

    @Override
    public Example<GameBasicInfo> getGameBasicInfoExampleBySearchDto(GamesSearchDto searchDto){

        final GameBasicInfoBuilder infoBuilder = GameBasicInfo.builder();

        if(searchDto.getGameId() != null){

            infoBuilder.id(searchDto.getGameId());
        }

        if(searchDto.getGameName() != null){

            infoBuilder.gameName(searchDto.getGameName());
        }

        if(searchDto.getGameStatus() != null){

            infoBuilder.gameStatus(searchDto.getGameStatus());
        }

        final UserStudentInfoBuilder studentInfoBuilder = UserStudentInfo.builder();

        if(searchDto.getStudentId() != null){

            studentInfoBuilder.id(searchDto.getStudentId());
        }

        if(searchDto.getTeacherId() != null){

            studentInfoBuilder.userTeacherInfo(UserTeacherInfo.builder().id(searchDto.getTeacherId()).build());
        }

        final GameBasicInfo gameBasicInfo = infoBuilder.gameCreator(studentInfoBuilder.accountEnable(true).build()).build();

        final ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreNullValues();

        return Example.of(gameBasicInfo,exampleMatcher);
    }

    @Override
    public ResponseVo<GameDetailInfoVo> createNewGame(GameCreateDto createDto){

        //检查这个创建者是否真的存在，不存在是不行的
        UserStudentInfo userStudentInfo = userStudentRepository.findOne(createDto.getCreatorId());

        //获取最新版本的比赛初始化信息
        GameInitInfo gameInitInfo = gameInitInfoRepository.findTop1ByIdIsNotNullOrderByIdDesc();

        if(gameInitInfo == null){

            return toFailResponseVo(HttpStatus.INTERNAL_SERVER_ERROR,"应用初始化参数出错，请联系管理员");
        }

        //纠正企业的最大个数
        if(createDto.getMaxEnterpriseNumber() > gameInitInfo.getMaxEnterpriseNumber() || createDto.getMaxEnterpriseNumber() < 1){

            createDto.setMaxEnterpriseNumber(gameInitInfo.getMaxEnterpriseNumber());
        }

        //创建这个比赛实体
        GameBasicInfo gameBasicInfo = GameBasicInfo.builder()
                .gameCreator(userStudentInfo)
                .gameInitInfo(gameInitInfo)
                .gameName(createDto.getGameName())
                .gameMaxEnterpriseNumber(createDto.getMaxEnterpriseNumber())
                .gameCreateTime(new Date())
                .gameCurrentYear(1)//初始化为当年
                .gameStatus(GameStatus.CREATE)//比赛状态为创建状态
                .build();

        //持久化这个比赛信息
        gameBasicInfo = gameBasicInfoRepository.saveAndFlush(gameBasicInfo);

        GameDetailInfoVo gameDetailInfoVo = new GameDetailInfoVo();

        EntityVoUtil.copyFieldsFromEntityToVo(gameBasicInfo,gameDetailInfoVo);

        return toSuccessResponseVo(gameDetailInfoVo);
    }

    @Override
    public GamesSearchDto getGameDetailVosBySearchDto(GamesSearchDto gamesSearchDto){

        final Example<GameBasicInfo> example = getGameBasicInfoExampleBySearchDto(gamesSearchDto);

        long count = gameBasicInfoRepository.count(example);

        Integer pageSize = gamesSearchDto.getPageSize();

        //页面数的矫正
        pageSize = pageSize <= 0 ? 1 : pageSize;

        if(count == 0){

            //如果没有数据
            gamesSearchDto.setTotalMessage(0L);
            gamesSearchDto.setConcurrentPage(1);
            gamesSearchDto.setPageData(null);

        }else {

            //如果有数据
            gamesSearchDto.setTotalMessage(count);

            Integer concurrentPage = gamesSearchDto.getConcurrentPage();

            //进行下标矫正
            int start = (concurrentPage - 1) * pageSize;

            start = start < 0 ? 0 : start;

            //只有一页
            if(pageSize >= count){

                final List<GameBasicInfo> basicInfos = gameBasicInfoRepository.findAll(example);

                List<GameDetailInfoVo> detailInfoVos = new ArrayList<>();

                basicInfos.forEach(gameBasicInfo -> {

                    GameDetailInfoVo detailInfoVo = new GameDetailInfoVo();

                    EntityVoUtil.copyFieldsFromEntityToVo(gameBasicInfo,detailInfoVo);

                    detailInfoVos.add(detailInfoVo);
                });

                gamesSearchDto.setPageData(detailInfoVos);

                gamesSearchDto.setConcurrentPage(1);
            }else {

                if(concurrentPage <= 0){
                    concurrentPage = 1;
                }

                //进行页数矫正
                while(start >= count && concurrentPage > 0){
                    concurrentPage --;
                    start = (concurrentPage - 1) * pageSize;
                }

                final Page<GameBasicInfo> infos = gameBasicInfoRepository.findAll(example,
                        new PageRequest(concurrentPage-1,pageSize,
                                new Sort(new Sort.Order(Direction.DESC,"gameCreateTime"))));

                gamesSearchDto.setConcurrentPage(concurrentPage);

                final Iterator<GameBasicInfo> iterator = infos.iterator();

                if(iterator.hasNext()){

                    List<GameDetailInfoVo> detailInfoVos = new ArrayList<>();

                    while(iterator.hasNext()){

                        GameDetailInfoVo detailInfoVo = new GameDetailInfoVo();

                        EntityVoUtil.copyFieldsFromEntityToVo(iterator.next(),detailInfoVo);

                        detailInfoVos.add(detailInfoVo);
                    }

                    gamesSearchDto.setPageData(detailInfoVos);
                }else {

                    gamesSearchDto.setPageData(null);
                }
            }
        }

        return gamesSearchDto;
    }
}
