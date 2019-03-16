package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo;

import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryDevelopStatus;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingStatus;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineDisplayVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

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

    @ApiModelProperty("展示为厂房编号，实际为id后三位（位数不足时用0补齐）")
    private String factoryNumber;

    @ApiModelProperty("展示为厂房规模，实际指厂房类型")
    private String factoryType;

    @ApiModelProperty("展示为厂房类型，实际指厂房拥有状态；除租赁状态外均展示为非租赁")
    private FactoryHoldingStatus factoryHoldingStatus;

    @ApiModelProperty("厂房中能容纳的最大生产线数量")
    private Integer factoryCapacity;

    @ApiModelProperty("展示为厂房状态，实际为厂房修建状态")
    private FactoryDevelopStatus factoryDevelopStatus;

    @ApiModelProperty("展示该厂房中的全部生产线信息")
    private List<ProdlineDisplayVo> prodlineDisplayVoList;
}
