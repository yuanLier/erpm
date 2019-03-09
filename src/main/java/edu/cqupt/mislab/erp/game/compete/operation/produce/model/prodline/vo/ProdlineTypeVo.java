package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-03-08 17:27
 * @description
 */
@Data
@ApiModel("生产线类型选择视图")
public class ProdlineTypeVo {

    @ApiModelProperty("代理主键，值同ProdlineDevelopInfo")
    private Long id;

    @ApiModelProperty("用户拥有的生产线类型")
    private String prodlineType;
}
