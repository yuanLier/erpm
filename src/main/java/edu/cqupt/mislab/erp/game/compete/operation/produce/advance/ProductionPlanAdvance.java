package edu.cqupt.mislab.erp.game.compete.operation.produce.advance;

import edu.cqupt.mislab.erp.commons.constant.FinanceOperationConstant;
import edu.cqupt.mislab.erp.game.compete.basic.ModelAdvance;
import edu.cqupt.mislab.erp.game.compete.operation.finance.service.finance.FinanceService;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineProduceInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineProduceInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineProduceStatus;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-06-08 12:10
 * @description 生产计划周期推进
 */
@Slf4j
@Component
public class ProductionPlanAdvance implements ModelAdvance {

    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired
    private ProdlineProduceInfoRepository prodlineProduceInfoRepository;

    @Autowired
    private FinanceService financeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean modelHistory(EnterpriseBasicInfo enterpriseBasicInfo) {

        log.info("开始记录生产计划模块比赛期间历史数据");

        // 暂时没有什么需要记录的w

        log.info("生产计划模块-比赛期间历史数据记录成功");

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean modelAdvance(EnterpriseBasicInfo enterpriseBasicInfo) {

        log.info("开始进行生产计划模块比赛期间周期推进");

        // 获取企业中全部生产中的生产线
        List<ProdlineProduceInfo> prodlineProduceInfoList = prodlineProduceInfoRepository.findByProdlineHoldingInfo_EnterpriseBasicInfo_IdAndAndProdlineProduceStatus(enterpriseBasicInfo.getId(), ProdlineProduceStatus.PRODUCING);
        for(ProdlineProduceInfo prodlineProduceInfo : prodlineProduceInfoList) {

            // 已经生产的周期数+1
            prodlineProduceInfo.setProducedPeriod(prodlineProduceInfo.getProducedPeriod()+1);

            // 计算需要生产的总周期（总周期 = 该产品需要生产的总周期*生产线对产品生产周期的影响）
            Integer totalPeriod = (int)(prodlineProduceInfo.getProductDevelopInfo().getProductBasicInfo().getProduceProductPeriod()*prodlineProduceInfo.getProdlineHoldingInfo().getProdlineBasicInfo().getProdlineBasicInfo().getExtraPeriod());
            // 判断是否完成生产
            if(totalPeriod.equals(prodlineProduceInfo.getProducedPeriod())) {
                // 生产状态转入完成生产
                prodlineProduceInfo.setProdlineProduceStatus(ProdlineProduceStatus.PRODUCED);
            }

            // 保存修改
            prodlineProduceInfoRepository.save(prodlineProduceInfo);

            // 扣除生产过程中需要支付的费用
            String changeOperating = FinanceOperationConstant.PRODUCT_PRODUCE;
            Double changeAmount = prodlineProduceInfo.getProductDevelopInfo().getProductBasicInfo().getProduceProductCost();
            financeService.updateFinanceInfo(enterpriseBasicInfo.getId(), changeOperating, changeAmount, true);
        }

        // 获取全部转产中的生产线
        prodlineProduceInfoList = prodlineProduceInfoRepository.findByProdlineHoldingInfo_EnterpriseBasicInfo_IdAndAndProdlineProduceStatus(enterpriseBasicInfo.getId(), ProdlineProduceStatus.TRANSFERRING);
        for(ProdlineProduceInfo prodlineProduceInfo : prodlineProduceInfoList) {

            // 如果beginPeriod为空，说明当前周期是刚刚进入转产的周期
            if(prodlineProduceInfo.getBeginPeriod() == null) {
                // 将转产开始的周期设为当前周期
                prodlineProduceInfo.setBeginPeriod(enterpriseBasicInfo.getEnterpriseCurrentPeriod());
            }

            // 若开始转产的周期数+转产所需的总周期数==当前周期
            if(prodlineProduceInfo.getBeginPeriod()+prodlineProduceInfo.getProdlineHoldingInfo().getProdlineBasicInfo().getProdlineBasicInfo().getProdlineChangePeriod() == enterpriseBasicInfo.getEnterpriseCurrentPeriod()) {
                // 转产完成；状态置为待生产，开始生产的周期数置为空
                prodlineProduceInfo.setProdlineProduceStatus(ProdlineProduceStatus.TOPRODUCE);
                prodlineProduceInfo.setBeginPeriod(null);
            }

            // 保存修改
            prodlineProduceInfoRepository.save(prodlineProduceInfo);

            // 扣除转产过程中需要支付的费用
            String changeOperating = FinanceOperationConstant.PRODLINE_TRANSACTION;
            Double changeAmount = prodlineProduceInfo.getProdlineHoldingInfo().getProdlineBasicInfo().getProdlineBasicInfo().getProdlineChangeCost();
            financeService.updateFinanceInfo(enterpriseBasicInfo.getId(), changeOperating, changeAmount, true);
        }

        log.info("生产计划模块-比赛期间周期推进正常");

        // 运行到这里说明周期推进正常
        return true;

    }
}
