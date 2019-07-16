package edu.cqupt.mislab.erp.game.compete.operation.product.service;

import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductMaterialDisplayVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @description
 * @date 20:12 2019/3/7
 **/

public interface ProductService {

    /**
     * 获取某个企业全部产品研发信息
     * @param enterpriseId
     * @return
     */
    List<ProductDisplayVo> findByEnterpriseId(Long enterpriseId);


    /**
     * 获取某个企业处于某种研发状态的产品研发信息
     * @param enterpriseId
     * @param productDevelopStatus
     * @return
     */
    List<ProductDisplayVo> findByEnterpriseIdAndProductStatus(Long enterpriseId, ProductDevelopStatusEnum productDevelopStatus);


    /**
     * 开始研发
     * @param productDevelopId
     * @return
     */
    ProductDisplayVo startProduct(Long productDevelopId);


    /**
     * 修改研发状态
     * @param productDevelopId
     * @return
     */
    ProductDisplayVo updateProductStatus(Long productDevelopId, ProductDevelopStatusEnum productDevelopStatusEnum);


    /**
     * 查询某个企业所有产品的原材料构成情况
     * @param enterpriseId
     * @return
     */
    List<ProductMaterialDisplayVo> findProductMaterialInfoByEnterpriseId(Long enterpriseId);


    /**
     * 查询某个企业出于某种状态的产品的原材料构成情况
     * @param enterpriseId
     * @param productDevelopStatus
     * @return
     */
    List<ProductMaterialDisplayVo> findProductMaterialInfoByEnterpriseIdAndProductStatus(Long enterpriseId, ProductDevelopStatusEnum productDevelopStatus);

}