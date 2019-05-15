package edu.cqupt.mislab.erp.game.compete.operation.market.model.vo;

import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketDevelopInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-15 20:04
 * @description
 */

@Data
@ApiModel("市场历史数据展示视图")
public class MarketHistoryVo {

    @ApiModelProperty("哪个企业")
    private EnterpriseBasicInfo enterpriseBasicInfo;

    @ApiModelProperty("哪个周期")
    private Integer period;

    @ApiModelProperty("截止到该周期，企业拥有的市场")
    private List<MarketDevelopInfo> marketDevelopInfoList;
}