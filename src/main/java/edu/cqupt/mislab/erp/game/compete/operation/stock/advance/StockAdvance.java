package edu.cqupt.mislab.erp.game.compete.operation.stock.advance;

import edu.cqupt.mislab.erp.commons.constant.FinanceOperationConstant;
import edu.cqupt.mislab.erp.game.compete.basic.ModelAdvance;
import edu.cqupt.mislab.erp.game.compete.operation.finance.service.FinanceService;
import edu.cqupt.mislab.erp.game.compete.operation.stock.dao.*;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.*;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-25 22:07
 * @description
 */
@Slf4j
@Service
public class StockAdvance implements ModelAdvance {

    @Autowired
    private MaterialOrderInfoRepository materialOrderInfoRepository;

    @Autowired
    private MaterialStockInfoRepository materialStockInfoRepository;
    @Autowired
    private ProductStockInfoRepository productStockInfoRepository;
    @Autowired
    private MaterialStockHistoryRepository materialStockHistoryRepository;
    @Autowired
    private ProductStockHistoryRepository productStockHistoryRepository;

    @Autowired
    private FinanceService financeService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean modelHistory(EnterpriseBasicInfo enterpriseBasicInfo) {

        log.info("开始记录库存管理模块比赛期间历史数据");

        // 获取该企业当前全部的材料库存情况
        List<MaterialStockInfo> materialStockInfoList = materialStockInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseBasicInfo.getId());

        for (MaterialStockInfo materialStockInfo : materialStockInfoList) {

            // 构建并存储材料库存历史记录
            MaterialStockHistoryInfo materialStockHistoryInfo = new MaterialStockHistoryInfo();

            materialStockHistoryInfo.setEnterpriseBasicInfo(enterpriseBasicInfo);
            materialStockHistoryInfo.setPeriod(enterpriseBasicInfo.getEnterpriseCurrentPeriod());
            materialStockHistoryInfo.setMaterialBasicInfo(materialStockInfo.getMaterialBasicInfo());
            materialStockHistoryInfo.setMaterialNumber(materialStockInfo.getMaterialNumber());

            materialStockHistoryRepository.save(materialStockHistoryInfo);
        }

        // 获取该企业当前全部的产品库存情况
        List<ProductStockInfo> productStockInfoList = productStockInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseBasicInfo.getId());

        for (ProductStockInfo productStockInfo : productStockInfoList) {

            // 构建并存储产品库存历史记录
            ProductStockHistoryInfo productStockHistoryInfo = new ProductStockHistoryInfo();

            productStockHistoryInfo.setEnterpriseBasicInfo(enterpriseBasicInfo);
            productStockHistoryInfo.setPeriod(enterpriseBasicInfo.getEnterpriseCurrentPeriod());
            productStockHistoryInfo.setProductBasicInfo(productStockInfo.getProductBasicInfo());
            productStockHistoryInfo.setProductNumber(productStockInfo.getProductNumber());

            productStockHistoryRepository.save(productStockHistoryInfo);
        }

        log.info("库存管理模块-比赛期间历史数据记录成功");

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean modelAdvance(EnterpriseBasicInfo enterpriseBasicInfo) {

        log.info("开始进行库存管理模块比赛期间周期推进");

        // 获取该企业中全部处于运输中状态下的订单
        List<MaterialOrderInfo> materialOrderInfoList = materialOrderInfoRepository.findByEnterpriseBasicInfo_IdAndTransportStatus(enterpriseBasicInfo.getId(), TransportStatusEnum.TRANSPORTING);

        for (MaterialOrderInfo materialOrderInfo : materialOrderInfoList) {

            // 如果企业所处的当前周期达到了该种运输方式所需要的周期数
            if(enterpriseBasicInfo.getEnterpriseCurrentPeriod() == materialOrderInfo.getTransportTime() + materialOrderInfo.getTransportMethod().getTransportBasicInfo().getTransportPeriod()) {

                // 设置为已送达
                materialOrderInfo.setTransportStatus(TransportStatusEnum.ARRIVED);
                materialOrderInfoRepository.save(materialOrderInfo);

                // 更新材料的库存情况
                MaterialStockInfo materialStockInfo = materialStockInfoRepository.findByEnterpriseBasicInfo_IdAndMaterialBasicInfo_Id(enterpriseBasicInfo.getId(), materialOrderInfo.getMaterialBasicInfo().getId());
                materialStockInfo.setMaterialNumber(materialStockInfo.getMaterialNumber()+materialOrderInfo.getPurchaseNumber());
                materialStockInfoRepository.save(materialStockInfo);
            }

            // 扣除运输过程中需要支付的费用
            String changeOperating = FinanceOperationConstant.MATERIAL_TRANSPORT;
            Double changeAmount = materialOrderInfo.getTransportMethod().getTransportBasicInfo().getTransportPrice();
            financeService.updateFinanceInfo(enterpriseBasicInfo.getId(), changeOperating, changeAmount, true, true);
        }

        // 获取该企业中全部处于已审核状态的订单
        materialOrderInfoList = materialOrderInfoRepository.findByEnterpriseBasicInfo_IdAndTransportStatus(enterpriseBasicInfo.getId(), TransportStatusEnum.CHECKED);

        for (MaterialOrderInfo materialOrderInfo : materialOrderInfoList) {

            // 设置为运输中
            materialOrderInfo.setTransportStatus(TransportStatusEnum.TRANSPORTING);
            // 更新开始运输的时间
            materialOrderInfo.setTransportTime(enterpriseBasicInfo.getEnterpriseCurrentPeriod());

            // 保存修改
            materialOrderInfoRepository.save(materialOrderInfo);
        }


        log.info("库存管理模块-比赛期间周期推进正常");

        // 运行到这里说明周期推进正常
        return true;
    }
}
