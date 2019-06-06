package edu.cqupt.mislab.erp.game.compete.operation.produce.service.productionplan.impl;

import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryHoldingInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineProduceInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryProdlineTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineProduceInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineProduceStatus;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.*;
import edu.cqupt.mislab.erp.game.compete.operation.produce.service.productionplan.ProductionPlanService;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuanyiwen
 * @create 2019-03-22 17:22
 * @description
 */
@Service
public class ProductionPlanServiceImpl implements ProductionPlanService {

    @Autowired
    private FactoryHoldingInfoRepository factoryHoldingInfoRepository;

    @Autowired
    private ProdlineDevelopInfoRepository prodlineDevelopInfoRepository;
    @Autowired
    private ProdlineProduceInfoRepository prodlineProduceInfoRepository;

    @Autowired
    private ProductDevelopInfoRepository productDevelopInfoRepository;

    @Override
    public List<ProductTypeVo> getProducableProduct(Long enterpriseId) {
        // 已经研发完成的产品
        List<ProductDevelopInfo> productDevelopInfoList = productDevelopInfoRepository
                .findByEnterpriseBasicInfo_IdAndProductDevelopStatus(enterpriseId, ProductDevelopStatusEnum.DEVELOPED);

        // 将Entity集转化为Vo集
        List<ProductTypeVo> productTypeVoList = new ArrayList<>();
        for (ProductDevelopInfo productDevelopInfo : productDevelopInfoList) {
            ProductTypeVo productTypeVo = new ProductTypeVo();

            productTypeVo.setProductName(productDevelopInfo.getProductBasicInfo().getProductName());
            productTypeVo.setId(productDevelopInfo.getId());

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

        // 查找生产线们所在的厂房，并将厂房-生产线们加入map
        Map<FactoryHoldingInfo, List<ProdlineProduceInfo>> factoryHoldingInfoListMap = new HashMap<>();
        for (ProdlineProduceInfo prodlineProduceInfo : prodlineProduceInfoList) {

            // 获取该生产线所在的厂房
            FactoryHoldingInfo factoryHoldingInfo = prodlineProduceInfo.getProdlineHoldingInfo().getFactoryHoldingInfo();

            // 获取该厂房的生产线信息集
            List<ProdlineProduceInfo> prodlineProduceInfos = factoryHoldingInfoListMap.get(factoryHoldingInfo);

            // 若该信息集为空，手动初始化一下它
            if(prodlineProduceInfos == null) {
                prodlineProduceInfos = new ArrayList<>();
            }

            // 将该生产线加入该厂房的生产线信息集
            prodlineProduceInfos.add(prodlineProduceInfo);
            // 更新该厂房的生产信息集（若该厂房未被添加过，则该语句意为添加）
            factoryHoldingInfoListMap.put(factoryHoldingInfo, prodlineProduceInfos);
        }

        // 转换为List<FactoryProdlineTypeVo>
        List<FactoryProdlineTypeVo> factoryProdlineTypeVoList = new ArrayList<>();
        for (Map.Entry<FactoryHoldingInfo, List<ProdlineProduceInfo>> factoryHoldingInfoListEntry : factoryHoldingInfoListMap.entrySet()) {
            FactoryProdlineTypeVo factoryProdlineTypeVo = new FactoryProdlineTypeVo();

            // 获取厂房信息
            FactoryHoldingInfo factoryHoldingInfo = factoryHoldingInfoListEntry.getKey();
            // 获取厂房中对应的生产线信息
            List<ProdlineProduceInfo> prodlineProduceInfos = factoryHoldingInfoListEntry.getValue();

            factoryProdlineTypeVo.setId(factoryHoldingInfo.getId());
            factoryProdlineTypeVo.setFactoryType(factoryHoldingInfo.getFactoryBasicInfo().getFactoryBasicInfo().getFactoryType());
            factoryProdlineTypeVo.setProdlineTypeVoList(EntityVoUtil.copyFieldsFromEntityToVo(prodlineProduceInfos, new ProdlineTypeVo()));

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

        List<ProductProduceVo> productProduceVoList = new ArrayList<>();
        for (ProdlineProduceInfo prodlineProduceInfo : prodlineProduceInfoList) {
            productProduceVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(prodlineProduceInfo));
        }
        return productProduceVoList;
    }

    @Override
    public FactoryDisplayVo getFactoryDisplayVo(Long prodlineProduceId) {
        // 获取厂房信息
        FactoryHoldingInfo factoryHoldingInfo = prodlineProduceInfoRepository.findOne(prodlineProduceId).getProdlineHoldingInfo().getFactoryHoldingInfo();

        // 将该厂房及厂房中全部生产线信息转换为FactoryDisplayVo并返回
        return castFactoryHoldingEntityToDisplayVo(factoryHoldingInfo);
    }

    @Override
    public List<FactoryDisplayVo> getAllFactoryDisplayVos(Long enterpriseId) {
        List<FactoryDisplayVo> factoryDisplayVoList = new ArrayList<>();

        // 获取企业拥有的全部厂房信息
        List<FactoryHoldingInfo> factoryHoldingInfoList = factoryHoldingInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId);

        for (FactoryHoldingInfo factoryHoldingInfo : factoryHoldingInfoList) {
            // 将厂房及厂房内全部生产线转换为factoryDisplayVo并添加到集合中
            factoryDisplayVoList.add(castFactoryHoldingEntityToDisplayVo(factoryHoldingInfo));
        }

        return factoryDisplayVoList;
    }

