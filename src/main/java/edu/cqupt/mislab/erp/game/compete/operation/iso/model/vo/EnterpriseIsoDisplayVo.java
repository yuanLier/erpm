package edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("企业认证信息的展示数据模型")
public class EnterpriseIsoDisplayVo {

    @ApiModelProperty("代理主键，是企业的ID")
    private Long id;

    @ApiModelProperty("还没有认证的信息")
    private List<IsoToDevelopInfoDisplayVo> toDevelopInfoDisplayVos;

    @ApiModelProperty("正在认证的信息")
    private List<IsoDevelopingInfoDisplayVo> developingInfoDisplayVos;

    @ApiModelProperty("已经认证的信息")
    private List<IsoDevelopedInfoDisplayVo> developedInfoDisplayVos;
}
