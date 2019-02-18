package edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("为认证的ISO基本数据视图")
public class IsoToDevelopInfoDisplayVo {

    @ApiModelProperty("代理主键")
    private Long id;

    @ApiModelProperty("哪一个企业的ISO认证信息")
    private Long enterpriseId;

    @ApiModelProperty("ISO认证的基本信息")
    private IsoBasicInfoDisplayVo isoBasicInfoDisplayVo;
}
