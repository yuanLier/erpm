package edu.cqupt.mislab.erp.user.model.dto;

import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.commons.validators.annotations.NameFormat;
import edu.cqupt.mislab.erp.user.dao.MajorInfoRepository;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用于学生用户的注册数据传输对象")
public class UserStudentInfoRegisterDto {

    @NotNull
    @Pattern(regexp = "^S[a-zA-Z]{0,15}[0-9]{4,15}$",message = "学号，作为系统账号，以S开头的长度为5-16的账号")
    @ApiModelProperty("学号，作为系统账号，以S开头的长度为5-16的账号")
    private String studentAccount;

    @NotNull
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{0,}$",message = "真实姓名，汉字")
    @ApiModelProperty("真实姓名，汉字")
    private String studentName;

    @NotNull
    @Exist(repository = MajorInfoRepository.class)
    @ApiModelProperty("学生的专业学院信息")
    private Long majorInfoId;

    @NotNull
    @ApiModelProperty("班级")
    private String studentClass;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]\\w{5,17}$",message = "密码，长度在6-18个之间，只能包含数字、字母、下划线，以字母开头")
    @ApiModelProperty("密码，长度在6-18个之间，只能包含数字、字母、下划线，以字母开头")
    private String studentPassword;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]\\w{5,17}$")
    @ApiModelProperty("密码，长度在6-18个之间，只能包含数字、字母、下划线，以字母开头")
    private String rePassword;
}
