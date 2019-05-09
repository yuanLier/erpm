package edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chuyunfei
 * @description
 **/

@Data
@ApiModel("ISO基本数据视图")
public class IsoBasicVo {

    @ApiModelProperty(value = "新ISO认证资格的id")
    private Long id;

    @ApiModelProperty(value = "ISO认证资格的名称")
    private String isoName;

    @ApiModelProperty(value = "完成ISO认证所需的周期数")
    private Integer isoResearchPeriod;

    @ApiModelProperty(value = "在开拓ISO认证过程中，每个周期需要支付的费用")
    private Double isoResearchCost;

    @ApiModelProperty(value = "ISO认证完成后，维持该认证每个周期需要支付的费用")
    private Double isoMaintainCost;

    @ApiModelProperty(value = "该认证对订单价格的影响程度，每一个产品价格影响")
    private Double extraValue;

    @ApiModelProperty(value = "该认证的默认初始状态")
    private IsoStatusEnum isoStatus;

    @ApiModelProperty(value = "该条设置是否启用")
    private boolean enable;

}
