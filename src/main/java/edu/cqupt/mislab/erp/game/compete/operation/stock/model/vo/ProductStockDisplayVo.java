package edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-03-31 22:41
 * @description
 */
@Data
@ApiModel("企业中产品库存展示信息")
public class ProductStockDisplayVo {

    @ApiModelProperty("代理主键，值同ProductStockInfoId")
    private Long id;

    @ApiModelProperty("产品基本信息id")
    private Long ProductBasicId;

    @ApiModelProperty("产品名称")
    private String materialName;

    @ApiModelProperty("产品库存数量")
    private Integer materialNumber;

}
