package edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-03-31 21:54
 * @description
 */
@Data
@ApiModel("企业中原材料库存展示信息")
public class MaterialStockDisplayVo {

    @ApiModelProperty("代理主键，值同MaterialStockInfoId")
    private Long id;

    @ApiModelProperty("原材料基本信息id")
    private Long MaterialBasicId;

    @ApiModelProperty("原材料名称")
    private String materialName;

    @ApiModelProperty("原材料库存数量")
    private Integer materialNumber;

    @ApiModelProperty("原材料单价")
    private Double materialPrice;

}
