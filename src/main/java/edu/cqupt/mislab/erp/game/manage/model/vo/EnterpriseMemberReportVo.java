package edu.cqupt.mislab.erp.game.manage.model.vo;

import edu.cqupt.mislab.erp.user.model.vo.UserStudentInfoBasicVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-10-20 15:07
 * @description
 */
@Data
@ApiModel("用户实验报告展示视图")
public class EnterpriseMemberReportVo {

    @ApiModelProperty(value = "代理主键，值同enterpriseMemberId")
    private Long id;

    @ApiModelProperty(value = "该报告隶属于哪个学生")
    private UserStudentInfoBasicVo studentInfoBasicVo;

    @ApiModelProperty(value = "文件存储路径；直接访问该路径即可访问或下载资源")
    private String reportLocation;
}
