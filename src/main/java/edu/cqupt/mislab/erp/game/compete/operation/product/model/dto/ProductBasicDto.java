package edu.cqupt.mislab.erp.game.compete.operation.product.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel("产品基本数据视图")
public class ProductBasicDto {

    @Size(min = 1, max = 50)
    @NotNull
    @ApiModelProperty(value = "产品的名称",required = true)
    private String productName;

    @Min(1)
    @NotNull
    @ApiModelProperty(value = "产品研发的周期数",required = true)
    private Integer productResearchPeriod;

    @Min(0)
    @NotNull
    @ApiModelProperty(value = "在产品研发过程中，每个周期需要支付的费用",required = true)
    private Double productResearchCost;

    @Min(1)
    @NotNull
    @ApiModelProperty(value = "生产该产品所需的基本周期数",required = true)
    private Integer produceProductPeriod;

    @Min(0)
    @NotNull
    @ApiModelProperty(value = "在产品生产过程中，每个周期需要支付的费用",required = true)
    private Double produceProductCost;

    @Min(0)
    @NotNull
    @ApiModelProperty(value = "产品的基本售价",required = true)
    private Double productSellingPrice;

}
