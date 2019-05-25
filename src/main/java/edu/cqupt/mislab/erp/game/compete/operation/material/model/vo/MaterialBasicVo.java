package edu.cqupt.mislab.erp.game.compete.operation.material.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-05-24 19:26
 * @description
 */

@Data
@ApiModel("材料基本信息视图")
public class MaterialBasicVo {

    @ApiModelProperty(value = "代理主键")
    private Long id;

    @ApiModelProperty(value = "原料的名称，所有相同原料名称数据中，enable=false标识历史数据，反之为最新数据，每一个name最多一个enable=true")
    private String materialName;

    @ApiModelProperty(value = "原料的价格,该值必须大于0")
    private double materialPrice;

    @ApiModelProperty(value = "指原料从采购开始到原料运到仓库，需要等待的周期数，该值必须大于0")
    private int materialDelayTime;

    @ApiModelProperty(value = "该数据是否被启用，当前最新数据是启用，所有的历史数据均为未启用，必须保证同一个材料信息最多只有一个Enable = true")
    private boolean enable;

}
