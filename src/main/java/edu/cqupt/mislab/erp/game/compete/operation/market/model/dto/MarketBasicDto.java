package edu.cqupt.mislab.erp.game.compete.operation.market.model.dto;

import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author chuyunfei
 * @description
 **/

@Data
@ApiModel("市场基本数据传输对象")
public class MarketBasicDto {

    @Size(min = 1, max = 50)
    @NotNull
    @ApiModelProperty(value = "市场的名称",required = true)
    private String marketName;

    @Min(1)
    @NotNull
    @ApiModelProperty(value = "完成市场开发的周期数",required = true)
    private Integer marketResearchPeriod;

    @Min(0)
    @NotNull
    @ApiModelProperty(value = "在市场开发过程中，每个周期需要支付的费用",required = true)
    private Double marketResearchCost;

    @Min(0)
    @NotNull
    @ApiModelProperty(value = "市场开发完成后，维持该市场每个周期需要支付的费用",required = true)
    private Double marketMaintainCost;

    @NotNull
    @ApiModelProperty(value = "该认证的默认初始状态", required = true)
    private MarketStatusEnum marketStatus;

}
