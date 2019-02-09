package edu.cqupt.mislab.erp.user.model.dto;

import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.user.dao.MajorInfoRepository;
import edu.cqupt.mislab.erp.user.dao.UserAvatarRepository;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import edu.cqupt.mislab.erp.user.model.entity.UserGender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用于修改学生基本信息的Dto")
public class UserStudentInfoUpdateDto {

    @NonNull
    @Exist(repository = UserStudentRepository.class)
    @Min(value = 1L)
    @ApiModelProperty(value = "代理主键，必须要这个",required = true)
    private Long id;

    @ApiModelProperty("性别")
    private UserGender gender;

    @Exist(repository = MajorInfoRepository.class)
    @ApiModelProperty("学生的专业学院信息")
    private Long majorInfoId;

    @ApiModelProperty("班级")
    private String studentClass;

    @Email
    @ApiModelProperty("电子邮箱")
    private String email;

    @Pattern(regexp = "^1(?:3\\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\\d|9\\d)\\d{8}$")
    @ApiModelProperty("电话，移动电话，支持移动、联通、电信")
    private String phone;

    @Exist(repository = UserAvatarRepository.class)
    @ApiModelProperty("头像")
    private Long userAvatarInfoId;
}
