package edu.cqupt.mislab.erp.game.compete.operation.product.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-09-04 11:22
 * @description
 */
@Data
@ApiModel("产品基本类型视图")
@AllArgsConstructor
public class ProductBasicTypeVo {

    @ApiModelProperty("代理主键，值同ProductBasicInfoId")
    private Long id;

    @ApiModelProperty("产品类型")
    private String productName;
}
