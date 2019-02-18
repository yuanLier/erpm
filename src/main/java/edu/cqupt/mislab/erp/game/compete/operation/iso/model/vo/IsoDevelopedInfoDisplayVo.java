package edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("已经认证完成的ISO基本数据视图")
public class IsoDevelopedInfoDisplayVo {

    @ApiModelProperty("代理主键")
    private Long id;

    @ApiModelProperty("哪一个企业的ISO认证信息")
    private Long enterpriseId;

    @ApiModelProperty("ISO认证的基本信息")
    private IsoBasicInfoDisplayVo isoBasicInfoDisplayVo;

    @ApiModelProperty("认证开始的周期")
    private Integer developBeginPeriod;

    @ApiModelProperty("认证完成的周期")
    private Integer developEndPeriod;
}
