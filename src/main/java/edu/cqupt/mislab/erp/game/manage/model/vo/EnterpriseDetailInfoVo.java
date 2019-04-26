package edu.cqupt.mislab.erp.game.manage.model.vo;

import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chuyunfei
 * @description 
 * @date 20:41 2019/4/26
 **/

@Data
@ApiModel("企业信息展示模型")
public class EnterpriseDetailInfoVo {

    @ApiModelProperty("代理主键")
    private Long id;

    @ApiModelProperty("这个企业在哪一场比赛里面")
    private Long gameId;

    @ApiModelProperty("企业的名称")
    private String enterpriseName;

    @ApiModelProperty("企业的创建者名称")
    private String ceoName;

    @ApiModelProperty("企业的创建者Id")
    private Long ceoId;

    @ApiModelProperty("企业成员的个数")
    private Integer enterpriseMemberNumber;

    @ApiModelProperty("企业所处于的一个状态")
    private EnterpriseStatus enterpriseStatus;
}
