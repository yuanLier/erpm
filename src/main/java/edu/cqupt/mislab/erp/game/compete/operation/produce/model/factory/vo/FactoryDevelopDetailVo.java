package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo;

import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-03-17 21:29
 * @description
 */
@Data
@ApiModel("查看修建中的厂房详情 展示视图")
public class FactoryDevelopDetailVo {

    @ApiModelProperty("代理主键，值同FactoryHoldingInfo")
    private Long id;

    @ApiModelProperty("展示为厂房编号，实际为id后三位（位数不足时用0补齐）")
    private String factoryNumber;

    @ApiModelProperty("展示为厂房规模，实际指厂房类型")
    private String factoryType;

    @ApiModelProperty("展示为厂房类型，实际指厂房拥有状态；除租赁状态外均展示为非租赁")
    private FactoryHoldingStatus factoryHoldingStatus;

    @ApiModelProperty("厂房中当前生产线数量")
    private Integer currentCapacity;

    @ApiModelProperty("展示为厂房状态，实际为厂房修建状态")
    private boolean developStatus;

    @ApiModelProperty("开始修建的周期数")
    private Integer beginPeriod;

    @ApiModelProperty("修建该厂房所需要的周期数")
    private Integer factoryMakePeriod;

    @ApiModelProperty("已经修建了多少周期数")
    private Integer developedPeriod;
}
