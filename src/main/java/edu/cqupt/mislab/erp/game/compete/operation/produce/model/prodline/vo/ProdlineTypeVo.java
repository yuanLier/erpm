package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-03-08 17:27
 * @description
 */
@Data
@ApiModel("生产线类型选择视图")
public class ProdlineTypeVo {

    @ApiModelProperty("代理主键，在生产计划中值同ProdlineHoldingInfo，在厂房管理时值同ProdlineBasicInfo")
    private Long id;

    @ApiModelProperty("生产线类型")
    private String prodlineType;
}
