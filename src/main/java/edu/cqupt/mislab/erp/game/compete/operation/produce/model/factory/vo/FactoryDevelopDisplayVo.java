package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo;

import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-03-24 14:47
 * @description
 */
@Data
@ApiModel("修建中的厂房信息展示视图")
public class FactoryDevelopDisplayVo {
    @ApiModelProperty("代理主键，值同FactoryDevelopInfo")
    private Long id;

    @ApiModelProperty("展示为厂房的临时编号，实际为id后三位（位数不足时用0补齐）")
    private String factoryNumber;

    @ApiModelProperty("展示为厂房规模，实际指厂房类型")
    private String factoryType;

    @ApiModelProperty("厂房中能容纳的最大生产线数量")
    private Integer factoryCapacity;

    @ApiModelProperty("展示为厂房的建造状态，true为建造中行你，false为暂停修建")
    private boolean developStatus;
}
