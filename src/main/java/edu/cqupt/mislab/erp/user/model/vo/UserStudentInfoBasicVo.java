package edu.cqupt.mislab.erp.user.model.vo;

import edu.cqupt.mislab.erp.user.model.entity.MajorBasicInfo;
import edu.cqupt.mislab.erp.user.model.entity.UserAvatarInfo;
import edu.cqupt.mislab.erp.user.model.entity.UserGenderEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author chuyunfei
 * @description 
 * @date 21:07 2019/4/22
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("学生用户信息的基本视图")
public class UserStudentInfoBasicVo {

    @ApiModelProperty("代理主键")
    private Long id;

    @ApiModelProperty("学号，作为系统账号")
    private String studentAccount;

    @ApiModelProperty("真实姓名")
    private String studentName;

    @ApiModelProperty("性别")
    private UserGenderEnum gender;

    @ApiModelProperty("专业信息，包含专业和学员")
    private MajorBasicInfo majorBasicInfo;

    @ApiModelProperty("班级")
    private String studentClass;

    @ApiModelProperty("电子邮箱")
    private String email;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("教师信息")
    private Long teacherId;

    @ApiModelProperty("头像位置信息")
    private UserAvatarInfo userAvatarInfo;
}
