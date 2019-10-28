package edu.cqupt.mislab.erp.user.model.dto;

import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.user.dao.MajorBasicInfoRepository;
import edu.cqupt.mislab.erp.user.dao.UserAvatarRepository;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import edu.cqupt.mislab.erp.user.model.entity.UserGenderEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 * @author chuyunfei
 * @description 
 * @date 21:07 2019/4/22
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用于修改学生基本信息的Dto")
public class UserStudentInfoUpdateDto {

    @NonNull
    @Exist(repository = UserStudentRepository.class,message = "更改的这个user必须存在才有意义")
    @Min(value = 1L)
    @ApiModelProperty(value = "代理主键，必须要这个",required = true)
    private Long id;

    @ApiModelProperty("性别")
    private UserGenderEnum gender;

    @Exist(repository = MajorBasicInfoRepository.class)
    @ApiModelProperty("学生的专业学院信息")
    private Long majorInfoId;

    @ApiModelProperty("班级")
    private String studentClass;

    @Email
    @ApiModelProperty("电子邮箱")
    private String email;

    @Pattern(regexp = "^1(?:3\\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\\d|9\\d)\\d{8}$",message = "支持移动联通电信哦，别乱填")
    @ApiModelProperty("电话，移动电话，支持移动、联通、电信")
    private String phone;

    @Exist(repository = UserAvatarRepository.class,message = "头像必须还是要存在的")
    @ApiModelProperty("头像")
    private Long userAvatarInfoId;
}
