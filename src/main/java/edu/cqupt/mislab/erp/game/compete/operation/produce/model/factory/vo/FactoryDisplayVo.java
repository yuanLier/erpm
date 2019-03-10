package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo;

import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryDevelopStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-03-10 15:27
 * @description
 */
@Data
@ApiModel("厂房信息展示视图")
public class FactoryDisplayVo {

    @ApiModelProperty("代理主键，值同FactoryDevelopInfo")
    private Long id;

    @ApiModelProperty("厂房编号")
    private String factoryNumber;

    @ApiModelProperty("厂房规模，即厂房类型")
    private String factoryType;

    @ApiModelProperty("厂房中能容纳的最大生产线数量")
    private Integer factoryCapacity;

    // todo 租赁状态和修建状态现在是合在一起的 也许不需要分开？原型图中对应的类型部分暂时用状态表示 如果表分开了就把这里也改了
    @ApiModelProperty("厂房状态")
    private FactoryDevelopStatus factoryDevelopStatus;
}
