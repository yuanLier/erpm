package edu.cqupt.mislab.erp.game.compete.operation.material.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-10-08 22:14
 * @description 材料类型展示视图，用于管理端产品材料信息的展示
 */
@Data
@ApiModel("材料类型展示视图")
public class MaterialTypeVo {

    @ApiModelProperty(value = "代理主键")
    private Long id;

    @ApiModelProperty(value = "原料的名称，所有相同原料名称数据中")
    private String materialName;

}