    @Override
    public ProdlineDetailVo getProdlineDetailVo(Long prodlineId) {
        // 根据id获取要查看的生产线信息
        ProdlineProduceInfo prodlineProduceInfo = prodlineProduceInfoRepository.findOne(prodlineId);

        return EntityVoUtil.copyFieldsFromEntityToVo(prodlineProduceInfo, new ProdlineDetailVo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductProduceVo productProduction(Long prodlineId) {
        ProdlineProduceInfo prodlineProduceInfo = prodlineProduceInfoRepository.findOne(prodlineId);

        // 更新生产开始的周期
        prodlineProduceInfo.setBeginPeriod(prodlineProduceInfo.getProdlineHoldingInfo().getEnterpriseBasicInfo().getEnterpriseCurrentPeriod());
        // 更新生产状态至生产中
        prodlineProduceInfo.setProdlineProduceStatus(ProdlineProduceStatus.PRODUCING);
        // 保存修改
        prodlineProduceInfoRepository.save(prodlineProduceInfo);

        return EntityVoUtil.copyFieldsFromEntityToVo(prodlineProduceInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductProduceVo updateProduceStatus(Long prodlineId, ProdlineProduceStatus prodlineProduceStatus) {

        ProdlineProduceInfo prodlineProduceInfo = prodlineProduceInfoRepository.findOne(prodlineId);

        // 更新生产状态
        prodlineProduceInfo.setProdlineProduceStatus(prodlineProduceStatus);
        // 保存修改
        prodlineProduceInfoRepository.save(prodlineProduceInfo);

        return EntityVoUtil.copyFieldsFromEntityToVo(prodlineProduceInfo);
    }


    /**
     * @author yuanyiwen
     * @description 对FactoryHoldingInfo转FactoryDisplayVo的简单封装
     * @date 19:54 2019/3/22
     **/
    private FactoryDisplayVo castFactoryHoldingEntityToDisplayVo(FactoryHoldingInfo factoryHoldingInfo) {
        // 获取厂房中的全部处于生产状态生产线信息（允许为空）
        List<ProdlineProduceInfo> prodlineProduceInfoList = prodlineProduceInfoRepository.findByProdlineHoldingInfo_FactoryHoldingInfo_Id(factoryHoldingInfo.getId());
        // 获取厂房中处于安装状态的生产线信息（允许为空）
        List<ProdlineDevelopInfo> prodlineDevelopInfoList = prodlineDevelopInfoRepository.findByProdlineHoldingInfo_FactoryHoldingInfo_Id(factoryHoldingInfo.getId());

        // 将生产状态生产线转换为vo集
        List<ProdlineProduceDisplayVo> prodlineProduceDisplayVoList = EntityVoUtil.copyFieldsFromEntityToVo(prodlineProduceInfoList, new ProdlineProduceDisplayVo());
        // 将安装状态生产线转换为vo集
        List<ProdlineDevelopDisplayVo> prodlineDevelopDisplayVoList = EntityVoUtil.copyFieldsFromEntityToVo(prodlineDevelopInfoList, new ProdlineDevelopDisplayVo());

        return EntityVoUtil.copyFieldsFromEntityToVo(factoryHoldingInfo, prodlineProduceDisplayVoList, prodlineDevelopDisplayVoList);
    }
}
