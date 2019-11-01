package edu.cqupt.mislab.erp.game.compete.operation.product.service.impl;

import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.GameProductMaterialBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.*;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductMaterialDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuanyiwen
 * @description
 * @date 20:12 2019/3/7
 **/

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private GameProductMaterialBasicInfoRepository gameProductMaterialBasicInfoRepository;

    @Autowired
    private ProductDevelopInfoRepository productDevelopInfoRepository;

    @Override
    public List<ProductTypeVo> getAllProductTypes(Long enterpriseId) {

        List<ProductDevelopInfo> productDevelopInfoList =
                productDevelopInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId);

        List<ProductTypeVo> productTypeVoList = new ArrayList<>();
        for(ProductDevelopInfo productDevelopInfo : productDevelopInfoList) {
            ProductBasicInfo productBasicInfo = productDevelopInfo.getProductBasicInfo();

            ProductTypeVo productTypeVo = new ProductTypeVo();
            productTypeVo.setId(productBasicInfo.getId());
            productTypeVo.setProductName(productBasicInfo.getProductName());

            productTypeVoList.add(productTypeVo);
        }

        return productTypeVoList;
    }

    @Override
    public List<ProductDisplayVo> findByEnterpriseId(Long enterpriseId) {
        // 获取entity集
        List<ProductDevelopInfo> productDevelopInfoList =
                productDevelopInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId);

        return castEntitiesToVos(productDevelopInfoList);
    }

    @Override
    public List<ProductDisplayVo> findByEnterpriseIdAndProductStatus(Long enterpriseId, ProductDevelopStatusEnum productDevelopStatus) {
        // 获取entity集
        List<ProductDevelopInfo> productDevelopInfoList =
                productDevelopInfoRepository.findByEnterpriseBasicInfo_IdAndProductDevelopStatus(enterpriseId, productDevelopStatus);

        return castEntitiesToVos(productDevelopInfoList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductDisplayVo startProduct(Long productDevelopId) {
        // 根据id查询产品信息
        ProductDevelopInfo productDevelopInfo = productDevelopInfoRepository.findOne(productDevelopId);

        // 开始研发
        productDevelopInfo.setProductDevelopStatus(ProductDevelopStatusEnum.DEVELOPING);
        // 设置开始研发的周期与已经研发的周期数
        productDevelopInfo.setBeginPeriod(productDevelopInfo.getEnterpriseBasicInfo().getEnterpriseCurrentPeriod());
        productDevelopInfo.setDevelopedPeriod(0);

        // 保存修改
        productDevelopInfoRepository.save(productDevelopInfo);

        // 转换为vo实体并返回
        return EntityVoUtil.copyFieldsFromEntityToVo(productDevelopInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductDisplayVo updateProductStatus(Long productDevelopId, ProductDevelopStatusEnum productDevelopStatusEnum) {
        // 根据id查询产品信息
        ProductDevelopInfo productDevelopInfo = productDevelopInfoRepository.findOne(productDevelopId);

        // 更新研发状态
        productDevelopInfo.setProductDevelopStatus(productDevelopStatusEnum);

        // 保存修改
        productDevelopInfoRepository.save(productDevelopInfo);

        // 转换为vo实体并返回
        return EntityVoUtil.copyFieldsFromEntityToVo(productDevelopInfo);
    }

    @Override
    public List<ProductMaterialDisplayVo> findProductMaterialInfoByEnterpriseId(Long enterpriseId) {

        // 首先根据企业id获取该企业的所有产品研发信息
        List<ProductDevelopInfo> productDevelopInfoList =
                productDevelopInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId);

        // 根据这个产品研发信息集合查询每个产品对应的原材料构成情况，调用一个封装的方法直接返回vo实体集
        return getProductMaterialVoByProductDevelopInfo(productDevelopInfoList);

    }

    @Override
    public List<ProductMaterialDisplayVo> findProductMaterialInfoByEnterpriseIdAndProductStatus(Long enterpriseId, ProductDevelopStatusEnum productDevelopStatus) {

        // 获取该企业处于该中状态下的的所有产品研发信息
        List<ProductDevelopInfo> productDevelopInfoList =
                productDevelopInfoRepository.findByEnterpriseBasicInfo_IdAndProductDevelopStatus(enterpriseId, productDevelopStatus);

        // 根据这个产品研发信息集合查询每个产品对应的原材料构成情况，调用一个封装的方法直接返回vo实体集
        return getProductMaterialVoByProductDevelopInfo(productDevelopInfoList);
    }


    /**
     * @author yuanyiwen
     * @description findByEnterpriseId 和 findByEnterpriseIdAndProductStatus 的简单封装
     * @date 20:19 2019/3/7
     * @param productDevelopInfoList 接收一个产品研发情况信息的Entity集合
     * @return 返回一个产品研发情况展示的Vo集合
     **/
    private List<ProductDisplayVo> castEntitiesToVos(List<ProductDevelopInfo> productDevelopInfoList) {

        // 转换为vo集
        List<ProductDisplayVo> productDisplayVoList = new ArrayList<>();
        for (ProductDevelopInfo productDevelopInfo : productDevelopInfoList) {
            productDisplayVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(productDevelopInfo));
        }

        // 返回vo集
        return productDisplayVoList;
    }

    /**
     * @author yuanyiwen
     * @description 对findProductMaterialInfoByEnterpriseId、findProductMaterialInfoByEnterpriseIdAndProductStatus的简单封装
     * @date 20:43 2019/3/7
     * @param productDevelopInfoList 接收一个productDevelopInfo集合
     * @return 返回productDevelopInfo集合中每个产品对应的原材料信息Vo
     **/
    private List<ProductMaterialDisplayVo> getProductMaterialVoByProductDevelopInfo(List<ProductDevelopInfo> productDevelopInfoList) {

        List<ProductMaterialDisplayVo> productMaterialDisplayVoList = new ArrayList<>();

        // 对该企业的每一个产品研发信息
        for (ProductDevelopInfo productDevelopInfo : productDevelopInfoList) {

            // 查询本场比赛中所使用的该产品对应的所有的原材料构成情况
            List<GameProductMaterialBasicInfo> gameProductMaterialBasicInfoList =
                    gameProductMaterialBasicInfoRepository.findByGameBasicInfo_IdAndProductMaterialBasicInfo_ProductBasicInfo_Id(productDevelopInfo.getEnterpriseBasicInfo().getGameBasicInfo().getId(), productDevelopInfo.getProductBasicInfo().getId());

            // 构建原料信息映射
            Map<String, Integer> materialMap = new HashMap<>();
            for (GameProductMaterialBasicInfo gameProductMaterialBasicInfo : gameProductMaterialBasicInfoList) {
                ProductMaterialBasicInfo productMaterialBasicInfo = gameProductMaterialBasicInfo.getProductMaterialBasicInfo();
                // key-value ；原材料名称-所需要该材料的数量
                String materialName = productMaterialBasicInfo.getMaterialBasicInfo().getMaterialName();
                Integer materialNumber = productMaterialBasicInfo.getNumber();

                materialMap.put(materialName, materialNumber);
            }

            // 构建一个ProductMaterialVo
            ProductMaterialDisplayVo productMaterialDisplayVo = new ProductMaterialDisplayVo();
            // 产品构成-id（同ProductDevelopId
            productMaterialDisplayVo.setProductTypeVo(new ProductTypeVo(productDevelopInfo.getId(), productDevelopInfo.getProductBasicInfo().getProductName()));
            // 产品构成-原材料map
            productMaterialDisplayVo.setMaterialMap(materialMap);
            // 产品构成-售价
            productMaterialDisplayVo.setProductSellingPrice(productDevelopInfo.getProductBasicInfo().getProductSellingPrice());

            // 将该Vo加入集合
            productMaterialDisplayVoList.add(productMaterialDisplayVo);
        }

        // 返回该企业所有产品的原材料构成集合
        return productMaterialDisplayVoList;
    }
}
