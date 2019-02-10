package edu.cqupt.mislab.erp.game.manage.service.impl;

import edu.cqupt.mislab.erp.commons.response.ResponseVo;
import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.game.compete.init.GameCompeteInitService;
import edu.cqupt.mislab.erp.game.manage.constant.ManageConstant;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameInitInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.dto.GameCreateDto;
import edu.cqupt.mislab.erp.game.manage.model.dto.GamesSearchDto;
import edu.cqupt.mislab.erp.game.manage.model.entity.*;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo.GameBasicInfoBuilder;
import edu.cqupt.mislab.erp.game.manage.model.vo.GameDetailInfoVo;
import edu.cqupt.mislab.erp.game.manage.service.GameManageService;
import edu.cqupt.mislab.erp.game.manage.websocket.WebSocketMessagePublisher;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo.UserStudentInfoBuilder;
import edu.cqupt.mislab.erp.user.model.entity.UserTeacherInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

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
    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired
    private GameCompeteInitService gameCompeteInitService;
    @Autowired
    private WebSocketMessagePublisher webSocketMessagePublisher;

    @Override
    public ResponseVo<String> deleteOneGame(Long gameId,Long userId){

        //校验保证这个比赛信息存在
        final GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);

        //防止同一个用户很接近的两个请求
        if(gameBasicInfo == null){

            return toSuccessResponseVo("已经被先一步删除了！");
        }

        //只有比赛的创建者才可以删除一个比赛，两个ID都是一定存在的
        if(gameBasicInfo.getGameCreator().getId().equals(userId)){

            //只有处于创建状态的比赛才能够被删除
            if(gameBasicInfo.getGameStatus() == GameStatus.CREATE){

                //这个地方如果所对应的数据不存在会报异常，不过不影响结果
                try{
                    gameBasicInfoRepository.delete(gameId);

                    //通知前端该比赛被删除
                    webSocketMessagePublisher.publish(gameId,new TextMessage(ManageConstant.GAME_DELETE_KEY_NAME + gameBasicInfo));
                }catch(Exception e){
                    e.printStackTrace();
                }

                return toSuccessResponseVo("删除比赛成功");
            }

            return toFailResponseVo(HttpStatus.BAD_REQUEST,"只能删除处于创建状态的比赛");
        }

        return toFailResponseVo(HttpStatus.BAD_REQUEST,"只有比赛创建者可以删除比赛");
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
    public ResponseVo<String> beginOneGame(Long userId,Long gameId){

        final GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);

        if(gameBasicInfo != null){

            if(gameBasicInfo.getGameStatus() == GameStatus.CREATE){

                if(gameId.equals(gameBasicInfo.getGameCreator().getId())){

                    final List<EnterpriseBasicInfo> enterpriseBasicInfos = enterpriseBasicInfoRepository.findByGameInfo_Id(gameId);

                    //如果这个比赛一个企业都没有也无法开启比赛
                    if(enterpriseBasicInfos == null || enterpriseBasicInfos.size() == 0){

                        return toFailResponseVo(HttpStatus.BAD_REQUEST,"该比赛还没有一个企业，无法开始比赛");
                    }

                    boolean existsCreate = enterpriseBasicInfoRepository.existsByGameInfo_IdAndEnterpriseStatus(gameId,EnterpriseStatus.CREATE);

                    if(existsCreate){

                        return toFailResponseVo(HttpStatus.BAD_REQUEST,"还有企业没有准备完毕，无法开始比赛");
                    }

                    //比赛正在初始化
                    gameBasicInfo.setGameStatus(GameStatus.INITIALING);
                    gameBasicInfoRepository.save(gameBasicInfo);

                    //初始化竞赛信息
                    gameCompeteInitService.gameInit(gameId);

                    //比赛初始化完成
                    gameBasicInfo.setGameStatus(GameStatus.PLAYING);
                    gameBasicInfoRepository.save(gameBasicInfo);

                    return toSuccessResponseVo("比赛初始化成功，请开启你的比赛！");
                }

                return toFailResponseVo(HttpStatus.BAD_REQUEST,"只有比赛创建者才可以开启这个比赛");
            }

            return toFailResponseVo(HttpStatus.BAD_REQUEST,"只有创建状态的比赛才可以进行开启");
        }

        return toFailResponseVo(HttpStatus.BAD_REQUEST,"比赛不存在");
    }

    @Override
    public ResponseVo<GameDetailInfoVo> createNewGame(GameCreateDto createDto){

        //校验保证该条数据存在却不保证一定被审核通过，所以需要确认该用户已经被审核通过
        final UserStudentInfo userStudentInfo = userStudentRepository.findByIdAndAccountEnable(createDto.getCreatorId(),true);

        if(userStudentInfo == null){

            return toFailResponseVo(HttpStatus.BAD_REQUEST,"该用户还没有审核通过，无法创建比赛");
        }

        //获取最新版本的比赛初始化信息，这个方法保证管理员将应用初始化更改后会响应在后面创建的比赛上
        GameInitInfo gameInitInfo = gameInitInfoRepository.findTop1ByIdIsNotNullOrderByIdDesc();

        if(gameInitInfo == null){

            return toFailResponseVo(HttpStatus.INTERNAL_SERVER_ERROR,"应用比赛初始化信息出错，请联系管理员");
        }

        //纠正企业的最大个数，企业的最大个数在1-管理员设置的最大值之间，验证保证这个值最小为1
        Integer maxEnterpriseNumber = createDto.getMaxEnterpriseNumber();

        if(maxEnterpriseNumber > gameInitInfo.getMaxEnterpriseNumber()){

            maxEnterpriseNumber = gameInitInfo.getMaxEnterpriseNumber();
        }

        //创建这个比赛实体
        GameBasicInfo gameBasicInfo = GameBasicInfo.builder()
                .gameCreator(userStudentInfo)
                .gameInitInfo(gameInitInfo)
                .gameName(createDto.getGameName())
                .gameMaxEnterpriseNumber(maxEnterpriseNumber)
                .gameCreateTime(new Date())
                .gameCurrentYear(1)//初始化为当年
                .gameStatus(GameStatus.CREATE)//比赛状态为创建状态
                .build();

        try{
            //持久化这个比赛信息，这一步有可能会出现异常
            gameBasicInfo = gameBasicInfoRepository.saveAndFlush(gameBasicInfo);

            GameDetailInfoVo gameDetailInfoVo = new GameDetailInfoVo();

            EntityVoUtil.copyFieldsFromEntityToVo(gameBasicInfo,gameDetailInfoVo);

            return toSuccessResponseVo(gameDetailInfoVo);

        }catch(Exception e){
            e.printStackTrace();
        }

        return toFailResponseVo(HttpStatus.INTERNAL_SERVER_ERROR,"服务器内部持久化失败！");
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

                //放在只有一条数据还被人删除了的情况
                if(basicInfos != null){

                    basicInfos.forEach(gameBasicInfo -> {

                        GameDetailInfoVo detailInfoVo = new GameDetailInfoVo();

                        EntityVoUtil.copyFieldsFromEntityToVo(gameBasicInfo,detailInfoVo);

                        detailInfoVos.add(detailInfoVo);
                    });
                }

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

                //查询比赛信息，进行分页查询，按照创建时间排序，也就是说后创建的在最前面
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
