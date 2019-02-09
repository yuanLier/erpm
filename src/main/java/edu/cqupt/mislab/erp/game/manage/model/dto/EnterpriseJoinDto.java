package edu.cqupt.mislab.erp.game.manage.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("用户加入某个企业")
public class EnterpriseJoinDto {

    @NotNull
    @Min(1L)
    @ApiModelProperty("哪一个比赛")
    private Long gameId;

    @NotNull
    @Min(1L)
    @ApiModelProperty("哪一个企业")
    private Long enterpriseId;

    @NotNull
    @Min(1L)
    @ApiModelProperty("哪一个用户")
    private Long userId;
}
