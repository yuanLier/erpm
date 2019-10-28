package edu.cqupt.mislab.erp.game.compete.operation.order.model.vo;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoBasicTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketBasicTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductBasicTypeVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-06-23 14:54
 * @description
 */
@Data
@ApiModel("订单池中订单信息展示视图")
public class GameOrderVo {

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "哪一场比赛")
    private Long gameBasicInfoId;

    @ApiModelProperty(value = "哪一个产品")
    private ProductBasicTypeVo productBasicType;

    @ApiModelProperty(value = "哪一个市场")
    private MarketBasicTypeVo marketBasicType;

    @ApiModelProperty(value = "产品数量")
    private Integer productNumber;

    @ApiModelProperty(value = "产品单价")
    private Double price;

    @ApiModelProperty(value = "截止交货的日期为第几期")
    private Integer deliveryPeriod;

    @ApiModelProperty(value = "违约金比率，超过1期交纳1期的违约金，超过多期，进行累计")
    private Double penalPercent;

    @ApiModelProperty(value = "所需要的质量认证信息")
    private IsoBasicTypeVo isoBasicType;

    @ApiModelProperty(value = "哪一年的订单")
    private Integer year;

}
