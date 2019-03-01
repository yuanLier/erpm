package edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("ISO数据展示视图")
public class IsoDisplayVo {

    @ApiModelProperty("代理主键，值同IsoDevelopInfo的主键")
    private Long id;

    @ApiModelProperty("ISO认证名称")
    private String isoName;

    @ApiModelProperty("认证总期数")
    private Integer isoResearchPeriod;

    @ApiModelProperty("每期认证费用")
    private Double isoResearchCost;

    @ApiModelProperty("每期维护费用")
    private Double isoMaintainCost;

    @ApiModelProperty("已经认证的周期数")
    private Integer researchedPeriod;

    @ApiModelProperty("当前认证状态")
    private IsoStatusEnum isoStatus;

}
