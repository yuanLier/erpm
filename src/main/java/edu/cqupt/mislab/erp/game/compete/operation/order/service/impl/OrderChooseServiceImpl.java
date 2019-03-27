package edu.cqupt.mislab.erp.game.compete.operation.order.service.impl;

import edu.cqupt.mislab.erp.commons.websocket.CommonWebSocketMessagePublisher;
import edu.cqupt.mislab.erp.game.compete.operation.order.dao.EnterpriseAdInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.dao.GameOrderChooseInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.service.OrderChooseService;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderChooseServiceImpl implements OrderChooseService {

    @Autowired
    private CommonWebSocketMessagePublisher messagePublisher;
    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired
    private GameBasicInfoRepository gameBasicInfoRepository;
    @Autowired
    private EnterpriseAdInfoRepository enterpriseAdInfoRepository;
    @Autowired
    private GameOrderChooseInfoRepository gameOrderChooseInfoRepository;

//    /**
//     * 初始化一个比赛的某一年的订单选择顺序信息，主要是需要进行各种条件的检测
//     * @param gameId：那一场比赛
//     * @return ：是否初始化成功
//     */
//    public boolean initGameChooseOrder(long gameId){
//
//        //1、检查是否所有的企业的都已经推进到指定的周期
//        //获取比赛的基本信息
//        final GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);
//
//        //只有运行时比赛才有可能进行订单选择
//        if(gameBasicInfo == null || gameBasicInfo.getGameStatus() != GameStatus.PLAYING){
//            return false;
//        }
//
//        //获取比赛的初始化信息：每一年多少个周期
//        final Integer period = gameBasicInfo.getGameInitInfo().getPeriod();
//
//        //获取当前比赛是那一年
//        final Integer gameCurrentYear = gameBasicInfo.getGameCurrentYear();
//
//        //计算当前企业如果要进行订单选择的话应该处于第几个周期
//        int gameCurrentPeriod = period * gameCurrentYear;
//
//        //查看是否是所有的在企业都已经推进到了指定周期
//        final List<EnterpriseBasicInfo> enterpriseBasicInfosOfNoCurrentPeriod = enterpriseBasicInfoRepository.findByGameInfo_IdAndEnterpriseCurrentPeriodIsNot(gameId,gameCurrentPeriod);
//
//        //只有所有的企业都推进到指定的周期才可以继续进行操作
//        if(enterpriseBasicInfosOfNoCurrentPeriod != null && enterpriseBasicInfosOfNoCurrentPeriod.size() != 0){
//            return false;
//        }
//
//        //2、检查所有需要投广告的企业都已经投放完毕了
//        //选取所有想投广告却还没有投广告的企业
//        final List<EnterpriseBasicInfo> enterpriseBasicInfos = enterpriseBasicInfoRepository.findByGameInfo_IdAndAdvertisingIsTrueAndAdvertisingCostIsFalse(gameId);
//
//        //只有所有的企业都已经投完广告才可以继续
//        if(enterpriseBasicInfos != null && enterpriseBasicInfos.size() != 0){
//
//            return false;
//        }
//
//        //3、查看该比赛的数据是否已经被初始化过了
//        if(gameOrderChooseInfoRepository.existsByOrderModelInfoBasicInfo_GameBasicInfo_IdAndFinishedIsFalse(gameId)){
//            return false;
//        }
//
//        //4、初始化这个比赛的订单信息
//
//
//
//        return false;
//    }

    //对所有的订单按照规则进行轮次排序
    public void test3(){

        return;
    }
    //刷新下一轮的排序
    public void test4(){


    }
}
