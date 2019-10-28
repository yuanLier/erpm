package edu.cqupt.mislab.erp.game.compete.operation.product.advance;

import edu.cqupt.mislab.erp.commons.constant.FinanceOperationConstant;
import edu.cqupt.mislab.erp.game.compete.basic.ModelAdvance;
import edu.cqupt.mislab.erp.game.compete.operation.finance.service.FinanceService;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductHistoryRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductHistoryInfo;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-16 10:07
 * @description
 */
@Slf4j
@Component
public class ProductAdvance implements ModelAdvance {

    @Autowired
    private ProductDevelopInfoRepository productDevelopInfoRepository;
    @Autowired
    private ProductHistoryRepository productHistoryRepository;

    @Autowired
    private FinanceService financeService;


    /**
     * 产品模块比赛期间历史数据 ：
     *      查一下企业拥有的产品研发信息就好
     *      Iso认证、市场开拓同理
     * @param enterpriseBasicInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean modelHistory(EnterpriseBasicInfo enterpriseBasicInfo) {

        log.info("开始记录产品模块比赛期间历史数据");

        // 获取全部研发完成的产品信息
        List<ProductDevelopInfo> productDevelopInfoList = productDevelopInfoRepository.findByEnterpriseBasicInfo_IdAndProductDevelopStatus(enterpriseBasicInfo.getId(), ProductDevelopStatusEnum.DEVELOPED);

        for(ProductDevelopInfo productDevelopInfo : productDevelopInfoList) {

            ProductHistoryInfo productHistoryInfo = new ProductHistoryInfo();

            productHistoryInfo.setEnterpriseBasicInfo(enterpriseBasicInfo);
            productHistoryInfo.setProductBasicInfo(productDevelopInfo.getProductBasicInfo());
            productHistoryInfo.setPeriod(enterpriseBasicInfo.getEnterpriseCurrentPeriod());

            // 记录截止到当前周期，各个企业研发完成的产品信息
            productHistoryRepository.save(productHistoryInfo);
        }

        log.info("产品模块-比赛期间历史数据记录成功");

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean modelAdvance(EnterpriseBasicInfo enterpriseBasicInfo) {

        log.info("开始进行产品模块比赛期间周期推进");

        // 获取该企业中全部研发中的产品
        List<ProductDevelopInfo> productDevelopInfoList = productDevelopInfoRepository.findByEnterpriseBasicInfo_IdAndProductDevelopStatus(enterpriseBasicInfo.getId(), ProductDevelopStatusEnum.DEVELOPING);

        for (ProductDevelopInfo productDevelopInfo : productDevelopInfoList) {

            // 已经研发的周期数+1
            productDevelopInfo.setDevelopedPeriod(productDevelopInfo.getDevelopedPeriod()+1);

            // 如果已经研发的周期数达到了该产品研发所需的周期数
            if(productDevelopInfo.getDevelopedPeriod() == productDevelopInfo.getProductBasicInfo().getProductResearchPeriod()) {
                // 设置为已研发
                productDevelopInfo.setProductDevelopStatus(ProductDevelopStatusEnum.DEVELOPED);
            }

            // 保存修改
            productDevelopInfoRepository.save(productDevelopInfo);

            // 扣除研发过程中需要支付的费用
            String changeOperating = FinanceOperationConstant.PRODUCT_DEVELOP;
            Double changeAmount = productDevelopInfo.getProductBasicInfo().getProductResearchCost();
            financeService.updateFinanceInfo(enterpriseBasicInfo.getId(), changeOperating, changeAmount, true, true);

        }

        log.info("产品模块-比赛期间周期推进正常");

        // 运行到这里说明周期推进正常
        return true;
    }
}
