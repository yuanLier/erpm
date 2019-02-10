package edu.cqupt.mislab.erp.game.manage.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class EnterpriseMemberDisplayVo {

    @ApiModelProperty("代理主键")
    private Long id;

    @ApiModelProperty("学号")
    private String studentAccount;

    @ApiModelProperty("真实姓名")
    private String studentName;

    @ApiModelProperty("学院信息")
    private String college;

    @ApiModelProperty("专业名称")
    private String major;

    @ApiModelProperty("成员的贡献率，按100分计算")
    private Integer gameContributionRate;

    @ApiModelProperty("头像位置信息")
    private String avatarLocation;
}
