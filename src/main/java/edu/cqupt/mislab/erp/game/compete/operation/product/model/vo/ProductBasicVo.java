package edu.cqupt.mislab.erp.game.compete.operation.product.model.vo;

import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatusEnum;
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

    @ApiModelProperty(value = "代理主键")
    private Long id;

    @ApiModelProperty(value = "产品的名称,所有相同产品名称数据中，enable=false标识历史数据，反之为最新数据，每一个name最多一个enable=true")
    private String productName;

    @ApiModelProperty(value = "产品研发的周期数，该值大于0")
    private int productResearchPeriod;

    @ApiModelProperty(value = "在产品研发过程中，每个周期需要支付的费用，该值大于0")
    private double productResearchCost;

    @ApiModelProperty(value = "生产x件该产品所需的基本周期数")
    private int produceProductPeriod;

    @ApiModelProperty(value = "这就是那个x")
    private int produceProductAmount;

    @ApiModelProperty(value = "在产品生产过程中，每个周期需要支付的费用")
    private double produceProductCost;

    @ApiModelProperty(value = "产品的基本售价")
    private double productSellingPrice;

    @ApiModelProperty(value = "市场需求量,该值大于0，百分制")
    private int mount;

    @ApiModelProperty(value = "市场之间的单价差异,该值大于0")
    private double priceDifference;

    @ApiModelProperty(value = "市场之间的需求量差异,该值大于0")
    private int mountDifference;

    @ApiModelProperty(value = "价格波动比例,该值大于0")
    private double priceFloat;

    @ApiModelProperty(value = "需求量波动比例,该值大于0")
    private double mountFloat;

    @ApiModelProperty(value = "默认的产品开发状态，该产品的默认研发状态，只能是未研发和已研发")
    private ProductDevelopStatusEnum productDevelopStatus;

    @ApiModelProperty(value = "该数据是否被启用，当前最新数据是启用，所有的历史数据均为未启用，必须保证同一个材料信息最多只有一个Enable = true")
    private boolean enable;
}
