package edu.cqupt.mislab.erp.game.compete.operation.product.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-16 21:14
 * @description
 */

@Data
@ApiModel("产品历史数据展示视图")
public class ProductHistoryVo {

    @ApiModelProperty("哪个企业")
    private Long enterpriseId;

    @ApiModelProperty("哪个周期")
    private Integer period;

    @ApiModelProperty("截止到该周期，企业拥有的产品")
    private List<ProductBasicVo> productBasicVoList;
}