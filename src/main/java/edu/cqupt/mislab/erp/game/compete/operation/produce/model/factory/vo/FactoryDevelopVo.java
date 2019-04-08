package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-04-08 9:03
 * @description
 */
@Data
@ApiModel("新建厂房 选择视图")
public class FactoryDevelopVo {

    @ApiModelProperty("代理主键，在生产计划中值同FactoryHoldingInfo，在厂房管理时值同FactoryBasicInfo")
    private Long id;

    @ApiModelProperty("厂房类型")
    private String factoryType;

    @ApiModelProperty("建造这个厂房需要的周期")
    private Integer factoryMakePeriod;

    @ApiModelProperty("在建造过程中，每个周期需要支付的建造费用")
    private Double factoryMakeCost;

    @ApiModelProperty("厂房建好后每期的维护费用，完工当期不支付维护费用")
    private Double factoryMaintainCost;

    @ApiModelProperty("厂房建造后，每期折旧的价值。完工当期不折旧")
    private Double factoryDepreciation;

    @ApiModelProperty("厂房可以容纳的生产线数量")
    private Integer factoryCapacity;
}
