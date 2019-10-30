package edu.cqupt.mislab.erp.game.compete.operation.product.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yuanyiwen
 * @description 
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("产品类型选择视图")
public class ProductTypeVo {

    @ApiModelProperty("代理主键，值同ProductDevelopInfoId")
    private Long id;

    @ApiModelProperty("用户已生产的产品类型")
    private String productName;
}
