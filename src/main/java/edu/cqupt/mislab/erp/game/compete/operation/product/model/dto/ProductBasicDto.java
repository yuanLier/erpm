package edu.cqupt.mislab.erp.game.compete.operation.product.model.dto;

import edu.cqupt.mislab.erp.commons.validators.annotations.DoubleMin;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author chuyunfei
 * @description 
 **/

@Data
@ApiModel("产品基本数据传输对象")
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

    @DoubleMin(0.01)
    @NotNull
    @ApiModelProperty(value = "在产品生产过程中，每个周期需要支付的费用",required = true)
    private Double produceProductCost;

    @DoubleMin(0.01)
    @NotNull
    @ApiModelProperty(value = "产品的基本售价",required = true)
    private Double productSellingPrice;

    @NotNull
    @ApiModelProperty(value = "该产品的默认初始状态", required = true)
    private ProductDevelopStatusEnum productDevelopStatus;

    @Range(min = 0, max = 100)
    @ApiModelProperty(value = "市场需求量,该值大于0，百分制")
    private int mount;

    @ApiModelProperty(value = "市场之间的单价差异,该值大于0，允许为null")
    private Double priceDifference;

    @ApiModelProperty(value = "市场之间的需求量差异,该值大于0，允许为null")
    private Integer mountDifference;

    @ApiModelProperty(value = "价格波动比例,该值大于0，允许为null")
    private Double priceFloat;

    @ApiModelProperty(value = "需求量波动比例,该值大于0，允许为null")
    private Double mountFloat;
}
