package edu.cqupt.mislab.erp.game.compete.operation.market.model.vo;

import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("市场基本数据视图")
public class MarketBasicVo {

    @ApiModelProperty(value = "新的市场id")
    private Long id;

    @ApiModelProperty(value = "市场的名称")
    private String marketName;

    @ApiModelProperty(value = "完成市场开发的周期数")
    private Integer marketResearchPeriod;

    @ApiModelProperty(value = "在市场开发过程中，每个周期需要支付的费用")
    private Double marketResearchCost;

    @ApiModelProperty(value = "市场开发完成后，维持该市场每个周期需要支付的费用")
    private Double marketMaintainCost;

    @ApiModelProperty(value = "该认证的默认初始状态")
    private MarketStatusEnum marketStatus;//该认证的默认初始状态
}
