package edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-04-01 21:28
 * @description
 */
@Data
@ApiModel("材料运输方式的展示信息")
public class TransportMethodDisplayVo {

    @ApiModelProperty("代理主键")
    private Long id;

    @ApiModelProperty("运输方式的名称")
    private String transportName;

    @ApiModelProperty("运达所需要的周期数")
    private Integer transportPeriod;

    @ApiModelProperty("每周期所需费用")
    private Double transportPrice;
}
