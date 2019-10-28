package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo;

import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingStatus;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineDevelopDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineProduceDisplayVo;
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

    @ApiModelProperty("代理主键，值同FactoryHoldingInfo")
    private Long id;

    @ApiModelProperty("展示为厂房编号，实际为id后三位（位数不足时用0补齐）")
    private String factoryNumber;

    @ApiModelProperty("展示为厂房规模，实际指厂房类型")
    private String factoryType;

    @ApiModelProperty("展示为厂房类型，实际指厂房拥有状态 ：holding自建的 / leasing租来的")
    private FactoryHoldingStatus factoryHoldingStatus;

    @ApiModelProperty("厂房中能容纳的最大生产线数量")
    private Integer factoryCapacity;

    @ApiModelProperty("厂房中当前容纳的生产线数量")
    private Integer currentCapacity;

    @ApiModelProperty("展示为厂房状态，实际为自建的厂房状态 / 租来的厂房状态（true为拥有中 / 租赁中，false为已出售 / 暂停租赁）")
    private boolean developStatus;

    @ApiModelProperty("展示该厂房中的全部生产状态生产线信息")
    private List<ProdlineProduceDisplayVo> prodlineProduceDisplayVoList;

    @ApiModelProperty("展示该厂房中的全部修建状态生产线信息")
    private List<ProdlineDevelopDisplayVo> prodlineDevelopDisplayVoList;
}
