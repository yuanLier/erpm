package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author yuanyiwen
 * @create 2019-06-04 21:50
 * @description
 */
@Data
@ApiModel("生产线历史数据变动记录")
public class ProdlineOperationVo {

    @ApiModelProperty("造成变动的操作")
    private String operation;

    @ApiModelProperty("该操作面向的厂房-该厂房数量")
    private Map<ProdlineBasicVo, Integer> operationAmount;
}
