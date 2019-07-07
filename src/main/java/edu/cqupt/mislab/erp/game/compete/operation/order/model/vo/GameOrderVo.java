package edu.cqupt.mislab.erp.game.compete.operation.order.model.vo;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
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

    @ApiModelProperty(value = "哪一场比赛")
    private GameBasicInfo gameBasicInfo;

    @ApiModelProperty(value = "哪一个产品")
    private ProductBasicInfo productBasicInfo;

    @ApiModelProperty(value = "哪一个市场")
    private MarketBasicInfo marketBasicInfo;

    @ApiModelProperty(value = "产品数量")
    private Integer productNumber;

    @ApiModelProperty(value = "产品单价")
    private Double price;

    @ApiModelProperty(value = "截止交货的日期为第几期")
    private Integer deliveryPeriod;

    @ApiModelProperty(value = "订单交货后，需要几个账期，金额可以到账")
    private Integer moneyTime;

    @ApiModelProperty(value = "违约金比率，超过1期交纳1期的违约金，超过多期，进行累计")
    private Double penalPercent;

    @ApiModelProperty(value = "所需要的质量认证信息")
    private IsoBasicInfo isoBasicInfo;

    @ApiModelProperty(value = "哪一年的订单")
    private Integer year;

    @ApiModelProperty(value = "该订单被哪个企业选择")
    private EnterpriseBasicInfo enterpriseBasicInfo;

}
