package edu.cqupt.mislab.erp.game.compete.operation.product.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author yuanyiwen
 * @description 
 **/

@Data
@ApiModel("产品构成展示视图")
public class ProductMaterialDisplayVo {

    @ApiModelProperty("代理主键，值同ProductDevelopInfo的主键")
    private Long id;

    @ApiModelProperty("构成该产品的原料（key-value:原料名-该原料所需个数）")
    private Map<String, Integer> materialMap;

    @ApiModelProperty("产品的基本售价")
    private Double productSellingPrice;

}
