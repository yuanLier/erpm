package edu.cqupt.mislab.erp.game.compete.operation.product.service;

import edu.cqupt.mislab.erp.game.compete.operation.product.model.dto.ProductBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.dto.ProductMaterialBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductMaterialBasicVo;

/**
 * @author yuanyiwen
 * @create 2019-05-16 18:06
 * @description
 */
public interface ProductManagerService {

    /**
     * 添加一个产品基本信息
     * @param productBasicDto
     * @return
     */
    ProductBasicVo addProductBasicInfo(ProductBasicDto productBasicDto);

    /**
     * 修改产品基本信息
     * @param productBasicId
     * @param productBasicDto
     * @return
     */
    ProductBasicVo updateProductBasicInfo(Long productBasicId, ProductBasicDto productBasicDto);

    /**
     * 关闭一个产品基本信息
     * @param productBasicId
     * @return
     */
    ProductBasicVo closeProductBasicInfo(Long productBasicId);

    /**
     * 修改产品组成原料的基本信息
     * @param productMaterialBasicDto
     * @return
     */
    ProductMaterialBasicVo updateProductMaterialBasicInfo(ProductMaterialBasicDto productMaterialBasicDto);

}
