package edu.cqupt.mislab.erp.game.manage.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel("创建企业的数据实体")
@Data
public class EnterpriseCreateDto {

    @NotNull
    @ApiModelProperty("企业的名称")
    private String enterpriseName;

    @Min(1)
    @NotNull
    @ApiModelProperty("该企业的最大人员数量")
    private Integer maxMemberNumber;

    @NotNull
    @Min(1L)
    @ApiModelProperty("创建者的ID")
    private Long creatorId;

    @NotNull
    @Min(1L)
    @ApiModelProperty("那一场比赛")
    private Long gameId;
}