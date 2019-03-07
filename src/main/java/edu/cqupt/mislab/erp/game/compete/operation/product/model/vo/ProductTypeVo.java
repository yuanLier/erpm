package edu.cqupt.mislab.erp.game.compete.operation.product.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("产品类型选择视图")
public class ProductTypeVo {

    @ApiModelProperty("代理主键，值同ProductBasicInfo")
    private Long id;

    @ApiModelProperty("产品类型（即产品名称）")
    private String productType;
}
