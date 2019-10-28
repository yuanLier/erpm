package edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-05-25 18:16
 * @description
 */
@Data
@ApiModel("运输方式基本信息展示视图")
public class TransportBasicVo {

    @ApiModelProperty("代理主键")
    private Long id;

    @ApiModelProperty("运输方式的名称")
    private String transportName;

    @ApiModelProperty("运达所需要的周期数")
    private Integer transportPeriod;

    @ApiModelProperty("每周期所需费用")
    private Double transportPrice;

    @ApiModelProperty("是否启用")
    private boolean enable;
}
