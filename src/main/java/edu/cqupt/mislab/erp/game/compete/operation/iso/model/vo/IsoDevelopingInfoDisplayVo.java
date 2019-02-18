package edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("正在认证的ISO基本数据视图")
public class IsoDevelopingInfoDisplayVo {

    @ApiModelProperty("代理主键")
    private Long id;

    @ApiModelProperty("哪一个企业的ISO认证信息")
    private Long enterpriseId;

    @ApiModelProperty("ISO认证的基本信息")
    private IsoBasicInfoDisplayVo isoBasicInfoDisplayVo;

    @ApiModelProperty("认证开始的周期")
    private Integer developBeginPeriod;

    @ApiModelProperty("已经认证了多少个周期")
    private Integer researchedPeriod;

    @ApiModelProperty("当前周期是否继续进行认证")
    private Boolean continueResearch;
}
