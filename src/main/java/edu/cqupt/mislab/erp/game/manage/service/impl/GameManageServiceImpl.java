package edu.cqupt.mislab.erp.game.manage.service.impl;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.response.WebResponseVo.ResponseStatus;
import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.commons.websocket.CommonWebSocketMessagePublisher;
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
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo.UserStudentInfoBuilder;
import edu.cqupt.mislab.erp.user.model.entity.UserTeacherInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.util.*;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.*;

@Service
public class GameManageServiceImpl implements GameManageService {

    @Autowired private GameInitInfoRepository gameInitInfoRepository;
    @Autowired private GameBasicInfoRepository gameBasicInfoRepository;
    @Autowired private UserStudentRepository userStudentRepository;
    @Autowired private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired private GameCompeteInitService gameCompeteInitService;
    @Autowired private CommonWebSocketMessagePublisher webSocketMessagePublisher;

    @Override
    public WebResponseVo<Object> deleteOneGame(Long gameId,Long userId,String password){

        //校验保证这个比赛信息存在
        final GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);

        //如果这个比赛本来就不存在就算是删除成功吧，主要是防止两次请求导致数据库报错
        if(gameBasicInfo == null){

            return toSuccessResponseVoWithNoData();
        }

        //只有比赛的创建者才可以删除一个比赛，两个ID都是一定存在的
        if(gameBasicInfo.getGameCreator().getId().equals(userId)){

            //查询该用户的数据，该数据肯定存在，因为校验过的，且用户无法被删除
            final UserStudentInfo userStudentInfo = userStudentRepository.findByIdAndAccountEnable(userId,true);

            //校验用户密码的准确性
            if(!userStudentInfo.getStudentPassword().equals(password)){

                return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"账户密码错误，无法删除该比赛");
            }

            //只有处于创建状态的比赛才能够被删除
            if(gameBasicInfo.getGameStatus() == GameStatus.CREATE){

                //这个地方如果所对应的数据不存在会报异常，不过不影响结果
                try{
                    gameBasicInfoRepository.delete(gameId);
                }catch(Exception e){
                    e.printStackTrace();
                }finally{
                    //通知前端该比赛被删除：格式为提示符 + 比赛ID
                    webSocketMessagePublisher.publish(gameId,new TextMessage(ManageConstant.GAME_DELETE_KEY_NAME + gameBasicInfo.getId()));
                }

                return toSuccessResponseVoWithNoData();
            }

            return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"只能删除处于创建状态的比赛");
        }

        return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"只有比赛创建者可以删除比赛");
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
    public WebResponseVo<String> beginOneGame(Long userId,Long gameId){

        final GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);

        if(gameBasicInfo != null){

            //只有处于创建状态的企业才能够进行开始操作
            if(gameBasicInfo.getGameStatus() == GameStatus.CREATE){

                //只有创建者才可以开始比赛
                if(gameId.equals(gameBasicInfo.getGameCreator().getId())){

                    final List<EnterpriseBasicInfo> enterpriseBasicInfos = enterpriseBasicInfoRepository.findByGameInfo_Id(gameId);

                    //如果这个比赛一个企业都没有也无法开启比赛
                    if(enterpriseBasicInfos == null || enterpriseBasicInfos.size() == 0){

                        return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"该比赛还没有一个企业，无法开始比赛");
                    }

                    //判断该比赛的企业里面是否还有处于创建状态没有进行确认的企业
                    boolean existsCreate = enterpriseBasicInfoRepository.existsByGameInfo_IdAndEnterpriseStatus(gameId,EnterpriseStatus.CREATE);

                    //还有企业没有准备完成将无法开始比赛
                    if(existsCreate){

                        return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"还有企业没有准备完毕，无法开始比赛");
                    }

                    //比赛正在初始化，这个阶段的比赛信息前端将无法获取
                    gameBasicInfo.setGameStatus(GameStatus.INITIALING);

                    //通知前端该比赛已经开始初始化
                    webSocketMessagePublisher.publish(gameId,new TextMessage(ManageConstant.GAME_TO_INIT_KEY_NAME + gameId));

                    //存储这个比赛信息
                    gameBasicInfoRepository.save(gameBasicInfo);

                    //初始化竞赛信息，这个阶段会比较消耗时间
                    final boolean init = gameCompeteInitService.gameInit(gameId);

                    //初始化失败
                    if(!init){

                        return toFailResponseVoWithMessage(ResponseStatus.INTERNAL_SERVER_ERROR,"比赛初始化失败，请联系管理员");
                    }

                    //比赛初始化完成
                    gameBasicInfo.setGameStatus(GameStatus.PLAYING);

                    //向前端广播这个比赛已经初始化完成的信息
                    webSocketMessagePublisher.publish(gameId,new TextMessage(ManageConstant.GAME_INIT_COMPLETE + gameId));

                    //响应比赛创建者信息
                    return toSuccessResponseVoWithNoData();
                }

                return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"只有比赛创建者才可以开启这个比赛");
            }

            return toFailResponseVoWithMessage(ResponseStatus.BAD_REQUEST,"只有创建状态的比赛才可以进行开启");
        }

        return toFailResponseVoWithMessage(ResponseStatus.NOT_FOUND,"比赛不存在");
    }

    @Override
    public WebResponseVo<GameDetailInfoVo> createNewGame(GameCreateDto createDto){

        //校验保证该条数据存在却不保证一定被审核通过，所以需要确认该用户已经被审核通过
        final UserStudentInfo userStudentInfo = userStudentRepository.findByIdAndAccountEnable(createDto.getCreatorId(),true);

        //校验用户是否能够创建比赛
        if(userStudentInfo == null){

            return toFailResponseVoWithMessage(ResponseStatus.BAD_REQUEST,"该用户还没有审核通过或未注册，无法创建比赛");
        }

        //获取最新版本的比赛初始化信息，这个方法保证管理员将应用初始化更改后会响应在后面创建的比赛上
        GameInitInfo gameInitInfo = gameInitInfoRepository.findTop1ByIdIsNotNullOrderByIdDesc();

        //校验比赛模块初始化数据是否正确
        if(gameInitInfo == null){

            return toFailResponseVoWithMessage(ResponseStatus.INTERNAL_SERVER_ERROR,"应用比赛初始化信息出错，请联系管理员");
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
            //持久化这个比赛信息，必须要立马持久化到数据库里面去防止意外
            gameBasicInfo = gameBasicInfoRepository.saveAndFlush(gameBasicInfo);

            GameDetailInfoVo gameDetailInfoVo = new GameDetailInfoVo();

            EntityVoUtil.copyFieldsFromEntityToVo(gameBasicInfo,gameDetailInfoVo);

            return toSuccessResponseVoWithData(gameDetailInfoVo);
        }catch(Exception e){
            e.printStackTrace();
        }

        return toFailResponseVoWithMessage(ResponseStatus.INTERNAL_SERVER_ERROR,"服务器内部持久化失败！");
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
