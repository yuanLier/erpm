package edu.cqupt.mislab.erp.game.compete.operation.product.service;

import edu.cqupt.mislab.erp.game.compete.operation.product.model.dto.ProductBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.dto.ProductMaterialBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatus;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductMaterialBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductMaterialDisplayVo;

import java.util.List;

public interface ProductService {
    /**
     * @author yuanyiwen
     * @description 获取某个企业全部产品研发信息
     * @date 20:12 2019/3/7
     * @param
     * @return
     **/
    List<ProductDisplayVo> findByEnterpriseId(Long enterpriseId);


    /**
     * @author yuanyiwen
     * @description 获取某个企业处于某种研发状态的产品研发信息
     * @date 20:12 2019/3/7
     * @param
     * @return
     **/
    List<ProductDisplayVo> findByEnterpriseIdAndProductStatus(Long enterpriseId, ProductDevelopStatus productDevelopStatus);


    /**
     * @author yuanyiwen
     * @description 修改某个产品的研发状态
     * @date 20:12 2019/3/7
     * @param
     * @return
     **/
    ProductDisplayVo updateProductStatus(Long productDevelopId, ProductDevelopStatus productDevelopStatus);

    /**
     * @author yuanyiwen
     * @description 查询某个企业所有产品的原材料构成情况
     * @date 20:13 2019/3/7
     * @param
     * @return
     **/
    List<ProductMaterialDisplayVo> findProductMaterialInfoByEnterpriseId(Long enterpriseId);

    /**
     * @author yuanyiwen
     * @description 查询某个企业出于某种状态的产品的原材料构成情况
     * @date 20:13 2019/3/7
     * @param
     * @return
     **/
    List<ProductMaterialDisplayVo> findProductMaterialInfoByEnterpriseIdAndProductStatus(Long enterpriseId, ProductDevelopStatus productDevelopStatus);




    /************************************************ 管理员调参相关 *******************************************/

    /**
     * @author yuanyiwen
     * @description （管理员）修改产品基本信息
     * @date 20:13 2019/3/7
     * @param
     * @return
     **/
    ProductBasicVo updateProductBasicInfo(ProductBasicDto productBasicDto);

    /**
     * @author yuanyiwen
     * @description （管理员）修改产品组成原料的基本信息
     * @date 20:13 2019/3/7
     * @param
     * @return
     **/
    ProductMaterialBasicVo updateProductMaterialBasicInfo(ProductMaterialBasicDto productMaterialBasicDto);
}