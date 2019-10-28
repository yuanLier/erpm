package edu.cqupt.mislab.erp.game.compete.operation.product.service;

import edu.cqupt.mislab.erp.game.compete.operation.product.model.dto.ProductBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.dto.ProductMaterialBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductMaterialBasicVo;

import java.util.List;

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
     * 添加一个产品原料信息
     * @param productMaterialBasicDto
     * @return
     */
    ProductMaterialBasicVo addProductMaterialBasicInfo(ProductMaterialBasicDto productMaterialBasicDto);

    /**
     * 修改产品组成原料的基本信息（只能修改原料数量）
     * @param productMaterialId
     * @param number
     * @return
     */
    ProductMaterialBasicVo updateProductMaterialBasicInfo(Long productMaterialId, Integer number);

    /**
     * 关闭一个产品原料信息
     * @param productMaterialId
     * @return
     */
    ProductMaterialBasicVo closeProductMaterialBasicInfo(Long productMaterialId);

    
    /**
     * 获取处于某一状态（可用or不可用）的产品基本信息
     * @param enable 是否可用
     * @return
     */
    List<ProductBasicVo> getAllProductBasicVoOfStatus(boolean enable);


    /**
     * 获取处于某一状态（可用or不可用）的产品基本原材料信息
     * @param enable 是否可用
     * @return
     */
    List<ProductMaterialBasicVo> getAllProductMaterialBasicVoOfStatus(boolean enable);

}
