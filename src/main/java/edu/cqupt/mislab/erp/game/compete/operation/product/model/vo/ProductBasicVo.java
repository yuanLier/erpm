package edu.cqupt.mislab.erp.game.compete.operation.product.model.vo;

import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * author： chuyunfei date：2019/3/7
 */
@Data
public class ProductBasicVo {
    
    /* 
     * @Author: chuyunfei
     * @Date: 2019/3/7 12:12
     * @Description: 产品基本数据信息视图
     **/

    @ApiModelProperty("代理主键")
    private Long id;

    @ApiModelProperty("产品的名称")
    private String productName;

    @ApiModelProperty("产品研发的周期数，该值大于0")
    private int productResearchPeriod;

    @ApiModelProperty("在产品研发过程中，每个周期需要支付的费用，该值大于0")
    private double productResearchCost;

    @ApiModelProperty("产品价格,该值大于0")
    private double price;

    @ApiModelProperty("市场需求量,该值大于0")
    private int mount;

    @ApiModelProperty("市场之间的单价差异,该值大于0")
    private double priceDifference;

    @ApiModelProperty("市场之间的需求量差异,该值大于0")
    private int mountDifference;

    @ApiModelProperty("价格波动比例,该值大于0")
    private double priceFloat;

    @ApiModelProperty("需求量波动比例,该值大于0")
    private double mountFloat;

    @ApiModelProperty("默认的产品开发状态，该产品的默认研发状态，只能是未研发和已研发")
    private ProductDevelopStatus productDevelopStatus;

    @ApiModelProperty("该数据是否被启用")
    private boolean enable;
}
