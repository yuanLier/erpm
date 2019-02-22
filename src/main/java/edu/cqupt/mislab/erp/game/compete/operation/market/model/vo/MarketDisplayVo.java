package edu.cqupt.mislab.erp.game.compete.operation.market.model.vo;

import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("市场数据展示视图")
public class MarketDisplayVo {
    
    @ApiModelProperty("代理主键，值同MarketDevelopInfo的主键")
    private Long id;

    @ApiModelProperty("市场开拓名称")
    private String MarketName;

    @ApiModelProperty("开拓总期数")
    private Integer MarketResearchPeriod;

    @ApiModelProperty("每期开拓费用")
    private Double MarketResearchCost;

    @ApiModelProperty("每期维护费用")
    private Double MarketMaintainCost;

    @ApiModelProperty("已经开拓的周期数")
    private Integer researchedPeriod;

    @ApiModelProperty("当前开拓状态")
    private MarketStatusEnum MarketStatus;

}
