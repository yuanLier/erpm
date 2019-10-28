package edu.cqupt.mislab.erp.game.manage.model.dto;

import edu.cqupt.mislab.erp.commons.validators.annotations.UserStatusValid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author chuyunfei
 * @description
 * @date 20:47 2019/4/26
 **/

@Data
@ApiModel("比赛创建数据类")
public class GameCreateDto {

    @NotBlank(message = "比赛名称不能为空字符串")
    @NotNull(message = "比赛名称不能为null")
    @ApiModelProperty(value = "比赛名称",required = true)
    private String gameName;

    @Min(1L)
    @NotNull(message = "创建者不能为null")
    @UserStatusValid(isEnable = true)
    @ApiModelProperty(value = "创建者ID",required = true)
    private Long creatorId;

    @Min(1)
    @NotNull(message = "企业最大个数不能为null")
    @ApiModelProperty(value = "比赛的最大企业数，最小值为1",required = true)
    private Integer maxEnterpriseNumber;
}
