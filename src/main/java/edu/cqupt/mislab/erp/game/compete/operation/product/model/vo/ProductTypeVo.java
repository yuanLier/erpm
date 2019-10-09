package edu.cqupt.mislab.erp.game.compete.operation.product.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @description 
 **/

@Data
@ApiModel("产品类型选择视图")
public class ProductTypeVo {

    @ApiModelProperty("代理主键，生产计划中值同ProductDevelopInfoId；管理端值同productBasicId")
    private Long id;

    @ApiModelProperty("用户已生产的产品类型")
    private String productName;
}
