package edu.cqupt.mislab.erp.game.compete.operation.order.advance;

import edu.cqupt.mislab.erp.commons.constant.FinanceOperationConstant;
import edu.cqupt.mislab.erp.game.compete.basic.ModelAdvance;
import edu.cqupt.mislab.erp.game.compete.operation.finance.service.FinanceService;
import edu.cqupt.mislab.erp.game.compete.operation.order.dao.GameOrderInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.GameOrderInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-07-14 20:03
 * @description
 */
@Slf4j
@Service
public class OrderAdvance implements ModelAdvance {

    @Autowired
    private GameOrderInfoRepository gameOrderInfoRepository;

    @Autowired
    private FinanceService financeService;

    @Override
    public boolean modelHistory(EnterpriseBasicInfo enterpriseBasicInfo) {

        log.info("开始记录订单模块比赛期间历史数据");

        // 暂时没有什么要记录的啦

        log.info("订单模块-比赛期间历史数据记录成功");

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean modelAdvance(EnterpriseBasicInfo enterpriseBasicInfo) {

        log.info("开始进行订单模块比赛期间周期推进");

        // 获取全部未交货的订单
        List<GameOrderInfo> gameOrderInfoList = gameOrderInfoRepository.findByEnterpriseBasicInfo_IdAndOrderStatus(enterpriseBasicInfo.getId(), false);

        // 判断是否到达了必须交货的周期
        for(GameOrderInfo gameOrderInfo : gameOrderInfoList) {

            // 根据该订单是哪一年的，计算得到获取该订单的周期
            Integer beginPeriod = 1 + (gameOrderInfo.getYear()-1)*enterpriseBasicInfo.getGameBasicInfo().getGameInitBasicInfo().getPeriodOfOneYear();

            // 若已到达期限而未交货，按期扣除违约金
            if(enterpriseBasicInfo.getEnterpriseCurrentPeriod() > beginPeriod+gameOrderInfo.getDeliveryPeriod()) {
                String changeOperating = FinanceOperationConstant.ORDER_DELAY;
                Double changeAmount = gameOrderInfo.getPrice()*gameOrderInfo.getProductNumber()*gameOrderInfo.getPenalPercent();
                financeService.updateFinanceInfo(enterpriseBasicInfo.getId(), changeOperating, changeAmount, true, true);
            }
        }

        log.info("订单模块-比赛期间周期推进正常");

        return true;
    }
}
