package edu.cqupt.mislab.erp.game.compete.operation.produce.service.impl;

import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineProduceInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryProdlineTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineProduceInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineProduceStatus;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineDetailVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProductProduceVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.service.ProductionPlanService;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatus;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private ProdlineProduceInfoRepository prodlineProduceInfoRepository;

    @Autowired
    private ProductDevelopInfoRepository productDevelopInfoRepository;

    @Override
    public List<ProductTypeVo> getProducableProduct(Long enterpriseId) {
        // 已经研发完成的产品
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
    public List<FactoryProdlineTypeVo> getProducableFactoryAndProdline(Long enterpriseId, Long productId) {
        // 获取某个企业中处于空闲状态（即待生产状态）的、可生产指定产品的全部生产线
        List<ProdlineProduceInfo> prodlineProduceInfoList = prodlineProduceInfoRepository
                .findByProdlineHoldingInfo_EnterpriseBasicInfo_IdAndProductDevelopInfo_IdAndProdlineProduceStatus
                        (enterpriseId, productId, ProdlineProduceStatus.TOPRODUCE);

        if(prodlineProduceInfoList == null) {
            return null;
        }

        // 查找生产线们所在的厂房，并将厂房-生产线们加入map
        Map<FactoryDevelopInfo, List<ProdlineProduceInfo>> factoryDevelopInfoMap = new HashMap<>();
        for (ProdlineProduceInfo prodlineProduceInfo : prodlineProduceInfoList) {

            // 获取该生产线所在的厂房
            FactoryDevelopInfo factoryDevelopInfo = prodlineProduceInfo.getProdlineHoldingInfo().getFactoryDevelopInfo();

            // 获取该厂房的生产线信息集
            List<ProdlineProduceInfo> prodlineProduceInfos = factoryDevelopInfoMap.get(factoryDevelopInfo);

            // 若该信息集为空，手动初始化一下它
            if(prodlineProduceInfos == null) {
                prodlineProduceInfos = new ArrayList<>();
            }

            // 将该生产线加入该厂房的生产线信息集
            prodlineProduceInfos.add(prodlineProduceInfo);
            // 更新该厂房的生产信息集（若该厂房未被添加过，则该语句意为添加）
            factoryDevelopInfoMap.put(factoryDevelopInfo, prodlineProduceInfos);
        }

        // 转换为List<FactoryProdlineTypeVo>
        List<FactoryProdlineTypeVo> factoryProdlineTypeVoList = new ArrayList<>();
        for (Map.Entry<FactoryDevelopInfo, List<ProdlineProduceInfo>> factoryDevelopInfoListEntry : factoryDevelopInfoMap.entrySet()) {
            FactoryProdlineTypeVo factoryProdlineTypeVo = new FactoryProdlineTypeVo();

            // 获取厂房信息
            FactoryDevelopInfo factoryDevelopInfo = factoryDevelopInfoListEntry.getKey();
            // 获取厂房中对应的生产线信息
            List<ProdlineProduceInfo> prodlineProduceInfos = factoryDevelopInfoListEntry.getValue();

            factoryProdlineTypeVo.setId(factoryDevelopInfo.getId());
            factoryProdlineTypeVo.setFactoryType(factoryDevelopInfo.getFactoryHoldingInfo().getFactoryBasicInfo().getFactoryType());
            factoryProdlineTypeVo.setProdlineTypeVoList(castProdlineEntityToTypeVos(prodlineProduceInfos));

            factoryProdlineTypeVoList.add(factoryProdlineTypeVo);
        }
        return factoryProdlineTypeVoList;
    }

    @Override
    public List<ProductProduceVo> getProductProduceVosOfOneProduct(Long enterpriseId, Long productId) {
        // 获取除未生产状态外（即处于生产中、暂停中、生产完成的）的该产品全部生产线信息
        List<ProdlineProduceInfo> prodlineProduceInfoList = prodlineProduceInfoRepository
                .findByProdlineHoldingInfo_EnterpriseBasicInfo_IdAndProductDevelopInfo_IdAndProdlineProduceStatusIsNot
                        (enterpriseId, productId, ProdlineProduceStatus.TOPRODUCE);

        if(prodlineProduceInfoList == null) {
            return null;
        }

        List<ProductProduceVo> productProduceVoList = new ArrayList<>();
        for (ProdlineProduceInfo prodlineProduceInfo : prodlineProduceInfoList) {
            productProduceVoList.add(castProdlineEntityToProductProduceVo(prodlineProduceInfo));
        }
        return productProduceVoList;
    }

    @Override
    public FactoryDisplayVo getFactoryDisplayVo(Long factoryId) {

        // 获取厂房信息
        FactoryDevelopInfo factoryDevelopInfo = factoryDevelopInfoRepository.findOne(factoryId);

        if(factoryDevelopInfo == null) {
            return null;
        }

        // 获取厂房中的全部生产线信息（这里是允许为空的）
        List<ProdlineProduceInfo> prodlineProduceInfoList = prodlineProduceInfoRepository.findByProdlineHoldingInfo_FactoryDevelopInfo_Id(factoryId);

        // 将生产线entity集转换为vo集
        List<ProdlineDisplayVo> prodlineDisplayVoList = castProdlineEntityToDisplayVos(prodlineProduceInfoList);

        return EntityVoUtil.copyFieldsFromEntityToVo(factoryDevelopInfo, prodlineDisplayVoList);
    }

    @Override
    public List<FactoryDisplayVo> getAllFactoryDisplayVos(Long enterpriseId) {
        List<FactoryDisplayVo> factoryDisplayVoList = new ArrayList<>();

        // 获取企业中的全部厂房信息
        List<FactoryDevelopInfo> factoryDevelopInfoList = factoryDevelopInfoRepository.findByFactoryHoldingInfo_EnterpriseBasicInfo_Id(enterpriseId);

        if(factoryDevelopInfoList == null) {
            return null;
        }

        for (FactoryDevelopInfo factoryDevelopInfo : factoryDevelopInfoList) {

            // 获取厂房中的全部生产线信息（同样，允许为空）
            List<ProdlineProduceInfo> prodlineProduceInfoList = prodlineProduceInfoRepository.findByProdlineHoldingInfo_FactoryDevelopInfo_Id(factoryDevelopInfo.getId());

            // 将生产线entity集转换为vo集
            List<ProdlineDisplayVo> prodlineDisplayVoList = castProdlineEntityToDisplayVos(prodlineProduceInfoList);

            factoryDisplayVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(factoryDevelopInfo, prodlineDisplayVoList));
        }

        return factoryDisplayVoList;
    }

    @Override
    public ProdlineDetailVo getProdlineDetailVo(Long prodlineId) {
        // 根据id获取要查看的生产线信息
        ProdlineProduceInfo prodlineProduceInfo = prodlineProduceInfoRepository.findOne(prodlineId);

        if(prodlineProduceInfo == null) {
            return null;
        }

        return EntityVoUtil.copyFieldsFromEntityToVo(prodlineProduceInfo, new ProdlineDetailVo());
    }

    @Override
    public ProductProduceVo productProduction(Long prodlineId) {

        ProdlineProduceInfo prodlineProduceInfo = prodlineProduceInfoRepository.findOne(prodlineId);

        if(prodlineProduceInfo == null) {
            return null;
        }

        // 更新生产开始的周期
        prodlineProduceInfo.setBeginPeriod(prodlineProduceInfo.getProdlineHoldingInfo().getEnterpriseBasicInfo().getEnterpriseCurrentPeriod());
        // 更新生产状态至生产中
        prodlineProduceInfo.setProdlineProduceStatus(ProdlineProduceStatus.PRODUCING);
        // 保存修改
        try {
            prodlineProduceInfoRepository.save(prodlineProduceInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return castProdlineEntityToProductProduceVo(prodlineProduceInfo);
    }

    @Override
    public ProductProduceVo updateProduceStatus(Long prodlineId, ProdlineProduceStatus prodlineProduceStatus) {

        ProdlineProduceInfo prodlineProduceInfo = prodlineProduceInfoRepository.findOne(prodlineId);

        if(prodlineProduceInfo == null) {
            return null;
        }

        // 更新生产状态
        prodlineProduceInfo.setProdlineProduceStatus(prodlineProduceStatus);
        // 保存修改
        try {
            prodlineProduceInfoRepository.save(prodlineProduceInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return castProdlineEntityToProductProduceVo(prodlineProduceInfo);
    }


    /**
     * @author yuanyiwen
     * @description 对生产线List<ProdlineProduceInfo>转List<ProdlineTypeVo>的简单封装
     * @date 0:24 2019/3/14
     **/
    private List<ProdlineTypeVo> castProdlineEntityToTypeVos(List<ProdlineProduceInfo> prodlineProduceInfoList) {

        if(prodlineProduceInfoList == null) {
            return new ArrayList<>();
        }

        // 将Entity集转化为Vo集
        List<ProdlineTypeVo> prodlineTypeVoList = new ArrayList<>();
        for (ProdlineProduceInfo prodlineProduceInfo : prodlineProduceInfoList) {
            ProdlineTypeVo prodlineTypeVo = new ProdlineTypeVo();

            prodlineTypeVo.setId(prodlineProduceInfo.getId());
            prodlineTypeVo.setProdlineType(prodlineProduceInfo.getProdlineHoldingInfo().getProdlineBasicInfo().getProdlineType());

            prodlineTypeVoList.add(prodlineTypeVo);
        }

        return prodlineTypeVoList;
    }


    /**
     * @author yuanyiwen
     * @description 对生产线LList<ProdlineProduceInfo>转List<ProdlineDisplayVo>的简单封装
     * @date 9:48 2019/3/15
     **/
    private List<ProdlineDisplayVo> castProdlineEntityToDisplayVos(List<ProdlineProduceInfo> prodlineProduceInfoList) {

        if(prodlineProduceInfoList == null) {
            return new ArrayList<>();
        }

        // 将生产线entity集转换为vo集
        List<ProdlineDisplayVo> prodlineDisplayVoList = new ArrayList<>();
        for (ProdlineProduceInfo prodlineProduceInfo : prodlineProduceInfoList) {
            prodlineDisplayVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(prodlineProduceInfo, new ProdlineDisplayVo()));
        }
        return prodlineDisplayVoList;
    }


    /**
     * @author yuanyiwen
     * @description 对ProdlineProduceInfo转ProductProduceVo的简单封装
     * @date 11:55 2019/3/16
     **/
    private ProductProduceVo castProdlineEntityToProductProduceVo(ProdlineProduceInfo prodlineProduceInfo) {

        ProductProduceVo productProduceVo = new ProductProduceVo();

        productProduceVo.setId(prodlineProduceInfo.getId());
        productProduceVo.setProdlineType(prodlineProduceInfo.getProdlineHoldingInfo().getProdlineBasicInfo().getProdlineType());
        productProduceVo.setFactoryNumber(prodlineProduceInfo.getProdlineHoldingInfo().getFactoryDevelopInfo().getFactoryHoldingInfo().getFactoryBasicInfo().getFactoryNumber());

        return productProduceVo;
    }
}
