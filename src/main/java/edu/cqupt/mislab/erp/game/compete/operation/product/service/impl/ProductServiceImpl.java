package edu.cqupt.mislab.erp.game.compete.operation.product.service.impl;

import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductMaterialBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.dto.ProductBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.dto.ProductMaterialBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatus;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductMaterialBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductMaterialBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductMaterialDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductBasicInfoRepository productBasicInfoRepository;

    @Autowired
    private ProductMaterialBasicInfoRepository productMaterialBasicInfoRepository;

    @Autowired
    private ProductDevelopInfoRepository productDevelopInfoRepository;


    @Override
    public List<ProductDisplayVo> findByEnterpriseId(Long enterpriseId) {
        // 获取entity集
        List<ProductDevelopInfo> productDevelopInfoList =
                productDevelopInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId);

        return castEntitiesToVos(productDevelopInfoList);
    }

    @Override
    public List<ProductDisplayVo> findByEnterpriseIdAndProductStatus(Long enterpriseId, ProductDevelopStatus productDevelopStatus) {
        // 获取entity集
        List<ProductDevelopInfo> productDevelopInfoList =
                productDevelopInfoRepository.findByEnterpriseBasicInfo_IdAndProductDevelopStatus(enterpriseId, productDevelopStatus);

        return castEntitiesToVos(productDevelopInfoList);
    }

    @Override
    public ProductDisplayVo updateProductStatus(Long productDevelopId, ProductDevelopStatus productDevelopStatus) {
        // 根据id查询产品信息
        ProductDevelopInfo productDevelopInfo = productDevelopInfoRepository.findOne(productDevelopId);

        // 非空判断
        if(productDevelopInfo == null) {
            return null;
        }

        // 修改市场状态
        productDevelopInfo.setProductDevelopStatus(productDevelopStatus);

        // 保存修改
        try {
            productDevelopInfoRepository.save(productDevelopInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
    public List<ProductMaterialDisplayVo> findProductMaterialInfoByEnterpriseIdAndProductStatus(Long enterpriseId, ProductDevelopStatus productDevelopStatus) {

        // 获取该企业处于该中状态下的的所有产品研发信息
        List<ProductDevelopInfo> productDevelopInfoList =
                productDevelopInfoRepository.findByEnterpriseBasicInfo_IdAndProductDevelopStatus(enterpriseId, productDevelopStatus);

        // 根据这个产品研发信息集合查询每个产品对应的原材料构成情况，调用一个封装的方法直接返回vo实体集
        return getProductMaterialVoByProductDevelopInfo(productDevelopInfoList);
    }

    /********************************************* 管理员调参相关 ********************************************/



    @Override
    public ProductBasicVo updateProductBasicInfo(ProductBasicDto productBasicDto) {
        return null;
    }

    @Override
    public ProductMaterialBasicVo updateProductMaterialBasicInfo(ProductMaterialBasicDto productMaterialBasicDto) {
        return null;
    }




    /******************************************** 以下是封装的方法 ********************************************/

    /**
     * @author yuanyiwen
     * @description findByEnterpriseId 和 findByEnterpriseIdAndProductStatus 的简单封装
     * @date 20:19 2019/3/7
     * @param productDevelopInfoList 接收一个产品研发情况信息的Entity集合
     * @return 返回一个产品研发情况展示的Vo集合
     **/
    private List<ProductDisplayVo> castEntitiesToVos(List<ProductDevelopInfo> productDevelopInfoList) {

        // 非空判断
        if(productDevelopInfoList == null || productDevelopInfoList.size() == 0) {
            return null;
        }

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

        // 非空判断
        if(productDevelopInfoList == null) {
            return null;
        }

        List<ProductMaterialDisplayVo> productMaterialDisplayVoList = new ArrayList<>();

        // 对该企业的每一个产品研发信息
        for (ProductDevelopInfo productDevelopInfo : productDevelopInfoList) {

            // 取出产品的基本信息id
            Long productBasicInfoId = productDevelopInfo.getProductBasicInfo().getId();
            // 根据产品id查询该产品对应的所有的原材料构成情况
            List<ProductMaterialBasicInfo> productMaterialBasicInfoList =
                    productMaterialBasicInfoRepository.findByEnableIsTrueAndProductBasicInfo_Id(productBasicInfoId);

            // 构建原料信息映射
            Map<String, Integer> materialMap = new HashMap<>();
            for (ProductMaterialBasicInfo productMaterialBasicInfo : productMaterialBasicInfoList) {
                // key-value ；原材料名称-所需要该材料的数量
                String materialName = productMaterialBasicInfo.getMaterialBasicInfo().getMaterialName();
                Integer materialNumber = productMaterialBasicInfo.getNumber();

                materialMap.put(materialName, materialNumber);
            }

            // 构建一个ProductMaterialVo
            ProductMaterialDisplayVo productMaterialDisplayVo = new ProductMaterialDisplayVo();
            // 产品构成-id（同ProductDevelopId
            productMaterialDisplayVo.setId(productDevelopInfo.getId());
            // 产品构成-原材料map
            productMaterialDisplayVo.setMaterialMap(materialMap);
            // 产品构成-售价 todo 是否需要？
            productMaterialDisplayVo.setProductSellingPrice(productDevelopInfo.getProductBasicInfo().getPrice());

            // 将该Vo加入集合
            productMaterialDisplayVoList.add(productMaterialDisplayVo);
        }

        // 返回该企业所有产品的原材料构成集合
        return productMaterialDisplayVoList;
    }
}
