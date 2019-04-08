package edu.cqupt.mislab.erp.game.compete.operation.order.model.vo;

import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductTypeVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-04-07 16:15
 * @description
 */
@Data
@ApiModel("销售管理中按订单交货 视图展示")
public class OrderDisplayVo {

    @ApiModelProperty("代理主键，指同orderId")
    private Long id;

    @ApiModelProperty("订单编号，值同id的后五位")
    private String orderNumber;

    @ApiModelProperty("哪个市场的订单")
    private MarketTypeVo marketType;

    @ApiModelProperty("哪个产品的订单")
    private ProductTypeVo productType;

    @ApiModelProperty("订单所需的产品数量")
    private Integer productNumber;

    @ApiModelProperty("产品单价")
    private Double productPrice;

    @ApiModelProperty("订单总额，值同productNumber*productPrice")
    private Double totalPrice;

    @ApiModelProperty("哪一年的订单")
    private Integer year;

    @ApiModelProperty("订单时限，即最晚经历多久必须要提交订单")
    private Integer deliveryPeriod;

    @ApiModelProperty("交货时间，指实际交货时间")
    private Integer orderEndPeriod;

    @ApiModelProperty("订单状态，即是否交货")
    private boolean orderStatus;
}
