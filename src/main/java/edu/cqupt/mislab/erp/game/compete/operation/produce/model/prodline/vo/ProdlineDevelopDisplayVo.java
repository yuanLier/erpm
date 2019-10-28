package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo;

import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineDevelopStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-03-17 17:02
 * @description
 */
@Data
@ApiModel("生产线修建信息展示视图")
public class ProdlineDevelopDisplayVo {

    @ApiModelProperty("代理主键，值同ProdlineHoldingInfo")
    private Long id;

    @ApiModelProperty("展示为生产线名称，实际指生产线类型")
    private String prodlineType;

    @ApiModelProperty("该生产线当前可生产的产品名称")
    private String productName;

    @ApiModelProperty("生产线安装状态")
    private ProdlineDevelopStatus prodlineDevelopStatus;

    @ApiModelProperty("已安装周期数")
    private Integer developedPeriod;

    @ApiModelProperty("安装所需的总周期数")
    private Integer prodlineSetupPeriod;
}
