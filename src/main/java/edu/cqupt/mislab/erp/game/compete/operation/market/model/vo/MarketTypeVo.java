package edu.cqupt.mislab.erp.game.compete.operation.market.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-04-08 8:37
 * @description
 */
@Data
@ApiModel("市场类型视图")
public class MarketTypeVo {

    @ApiModelProperty("代理主键，值同marketBasicId")
    private Long id;

    @ApiModelProperty("市场名称")
    private String marketName;
}
