package edu.cqupt.mislab.erp.game.compete.operation.product.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
@ApiModel("产品构成展示视图")
public class ProductMaterialDisplayVo {

    @ApiModelProperty("代理主键，值同ProductDevelopInfo的主键")
    private Long id;

    @ApiModelProperty("构成该产品的原料（key-value:原料名-该原料所需个数）")
    private Map<String, Integer> materialMap;

    // 该字段是否需要？是否必要？存疑 todo 等这一块下一版原型图出来再改
    @ApiModelProperty("产品的基本售价")
    private Double productSellingPrice;

}
