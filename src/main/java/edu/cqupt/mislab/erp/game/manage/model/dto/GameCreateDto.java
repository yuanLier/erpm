package edu.cqupt.mislab.erp.game.manage.model.dto;

import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel("比赛创建数据类")
@Data
public class GameCreateDto {

    @NotBlank
    @NotNull
    @ApiModelProperty(value = "比赛名称",required = true)
    private String gameName;

    @NotNull
    @Min(1L)
    @Exist(repository = UserStudentRepository.class)
    @ApiModelProperty(value = "创建者ID",required = true)
    private Long creatorId;

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "比赛的最大企业数，最小值为1",required = true)
    private Integer maxEnterpriseNumber;
}
