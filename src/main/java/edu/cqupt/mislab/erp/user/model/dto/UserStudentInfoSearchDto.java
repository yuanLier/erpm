package edu.cqupt.mislab.erp.user.model.dto;

import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.user.dao.UserTeacherRepository;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用于内部进行数据的搜索")
public class UserStudentInfoSearchDto {

    @ApiModelProperty("学号，作为系统账号")
    private String studentAccount;

    @ApiModelProperty("密码")
    private String studentPassword;

    @Exist(repository = UserTeacherRepository.class)
    @ApiModelProperty("教师编号")
    private Long teacherId;

    @ApiModelProperty("是否启用")
    private boolean accountEnable;
}
