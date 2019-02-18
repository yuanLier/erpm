package edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@ApiModel("ISO基本数据视图")
public class IsoBasicInfoDisplayVo {

    @ApiModelProperty("代理主键")
    private Long id;

    @ApiModelProperty("ISO认证资格的名称")
    private String isoName;

    @ApiModelProperty("完成ISO认证的周期数")
    private Integer isoResearchPeriod;

    @ApiModelProperty("在开拓ISO认证过程中，每个周期需要支付的费用")
    private Double isoResearchCost;

    @ApiModelProperty("ISO认证完成后，维持该认证每个周期需要支付的费用")
    private Double isoMaintainCost;

    @ApiModelProperty("该认证对订单价格的影响程度，每一个产品价格影响")
    private Double extraValue;

    @ApiModelProperty("时间戳，用于版本控制")
    private Date timeStamp;
}
