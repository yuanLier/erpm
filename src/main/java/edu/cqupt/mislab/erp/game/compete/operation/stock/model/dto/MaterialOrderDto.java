package edu.cqupt.mislab.erp.game.compete.operation.stock.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author yuanyiwen
 * @create 2019-03-31 22:02
 * @description
 */
@Data
@ApiModel("材料订单基本数据视图")
public class MaterialOrderDto {

    @NotNull
    @ApiModelProperty(value = "原材料id",required = true)
    private Long materialBasicId;

    @Min(1)
    @NotNull
    @ApiModelProperty(value = "该材料对应的采购数量",required = true)
    private Integer purchaseQuantity;

    @NotNull
    @ApiModelProperty(value = "运输方式的id",required = true)
    private Long transportBasicId;


}
