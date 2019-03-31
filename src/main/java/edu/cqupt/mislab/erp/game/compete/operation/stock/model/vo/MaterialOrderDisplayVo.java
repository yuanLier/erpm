package edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo;

import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.TransportBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.TransportStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-03-31 22:21
 * @description
 */
@Data
@ApiModel("材料订单确认生成后的展示信息")
public class MaterialOrderDisplayVo {

    @ApiModelProperty("代理主键，值同MaterialOrderInfoId")
    private Long id;

    @ApiModelProperty("订单编号，值为id的后四位（有余则截位，不足则补0")
    private Long orderNumber;

    @ApiModelProperty("原材料名称")
    private String materialName;

    @ApiModelProperty("原材料单价")
    private Double materialPrice;

    @ApiModelProperty("原材料购买数量")
    private Integer purchaseNumber;

    @ApiModelProperty("采购时间")
    private Integer purchaseTime;

    @ApiModelProperty("运输方式")
    private TransportBasicInfo transportMethod;

    @ApiModelProperty("开始运货的时间")
    private Integer transportTime;

    @ApiModelProperty("原材料运送状态")
    private TransportStatusEnum transportStatus;

}
