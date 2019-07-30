package edu.cqupt.mislab.erp.game.manage.service.impl;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.response.WebResponseVo.ResponseStatus;
import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.commons.websocket.CommonWebSocketMessagePublisher;
import edu.cqupt.mislab.erp.game.compete.basic.impl.GameModelInitService;
import edu.cqupt.mislab.erp.game.manage.constant.ManageConstant;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseMemberInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameInitInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.dto.GameCreateDto;
import edu.cqupt.mislab.erp.game.manage.model.dto.GamesSearchDto;
import edu.cqupt.mislab.erp.game.manage.model.entity.*;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo.GameBasicInfoBuilder;
import edu.cqupt.mislab.erp.game.manage.model.vo.GameDetailInfoVo;
import edu.cqupt.mislab.erp.game.manage.model.vo.GameDetailPageVo;
import edu.cqupt.mislab.erp.game.manage.service.GameManageService;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo.UserStudentInfoBuilder;
import edu.cqupt.mislab.erp.user.model.entity.UserTeacherInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import java.util.*;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.*;

/**
 * @author chuyunfei
 * @description
 * @date 22:38 2019/4/26
 **/

@Service
public class GameManageServiceImpl implements GameManageService {

    @Autowired private GameInitInfoRepository gameInitInfoRepository;
    @Autowired private GameBasicInfoRepository gameBasicInfoRepository;
    @Autowired private UserStudentRepository userStudentRepository;
    @Autowired private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired private EnterpriseMemberInfoRepository enterpriseMemberInfoRepository;
    @Autowired private GameModelInitService gameCompeteInitService;
    @Autowired private CommonWebSocketMessagePublisher webSocketMessagePublisher;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public WebResponseVo<Object> deleteOneGame(Long gameId,Long userId,String password){

        //校验保证这个比赛信息存在且状态正确
        final GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);

