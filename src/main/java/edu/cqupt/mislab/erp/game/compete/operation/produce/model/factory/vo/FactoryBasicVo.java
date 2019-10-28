package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-06-02 20:09
 * @description
 */
@Data
@ApiModel("厂房基本数据视图")
public class FactoryBasicVo {

    @ApiModelProperty("代理主键")
    private Long id;

    @ApiModelProperty("厂房类型")
    private String factoryType;

    @ApiModelProperty("建造这个厂房需要的周期")
    private int factoryMakePeriod;

    @ApiModelProperty("在建造过程中，每个周期需要支付的建造费用")
    private double factoryMakeCost;

    @ApiModelProperty("厂房建造后，每期折旧的价值。完工当期不折旧")
    private double factoryDepreciation;

    @ApiModelProperty("不考虑折旧时，厂房售卖价值")
    private double factoryValue;

    @ApiModelProperty("残值，即折旧到一定阶段后，无论怎么再折旧，都不再减少的价值")
    private double factoryStumpCost;

    @ApiModelProperty("厂房可以容纳的生产线数量")
    private int factoryCapacity;

    @ApiModelProperty("租用该厂房时，每期需要交纳的租金")
    private double factoryRentCost;

    @ApiModelProperty("出售厂房后延迟多少个账期买房资金到账")
    private int factoryDelayTime;

    @ApiModelProperty("厂房建好后每期的维护费用，完工当期不支付维护费用")
    private double factoryMaintainCost;
}
