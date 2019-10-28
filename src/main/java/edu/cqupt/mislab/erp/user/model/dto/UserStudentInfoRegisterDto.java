package edu.cqupt.mislab.erp.user.model.dto;

import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.user.dao.MajorBasicInfoRepository;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author chuyunfei
 * @description 
 * @date 21:07 2019/4/22
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用于学生用户的注册数据传输对象")
public class UserStudentInfoRegisterDto {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]{0,15}[0-9]{3,14}$",message = "学号，作为系统账号，长度为5-16的账号")
    @ApiModelProperty("学号，作为系统账号，长度为4-15的账号")
    private String studentAccount;

    @NotNull
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{0,}$",message = "真实姓名，汉字")
    @ApiModelProperty("真实姓名，汉字")
    private String studentName;

    @NotNull
    @Exist(repository = MajorBasicInfoRepository.class,message = "专业信息必须存在")
    @ApiModelProperty("学生的专业学院信息")
    private Long majorInfoId;

    @NotNull(message = "班级不能为空")
    @ApiModelProperty("班级")
    private String studentClass;

    @NotNull(message = "电话不能为空")
    @ApiModelProperty("电话")
    private String phone;

    @NotNull
    @Size(min = 6, max = 18)
    @Pattern(regexp = "^[A-Za-z0-9]+$",message = "密码，长度在6-18个之间，只能包含数字、字母")
    @ApiModelProperty("密码，长度在6-18个之间，只能包含数字、字母")
    private String studentPassword;

    @NotNull
    @Size(min = 6, max = 18)
    @Pattern(regexp = "^[A-Za-z0-9]+$",message = "重复密码必须和密码相同")
    @ApiModelProperty("密码，长度在6-18个之间，只能包含数字、字母")
    private String rePassword;
}
