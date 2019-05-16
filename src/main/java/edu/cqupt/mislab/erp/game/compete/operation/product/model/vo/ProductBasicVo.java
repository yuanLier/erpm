package edu.cqupt.mislab.erp.game.compete.operation.product.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chuyunfei
 * @description 
 **/

@Data
@ApiModel("产品基本数据视图")
public class ProductBasicVo {

    @ApiModelProperty(value = "新的产品id")
    private Long id;

    @ApiModelProperty(value = "产品的名称")
    private String productName;

    @ApiModelProperty(value = "产品研发的周期数")
    private Integer productResearchPeriod;

    @ApiModelProperty(value = "在产品研发过程中，每个周期需要支付的费用")
    private Double productResearchCost;

    @ApiModelProperty(value = "生产该产品所需的基本周期数")
    private Integer produceProductPeriod;

    @ApiModelProperty(value = "在产品生产过程中，每个周期需要支付的费用")
    private Double produceProductCost;

    @ApiModelProperty(value = "产品的基本售价")
    private Double productSellingPrice;

}
