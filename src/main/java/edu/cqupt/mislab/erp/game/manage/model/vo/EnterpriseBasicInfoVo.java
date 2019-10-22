package edu.cqupt.mislab.erp.game.manage.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yuanyiwen
 * @create 2019-10-22 18:50
 * @description 仅记录企业最基本信息的展示
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("企业基本信息展示视图")
public class EnterpriseBasicInfoVo {

    @ApiModelProperty(value = "企业id")
    private Long enterpriseId;

    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;
}
