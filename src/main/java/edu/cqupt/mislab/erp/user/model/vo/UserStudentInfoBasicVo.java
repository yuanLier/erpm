package edu.cqupt.mislab.erp.user.model.vo;

import edu.cqupt.mislab.erp.user.model.entity.MajorInfo;
import edu.cqupt.mislab.erp.user.model.entity.UserGender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("学生用户信息的基本视图")
public class UserStudentInfoBasicVo {

    @ApiModelProperty("代理主键")
    private Long id;

    @ApiModelProperty("学号，作为系统账号")
    private String studentCount;

    @ApiModelProperty("真实姓名")
    private String studentName;

    @ApiModelProperty("性别")
    private UserGender gender;

    @ApiModelProperty("专业信息，包含专业和学员")
    private MajorInfo majorInfo;

    @ApiModelProperty("班级")
    private String studentClass;

    @ApiModelProperty("电子邮箱")
    private String email;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("教师信息")
    private Long teacherId;

    @ApiModelProperty("头像位置信息")
    private String avatarLocation;
}
