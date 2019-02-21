package edu.cqupt.mislab.erp.game.compete.operation.product.service;

import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductMaterialBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductMaterialVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
     * @param productStatus
     * @return
     */
    List<ProductDisplayVo> findByEnterpriseIdAndProductStatus(Long enterpriseId, ProductStatusEnum productStatus);


    /**
     * 修改某个产品的研发状态
     * @param productDevelopId
     * @param productStatus
     * @return
     */
    ProductDisplayVo updateProductStatus(Long productDevelopId, ProductStatusEnum productStatus);


    /**
     * （管理员）修改产品基本信息
     * @param productBasicInfo
     * @return
     */
    ProductBasicInfo updateProductBasicInfo(ProductBasicInfo productBasicInfo);

    /**
     * （管理员）修改产品组成原料的基本信息
     * @param productMaterialBasicInfo
     * @return
     */
    ProductMaterialBasicInfo updateProductMaterialBasicInfo(ProductMaterialBasicInfo productMaterialBasicInfo);

    /**
     * 查询某个企业所有产品的原材料构成情况
     * @param enterpriseId
     * @return
     */
    List<ProductMaterialVo> findProductMaterialInfoByEnterpriseId(Long enterpriseId);

    /**
     * 查询某个企业出于某种状态的产品的原材料构成情况
     * @param enterpriseId
     * @param productStatus
     * @return
     */
    List<ProductMaterialVo> findProductMaterialInfoByEnterpriseIdAndProductStatus(Long enterpriseId, ProductStatusEnum productStatus);
}
