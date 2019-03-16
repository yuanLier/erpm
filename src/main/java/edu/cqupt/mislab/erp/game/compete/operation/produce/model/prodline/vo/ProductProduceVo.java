package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-03-12 21:45
 * @description
 */
@Data
@ApiModel("产品生产信息视图")
public class ProductProduceVo {

    @ApiModelProperty("代理主键，值同ProdlineProduceInfo")
    private Long id;

    @ApiModelProperty("厂房编号")
    private String factoryNumber;

    @ApiModelProperty("生产线类型")
    private String prodlineType;
}
