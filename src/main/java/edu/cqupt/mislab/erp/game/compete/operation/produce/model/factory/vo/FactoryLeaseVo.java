package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-03-18 21:41
 * @description
 */
@Data
@ApiModel("厂房租赁情况展示")
public class FactoryLeaseVo {

    @ApiModelProperty("代理主键，值同factoryBasicInfo")
    private Long id;

    @ApiModelProperty("厂房类型")
    private String factoryType;

    @ApiModelProperty("租用该厂房时，每期需要交纳的租金")
    private Double factoryRentCost;
}
