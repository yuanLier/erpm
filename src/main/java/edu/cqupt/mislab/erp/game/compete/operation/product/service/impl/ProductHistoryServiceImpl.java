package edu.cqupt.mislab.erp.game.compete.operation.product.service.impl;

import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductHistoryRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductHistoryInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductHistoryVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.service.ProductHistoryService;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yuanyiwen
 * @create 2019-05-16 21:38
 * @description
 */
@Service
public class ProductHistoryServiceImpl implements ProductHistoryService {

    @Autowired
    private ProductHistoryRepository productHistoryRepository;
    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;

    @Override
    public List<ProductHistoryVo> findProductHistoryVoOfGameAndPeriod(Long gameId, Integer period) {

        List<ProductHistoryVo> productHistoryVoList = new ArrayList<>();

        // 获取当前比赛中的全部企业
        List<EnterpriseBasicInfo> enterpriseBasicInfoList = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId);
        for (EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfoList) {

            ProductHistoryVo productHistoryVo = new ProductHistoryVo();
            productHistoryVo.setEnterpriseBasicInfo(enterpriseBasicInfo);
            productHistoryVo.setPeriod(period);

            // 若该周期该企业已破产，则返回破产前最后一周期的研发情况
            Integer bankruptPeriod = enterpriseBasicInfo.getEnterpriseCurrentPeriod();
            period = (period < bankruptPeriod) ? period : bankruptPeriod-1;

            // 获取全部产品研发信息
            List<ProductHistoryInfo> productHistoryInfoList = productHistoryRepository.findByEnterpriseBasicInfo_IdAndPeriod(enterpriseBasicInfo.getId(), period);
            // 获取该企业在当前周期的全部的产品基本信息历史数据
            List<ProductDevelopInfo> productDevelopInfoList = productHistoryInfoList.stream()
                    .map(ProductHistoryInfo::getProductDevelopInfo)
                    .collect(Collectors.toList());

            productHistoryVo.setProductDevelopInfoList(productDevelopInfoList);

            productHistoryVoList.add(productHistoryVo);
        }

        return productHistoryVoList;
    }

    @Override
    public Integer getTotalPeriod(Long gameId) {

        Integer totalPeriod = 0;

        // 获取当前比赛中的全部企业
        List<EnterpriseBasicInfo> enterpriseBasicInfoList = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId);
        for (EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfoList) {

            Integer currentPeriod = enterpriseBasicInfo.getEnterpriseCurrentPeriod();

            // 其实就是获取企业的最大存活周期
            totalPeriod = (totalPeriod > currentPeriod) ? totalPeriod : currentPeriod;
        }

        return totalPeriod;
    }
}
