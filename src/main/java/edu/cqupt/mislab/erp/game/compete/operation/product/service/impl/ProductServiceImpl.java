package edu.cqupt.mislab.erp.game.compete.operation.product.service.impl;

import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductMaterialBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductMaterialVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.util.VoUtil;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductMaterialBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // 非空判断
        if(productDevelopInfoList == null || productDevelopInfoList.size() == 0) {
            return null;
        }

        // 转换为vo集
        List<ProductDisplayVo> productDisplayVoList = new ArrayList<>();
        for (ProductDevelopInfo productDevelopInfo : productDevelopInfoList) {
            productDisplayVoList.add(VoUtil.castEntityToVo(productDevelopInfo));
        }

        // 返回vo集
        return productDisplayVoList;
    }

    @Override
    public List<ProductDisplayVo> findByEnterpriseIdAndProductStatus(Long enterpriseId, ProductStatusEnum productStatus) {
        // 获取entity集
        List<ProductDevelopInfo> productDevelopInfoList =
                productDevelopInfoRepository.findByEnterpriseBasicInfo_IdAndProductStatus(enterpriseId, productStatus);

        // 非空判断
        if(productDevelopInfoList == null || productDevelopInfoList.size() == 0) {
            return null;
        }

        // 转换为vo集
        List<ProductDisplayVo> productDisplayVoList = new ArrayList<>();
        for (ProductDevelopInfo productDevelopInfo : productDevelopInfoList) {
            productDisplayVoList.add(VoUtil.castEntityToVo(productDevelopInfo));
        }

        // 返回vo集
        return productDisplayVoList;
    }

    @Override
    public ProductDisplayVo updateProductStatus(Long productDevelopId, ProductStatusEnum productStatus) {
        // 根据id查询产品信息
        ProductDevelopInfo productDevelopInfo = productDevelopInfoRepository.findOne(productDevelopId);

        // 非空判断
        if(productDevelopInfo == null) {
            return null;
        }

        // 修改市场状态
        productDevelopInfo.setProductStatus(productStatus);

        // 保存修改
        productDevelopInfoRepository.save(productDevelopInfo);

        // 转换为vo实体并返回
        return VoUtil.castEntityToVo(productDevelopInfo);
    }

    @Override
    public ProductBasicInfo updateProductBasicInfo(ProductBasicInfo productBasicInfo) {

        // 保存修改并返回
        return productBasicInfoRepository.save(productBasicInfo);
    }

    @Override
    public ProductMaterialBasicInfo updateProductMaterialBasicInfo(ProductMaterialBasicInfo productMaterialBasicInfo) {

        // 保存修改并返回
        return productMaterialBasicInfoRepository.save(productMaterialBasicInfo);
    }

    @Override
    public List<ProductMaterialVo> findProductMaterialInfoByEnterpriseId(Long enterpriseId) {

        // 首先根据企业id获取该企业的所有产品研发信息
        List<ProductDevelopInfo> productDevelopInfoList =
                productDevelopInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId);

        // 非空判断
        if(productDevelopInfoList == null) {
            return null;
        }

        // 根据这个产品研发信息集合查询每个产品对应的原材料构成情况，调用一个封装的方法直接返回vo实体集
        return getProductMaterialVoByProductDevelopInfo(productDevelopInfoList);

    }

    @Override
    public List<ProductMaterialVo> findProductMaterialInfoByEnterpriseIdAndProductStatus(Long enterpriseId, ProductStatusEnum productStatus) {
        List<ProductMaterialVo> productMaterialVoList = new ArrayList<>();

        // 获取该企业处于该中状态下的的所有产品研发信息
        List<ProductDevelopInfo> productDevelopInfoList =
                productDevelopInfoRepository.findByEnterpriseBasicInfo_IdAndProductStatus(enterpriseId, productStatus);

        // 非空判断
        if(productDevelopInfoList == null) {
            return null;
        }

        // 根据这个产品研发信息集合查询每个产品对应的原材料构成情况，调用一个封装的方法直接返回vo实体集
        return getProductMaterialVoByProductDevelopInfo(productDevelopInfoList);
    }

    /**
     * 通过产品研发信息列表得到对应的产品原材料信息的vo实体
     * 只是把前面两个方法封装了一下啦 不放到VoUtil是因为需要调用repository
     * @param productDevelopInfoList 接收一个productDevelopInfo集合
     * @return 返回productDevelopInfo集合中每个产品对应的原材料信息Vo
     */
    public List<ProductMaterialVo> getProductMaterialVoByProductDevelopInfo(List<ProductDevelopInfo> productDevelopInfoList) {

        List<ProductMaterialVo> productMaterialVoList = new ArrayList<>();

        // 对该企业的每一个产品研发信息
        for (ProductDevelopInfo productDevelopInfo : productDevelopInfoList) {

            // 取出产品的基本信息id
            Long productBasicInfoId = productDevelopInfo.getProductBasicInfo().getId();
            // 根据产品id查询该产品对应的所有的原材料构成情况
            List<ProductMaterialBasicInfo> productMaterialBasicInfoList =
                    productMaterialBasicInfoRepository.findByProductBasicInfo_Id(productBasicInfoId);

            // 构建原料信息映射
            Map<String, Integer> materialMap = new HashMap<>();
            for (ProductMaterialBasicInfo productMaterialBasicInfo : productMaterialBasicInfoList) {
                // key-value ；原材料名称-所需要该材料的数量
                String materialName = productMaterialBasicInfo.getMaterialBasicInfo().getMaterialName();
                Integer materialNumber = productMaterialBasicInfo.getMaterialNumber();

                materialMap.put(materialName, materialNumber);
            }

            // 构建一个ProductMaterialVo
            ProductMaterialVo productMaterialVo = new ProductMaterialVo();
            // 产品构成-id（同ProductDevelopId
            productMaterialVo.setId(productDevelopInfo.getId());
            // 产品构成-原材料map
            productMaterialVo.setMaterialMap(materialMap);
            // 产品构成-售价（该字段是否需要？是否必要？存疑 todo 等这一块下一版原型图出来再改
            productMaterialVo.setProductSellingPrice(productDevelopInfo.getProductBasicInfo().getProductSellingPrice());

            // 将该Vo加入集合
            productMaterialVoList.add(productMaterialVo);
        }

        // 返回该企业所有产品的原材料构成集合
        return productMaterialVoList;
    }
}
