package edu.cqupt.mislab.erp.game.manage.model.dto;

import edu.cqupt.mislab.erp.commons.validators.annotations.GameStatusValid;
import edu.cqupt.mislab.erp.commons.validators.annotations.UserStatusValid;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author chuyunfei
 * @description 
 * @date 20:50 2019/4/26
 **/

@ApiModel("创建企业的数据实体")
@Data
public class EnterpriseCreateDto {

    @NotNull
    @ApiModelProperty(value = "企业的名称",required = true)
    private String enterpriseName;

    @Min(1)
    @NotNull
    @ApiModelProperty(value = "该企业的最大人员数量",required = true)
    private Integer maxMemberNumber;

    @Min(1L)
    @NotNull
    @UserStatusValid(isEnable = true)
    @ApiModelProperty(value = "创建者的ID",required = true)
    private Long creatorId;

    @Min(1L)
    @NotNull
    @GameStatusValid(requireStatus = GameStatus.CREATE)
    @ApiModelProperty(value = "那一场比赛",required = true)
    private Long gameId;
}