        //只有比赛的创建者才可以删除一个比赛，两个ID都是一定存在的
        if(!gameBasicInfo.getUserStudentInfo().getId().equals(userId)){

            return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"只有比赛创建者可以删除比赛");
        }

        //校验保证用户数据存在且状态合适
        final UserStudentInfo userStudentInfo = userStudentRepository.findOne(userId);

        //校验用户密码的准确性
        if(!userStudentInfo.getStudentPassword().equals(password)){

            return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"账户密码错误，无法删除该比赛");
        }

        //删除这个比赛信息
        gameBasicInfoRepository.delete(gameId);

        //通知前端该比赛被删除：格式为提示符 + 比赛ID
        webSocketMessagePublisher.publish(gameId,new TextMessage(ManageConstant.GAME_DELETE_KEY_NAME + gameBasicInfo.getId()));

        return toSuccessResponseVoWithNoData();
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

        if(searchDto.getGameStatusEnum() != null){

            infoBuilder.gameStatus(searchDto.getGameStatusEnum());
        }

        final UserStudentInfoBuilder studentInfoBuilder = UserStudentInfo.builder();

        if(searchDto.getStudentId() != null){

            studentInfoBuilder.id(searchDto.getStudentId());
        }

        if(searchDto.getTeacherId() != null){

            studentInfoBuilder.userTeacherInfo(UserTeacherInfo.builder().id(searchDto.getTeacherId()).build());
        }

        final GameBasicInfo gameBasicInfo = infoBuilder.userStudentInfo(studentInfoBuilder.accountEnable(true).build()).build();

        final ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreNullValues();

        return Example.of(gameBasicInfo,exampleMatcher);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WebResponseVo<String> beginOneGame(Long userId,Long gameId){

        //校验保证信息准确性
        final GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);

        //只有创建者才可以开始比赛
        if(userId.equals(gameBasicInfo.getUserStudentInfo().getId())){

            //这里不能使用 gameBasicInfo 对象获取，因为无法感知后面创建的企业信息
            final List<EnterpriseBasicInfo> enterpriseBasicInfos = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId);

            //如果这个比赛一个企业都没有也无法开启比赛
            if(enterpriseBasicInfos.size() == 0){

                return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"该比赛还没有一个企业，无法开始比赛");
            }

            //判断该比赛的企业里面是否还有处于创建状态没有进行确认的企业
            boolean existsCreate = enterpriseBasicInfoRepository.existsByGameBasicInfo_IdAndEnterpriseStatus(gameId,EnterpriseStatusEnum.CREATE);

            //还有企业没有准备完成将无法开始比赛
            if(existsCreate){

                return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"还有企业没有准备完毕，无法开始比赛");
            }

            //比赛正在初始化，这个阶段的比赛信息前端将无法获取
            gameBasicInfo.setGameStatus(GameStatusEnum.INITIALING);

            //通知前端该比赛已经开始初始化
            webSocketMessagePublisher.publish(gameId,new TextMessage(ManageConstant.GAME_TO_INIT_KEY_NAME + gameId));

            //存储这个比赛信息
            gameBasicInfoRepository.save(gameBasicInfo);

            //初始化竞赛信息，这个阶段会比较消耗时间，这个阶段不允许出错，出错后比赛状态将被卡在初始化 ing，正好前端也无法获取这个状态的比赛信息
            final List<String> stringList = gameCompeteInitService.initGameModel(gameId);

            //初始化失败
            if(stringList != null){

                return toFailResponseVoWithMessage(ResponseStatus.INTERNAL_SERVER_ERROR,"比赛初始化失败，请联系管理员");
            }

            //比赛初始化完成
            gameBasicInfo.setGameStatus(GameStatusEnum.PLAYING);

            //存储这个比赛信息
            gameBasicInfoRepository.save(gameBasicInfo);

            //更改企业状态信息为比赛中
            enterpriseBasicInfos.forEach(enterpriseBasicInfo -> enterpriseBasicInfo.setEnterpriseStatus(EnterpriseStatusEnum.PLAYING));

            enterpriseBasicInfoRepository.save(enterpriseBasicInfos);
            //更改比赛信息完成

            //向前端广播这个比赛已经初始化完成的信息
            webSocketMessagePublisher.publish(gameId,new TextMessage(ManageConstant.GAME_INIT_COMPLETE + gameId));

            // 通知前端新的一年开始了
            webSocketMessagePublisher.publish(gameId, new TextMessage(ManageConstant.NEW_YEAR + gameBasicInfo.getGameCurrentYear()));
            // 通知前端订单会开始了
            webSocketMessagePublisher.publish(gameId, new TextMessage(ManageConstant.ORDER_MEETING_BEGIN + gameId));

            //响应比赛创建者信息
            return toSuccessResponseVoWithNoData();
        }

        return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"只有比赛创建者可以开始比赛");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WebResponseVo<GameDetailInfoVo> createNewGame(GameCreateDto createDto){

        //查出这条数据，校验保证其已经审核通过
        final UserStudentInfo userStudentInfo = userStudentRepository.findOne(createDto.getCreatorId());

        //获取最新版本的比赛初始化信息，这个方法保证管理员将应用初始化更改后会响应在后面创建的比赛上
        GameInitBasicInfo gameInitBasicInfo = gameInitInfoRepository.findBySettingEnableIsTrue();

        //校验比赛模块初始化数据是否正确，这里是必须要进行判断的，不然可能会出现不一致的数据
        if(gameInitBasicInfo == null){

            return toFailResponseVoWithMessage(ResponseStatus.INTERNAL_SERVER_ERROR,"应用比赛初始化信息出错，请联系管理员");
        }

        //纠正企业的最大个数，企业的最大个数在1-管理员设置的最大值之间，验证保证这个值最小为1
        Integer maxEnterpriseNumber = createDto.getMaxEnterpriseNumber();

        if(maxEnterpriseNumber > gameInitBasicInfo.getMaxEnterpriseNumber()){

            maxEnterpriseNumber = gameInitBasicInfo.getMaxEnterpriseNumber();
        }

        //创建这个比赛实体
        GameBasicInfo gameBasicInfo = GameBasicInfo.builder()
                .userStudentInfo(userStudentInfo)
                .gameInitBasicInfo(gameInitBasicInfo)
                .gameName(createDto.getGameName())
                .gameMaxEnterpriseNumber(maxEnterpriseNumber)
                .gameCreateTime(new Date())
                //初始化为当年
                .gameCurrentYear(1)
                //比赛状态为创建状态
                .gameStatus(GameStatusEnum.CREATE)
                .build();

        //持久化这个比赛信息，必须要立马持久化到数据库里面去防止意外
        gameBasicInfo = gameBasicInfoRepository.save(gameBasicInfo);

        GameDetailInfoVo gameDetailInfoVo = new GameDetailInfoVo();

        EntityVoUtil.copyFieldsFromEntityToVo(gameBasicInfo,gameDetailInfoVo);

        return toSuccessResponseVoWithData(gameDetailInfoVo);
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

                //防止只有一条数据还被人删除了的情况
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

    @Override
    public GameDetailPageVo getGamesOfUser(Long userId, GameStatusEnum gameStatus, Integer currentPage, Integer amountOfPage) {

        GameDetailPageVo gameDetailPageVo = new GameDetailPageVo();

        // 获取该用户全部参与过的比赛企业信息
        List<EnterpriseMemberInfo> enterpriseMemberInfoList = enterpriseMemberInfoRepository.findByUserStudentInfo_Id(userId);
        // 获取该用户创建的全部比赛信息
        List<GameBasicInfo> gameBasicInfoList = gameBasicInfoRepository.findByUserStudentInfo_Id(userId);

        // 整理出用户参与的全部比赛
        Set<GameBasicInfo> gameBasicInfoSet = new HashSet<>(gameBasicInfoList);
        for(EnterpriseMemberInfo enterpriseMemberInfo : enterpriseMemberInfoList) {
            gameBasicInfoSet.add(enterpriseMemberInfo.getEnterpriseBasicInfo().getGameBasicInfo());
        }

        List<GameDetailInfoVo> gameDetailInfoVoList = new ArrayList<>();
        for(GameBasicInfo gameBasicInfo : gameBasicInfoSet) {

            // 当该企业所在比赛的比赛状态与要查询的状态相同时，加入集合
            if(gameStatus.equals(gameBasicInfo.getGameStatus())) {
                GameDetailInfoVo gameDetailInfoVo = new GameDetailInfoVo();
                EntityVoUtil.copyFieldsFromEntityToVo(gameBasicInfo, gameDetailInfoVo);

                gameDetailInfoVoList.add(gameDetailInfoVo);
            }
        }

        Integer size = gameDetailInfoVoList.size();
        Integer begin = (currentPage-1)*amountOfPage;
        if(begin > size) {
            return null;
        }
        Integer end = Math.min(currentPage*amountOfPage, size);

        gameDetailPageVo.setCurrentPage(currentPage);
        gameDetailPageVo.setTotalAmount(size);
        gameDetailPageVo.setGameDetailInfoVo(gameDetailInfoVoList.subList(begin, end));

        return gameDetailPageVo;
    }
}
