package edu.cqupt.mislab.erp.game.compete.operation.produce.service.impl;

import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineProduceInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryDevelopStatus;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineDevelopStatus;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineProduceInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineProduceStatus;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.service.ProductionPlanService;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatus;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-09 15:36
 * @description 生产计划ServiceImpl
 */
@Service
public class ProductionPlanServiceImpl implements ProductionPlanService {

    @Autowired
    private FactoryDevelopInfoRepository factoryDevelopInfoRepository;
    @Autowired
    private ProdlineDevelopInfoRepository prodlineDevelopInfoRepository;
    @Autowired
    private ProductDevelopInfoRepository productDevelopInfoRepository;
    @Autowired
    private ProdlineProduceInfoRepository prodlineProduceInfoRepository;

    @Override
    public List<ProductTypeVo> getProductTypeOfEnterprise(Long enterpriseId) {
        // 获取该企业已经研发完成的产品
        List<ProductDevelopInfo> productDevelopInfoList = productDevelopInfoRepository
                .findByEnterpriseBasicInfo_IdAndProductDevelopStatus(enterpriseId, ProductDevelopStatus.DEVELOPED);

        // 将Entity集转化为Vo集
        List<ProductTypeVo> productTypeVoList = new ArrayList<>();
        for (ProductDevelopInfo productDevelopInfo : productDevelopInfoList) {
            ProductTypeVo productTypeVo = new ProductTypeVo();

            productTypeVo.setId(productDevelopInfo.getId());
            productTypeVo.setProductName(productDevelopInfo.getProductBasicInfo().getProductName());

            productTypeVoList.add(productTypeVo);
        }

        return productTypeVoList;
    }

    @Override
    public List<FactoryTypeVo> getFactoryTypeOfEnterprise(Long enterpriseId) {
        // 获取该企业有空闲生产线的全部厂房
        List<FactoryDevelopInfo> factoryDevelopInfoList = factoryDevelopInfoRepository
                .findByFactoryHoldingInfo_EnterpriseBasicInfo_IdAndFactoryDevelopStatus(enterpriseId, FactoryDevelopStatus.DEVELOPED);

        // 将Entity集转化为Vo集
        List<FactoryTypeVo> factoryTypeVoList = new ArrayList<>();
        for (FactoryDevelopInfo factoryDevelopInfo : factoryDevelopInfoList) {
            FactoryTypeVo factoryTypeVo = new FactoryTypeVo();

            factoryTypeVo.setId(factoryDevelopInfo.getId());
            factoryTypeVo.setFactoryType(factoryDevelopInfo.getFactoryHoldingInfo().getFactoryBasicInfo().getFactoryType());

            factoryTypeVoList.add(factoryTypeVo);
        }

        return factoryTypeVoList;
    }

    @Override
    public List<ProdlineTypeVo> getProdlineTypeOfEnterprise(Long factoryId) {
        // 获取某个厂房中处于空闲状态的全部生产线
        List<ProdlineProduceInfo> prodlineDevelopInfoList = prodlineProduceInfoRepository
                .findByProdlineHoldingInfo_FactoryDevelopInfo_IdAndProdlineProduceStatus(factoryId, ProdlineProduceStatus.TOPRODUCE);

        // 将Entity集转化为Vo集
        List<ProdlineTypeVo> prodlineTypeVoList = new ArrayList<>();
        for (ProdlineProduceInfo prodlineProduceInfo : prodlineDevelopInfoList) {
            ProdlineTypeVo prodlineTypeVo = new ProdlineTypeVo();

            prodlineTypeVo.setId(prodlineProduceInfo.getId());
            prodlineTypeVo.setProdlineType(prodlineProduceInfo.getProdlineHoldingInfo().getProdlineBasicInfo().getProdlineType());

            prodlineTypeVoList.add(prodlineTypeVo);
        }

        return prodlineTypeVoList;
    }
}
