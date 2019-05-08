package edu.cqupt.mislab.erp.game.compete.operation.iso.model.dto;

import edu.cqupt.mislab.erp.commons.validators.annotations.DoubleMin;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author chuyunfei
 * @description
 **/

@Data
@ApiModel("ISO基本数据视图")
public class IsoBasicDto {

    @Size(min = 1, max = 50)
    @NotNull
    @ApiModelProperty(value = "ISO认证资格的名称",required = true)
    private String isoName;

    @Min(1)
    @NotNull
    @ApiModelProperty(value = "完成ISO认证所需的周期数",required = true)
    private Integer isoResearchPeriod;

    @Min(0)
    @NotNull
    @ApiModelProperty(value = "在开拓ISO认证过程中，每个周期需要支付的费用",required = true)
    private Double isoResearchCost;

    @Min(0)
    @NotNull
    @ApiModelProperty(value = "ISO认证完成后，维持该认证每个周期需要支付的费用",required = true)
    private Double isoMaintainCost;

    @DoubleMin(0)
    @Max(1)
    @NotNull
    @ApiModelProperty(value = "该认证对订单价格的影响程度，每一个产品价格影响",required = true)
    private Double extraValue;

    @NotNull
    @ApiModelProperty(value = "该认证的默认初始状态", required = true)
    private IsoStatusEnum isoStatus;
}
