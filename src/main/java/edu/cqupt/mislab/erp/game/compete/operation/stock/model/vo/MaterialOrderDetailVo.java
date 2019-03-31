package edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo;

import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.TransportBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.TransportStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-03-31 22:35
 * @description
 */
@Data
@ApiModel("原料入库详情展示信息，就是在MaterialDetailVo的基础上多了个总额和库存数")
public class MaterialOrderDetailVo {

    @ApiModelProperty("代理主键，值同MaterialOrderInfoId")
    private Long id;

    @ApiModelProperty("订单编号，值为id的后四位（有余则截位，不足则补0")
    private Long orderNumber;

    @ApiModelProperty("原材料名称")
    private String materialName;

    @ApiModelProperty("原材料库存数量")
    private Integer materialNumber;

    @ApiModelProperty("原材料单价")
    private Double materialPrice;

    @ApiModelProperty("原材料购买数量")
    private Integer purchaseNumber;

    @ApiModelProperty("该订单总额，值同materialPrice * purchaseNumber")
    private Double totalPrice;

    @ApiModelProperty("采购时间")
    private Integer purchaseTime;

    @ApiModelProperty("运输方式")
    private TransportBasicInfo transportMethod;

    @ApiModelProperty("开始运货的时间")
    private Integer transportTime;

    @ApiModelProperty("原材料运送状态")
    private TransportStatusEnum transportStatus;
}
