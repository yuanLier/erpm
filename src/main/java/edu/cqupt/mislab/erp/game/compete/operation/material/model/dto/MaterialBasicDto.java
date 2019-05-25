package edu.cqupt.mislab.erp.game.compete.operation.material.model.dto;

import edu.cqupt.mislab.erp.commons.validators.annotations.DoubleMin;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author yuanyiwen
 * @create 2019-05-24 19:56
 * @description
 */
@Data
@ApiModel("材料基本信息传输对象")
public class MaterialBasicDto {

    @NotNull
    @ApiModelProperty(value = "原料的名称，所有相同原料名称数据中，enable=false标识历史数据，反之为最新数据，每一个name最多一个enable=true")
    private String materialName;

    @DoubleMin(0.01)
    @ApiModelProperty(value = "原料的价格,该值必须大于0")
    private double materialPrice;

    @Min(1)
    @ApiModelProperty(value = "指原料从采购开始到原料运到仓库，需要等待的周期数，该值必须大于0")
    private int materialDelayTime;
}
