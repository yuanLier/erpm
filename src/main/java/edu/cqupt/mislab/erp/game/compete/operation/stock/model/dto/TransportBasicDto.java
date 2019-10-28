package edu.cqupt.mislab.erp.game.compete.operation.stock.model.dto;

import edu.cqupt.mislab.erp.commons.validators.annotations.DoubleMin;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author yuanyiwen
 * @create 2019-05-25 18:08
 * @description 所以 虽然库存没有管理端 但运输方式有！哭了
 */
@Data
@ApiModel("运输方式传输对象")
public class TransportBasicDto {

    @NotNull
    @ApiModelProperty(value = "运输方式的名称")
    private String transportName;

    @Min(1)
    @ApiModelProperty(value = "运达所需要的周期数")
    private int transportPeriod;

    @DoubleMin(0.01)
    @ApiModelProperty(value = "每周期所需费用")
    private double transportPrice;
}
