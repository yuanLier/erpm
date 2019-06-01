package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo;

import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-03-17 17:43
 * @description
 */
@Data
@ApiModel("查看厂房详情 展示视图")
public class FactoryDetailVo {

    @ApiModelProperty("代理主键，值同FactoryHoldingInfo")
    private Long id;

    @ApiModelProperty("展示为厂房编号，实际为id后三位（位数不足时用0补齐）")
    private String factoryNumber;

    @ApiModelProperty("展示为厂房规模，实际指厂房类型")
    private String factoryType;

    @ApiModelProperty("展示为厂房类型，实际指厂房拥有状态；除租赁状态外均展示为非租赁")
    private FactoryHoldingStatus factoryHoldingStatus;

    @ApiModelProperty("厂房中能容纳的最大生产线数量")
    private Integer factoryCapacity;

    @ApiModelProperty("展示为厂房状态，实际为自建的厂房状态 / 租来的厂房状态（true为拥有中 / 租赁中，false为已出售 / 暂停租赁）")
    private boolean developStatus;

    @ApiModelProperty("修建该厂房所需要的周期数")
    private Integer factoryMakePeriod;

    @ApiModelProperty("修建该厂房每期所需要的费用")
    private Double factoryMakeCost;

    @ApiModelProperty("厂房建造完成后每期的折旧费用")
    private Double factoryDepreciation;

    @ApiModelProperty("厂房修建完成后每期的维护费用")
    private Double factoryMaintainCost;

    @ApiModelProperty("不考虑折旧时，厂房售卖价值")
    private Double factoryValue;

    @ApiModelProperty("残值，即折旧到一定阶段后，无论怎么再折旧，都不再减少的价值")
    private Double factoryStumpCost;

}
