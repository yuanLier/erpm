package edu.cqupt.mislab.erp.game.compete.operation.stock.service.impl;

import edu.cqupt.mislab.erp.commons.util.BeanCopyUtil;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.vo.MaterialBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.dao.MaterialStockHistoryRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.dao.ProductStockHistoryRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.MaterialStockHistoryInfo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.ProductStockHistoryInfo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo.MaterialStockHistoryVo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo.ProductStockHistoryVo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.service.StockHistoryService;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuanyiwen
 * @create 2019-05-25 21:30
 * @description
 */
@Service
public class StockHistoryServiceImpl implements StockHistoryService {

    @Autowired
    private MaterialStockHistoryRepository materialStockHistoryRepository;
    @Autowired
    private ProductStockHistoryRepository productStockHistoryRepository;
    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;

    @Override
    public List<MaterialStockHistoryVo> findMaterialStockHistoryVoOfGameAndPeriod(Long gameId, Integer period) {

        List<MaterialStockHistoryVo> materialStockHistoryVoList = new ArrayList<>();

        // 获取当前比赛中的全部企业
        List<EnterpriseBasicInfo> enterpriseBasicInfoList = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId);
        for (EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfoList) {

            MaterialStockHistoryVo materialStockHistoryVo = new MaterialStockHistoryVo();
            materialStockHistoryVo.setEnterpriseId(enterpriseBasicInfo.getId());
            materialStockHistoryVo.setPeriod(period);

            // 若该周期该企业已破产，则返回破产前最后一周期的认证情况
            Integer bankruptPeriod = enterpriseBasicInfo.getEnterpriseCurrentPeriod();
            period = (period < bankruptPeriod) ? period : bankruptPeriod;

            // 获取该企业在当前周期的全部的材料库存历史数据
            List<MaterialStockHistoryInfo> materialStockHistoryInfoList = materialStockHistoryRepository.findByEnterpriseBasicInfo_IdAndPeriod(enterpriseBasicInfo.getId(), period);

            Integer totalNumber = 0;
            Map<MaterialBasicVo, Integer> map = new HashMap<>();
            for (MaterialStockHistoryInfo materialStockHistoryInfo : materialStockHistoryInfoList) {

                // 统计库存总数
                totalNumber += materialStockHistoryInfo.getMaterialNumber();

                // 添加各个材料库存量
                MaterialBasicVo materialBasicVo = new MaterialBasicVo();
                BeanCopyUtil.copyPropertiesSimple(materialStockHistoryInfo.getMaterialBasicInfo(), materialBasicVo);
                map.put(materialBasicVo, materialStockHistoryInfo.getMaterialNumber());
            }

            materialStockHistoryVo.setTotalNumber(totalNumber);
            materialStockHistoryVo.setMaterialBasicVoMap(map);

            materialStockHistoryVoList.add(materialStockHistoryVo);
        }

        return materialStockHistoryVoList;
    }


    @Override
    public List<ProductStockHistoryVo> findProductStockHistoryVoOfGameAndPeriod(Long gameId, Integer period) {

        List<ProductStockHistoryVo> productStockHistoryVoList = new ArrayList<>();

        // 获取当前比赛中的全部企业
        List<EnterpriseBasicInfo> enterpriseBasicInfoList = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId);
        for (EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfoList) {

            ProductStockHistoryVo productStockHistoryVo = new ProductStockHistoryVo();
            productStockHistoryVo.setEnterpriseId(enterpriseBasicInfo.getId());
            productStockHistoryVo.setPeriod(period);

            // 若该周期该企业已破产，则返回破产前最后一周期的认证情况
            Integer bankruptPeriod = enterpriseBasicInfo.getEnterpriseCurrentPeriod();
            period = (period < bankruptPeriod) ? period : bankruptPeriod;

            // 获取该企业在当前周期的全部的产品库存历史数据
            List<ProductStockHistoryInfo> productStockHistoryInfoList = productStockHistoryRepository.findByEnterpriseBasicInfo_IdAndPeriod(enterpriseBasicInfo.getId(), period);

            Integer totalNumber = 0;
            Map<ProductBasicVo, Integer> map = new HashMap<>();
            for (ProductStockHistoryInfo productStockHistoryInfo : productStockHistoryInfoList) {

                // 统计库存总数
                totalNumber += productStockHistoryInfo.getProductNumber();

                // 添加各个产品库存量
                ProductBasicVo productBasicVo = new ProductBasicVo();
                BeanCopyUtil.copyPropertiesSimple(productStockHistoryInfo.getProductBasicInfo(), productBasicVo);
                map.put(productBasicVo, productStockHistoryInfo.getProductNumber());
            }

            productStockHistoryVo.setTotalNumber(totalNumber);
            productStockHistoryVo.setProductBasicVoMap(map);

            productStockHistoryVoList.add(productStockHistoryVo);
        }

        return productStockHistoryVoList;
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
