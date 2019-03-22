package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo;

import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineDevelopStatus;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineProduceStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-03-10 15:27
 * @description
 */
@Data
@ApiModel("生产线生产信息展示视图")
public class ProdlineProduceDisplayVo {

    @ApiModelProperty("代理主键，值同ProdlineHoldingInfo")
    private Long id;

    @ApiModelProperty("展示为生产线名称，实际指生产线类型")
    private String prodlineType;

    @ApiModelProperty("生产线生产状态")
    private ProdlineProduceStatus prodlineProduceStatus;

    @ApiModelProperty("已生产周期数")
    private Integer producedPeriod;

    @ApiModelProperty("该生产线当前可生产的产品名称")
    private String productName;

}
