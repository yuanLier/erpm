package edu.cqupt.mislab.erp.game.manage.model.dto;

import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.commons.validators.annotations.UserStatusValid;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author chuyunfei
 * @description 
 * @date 20:50 2019/4/26
 **/

@Data
@ApiModel("用户加入某个企业")
public class EnterpriseJoinDto {

    @NotNull
    @Min(1L)
    @Exist(repository = GameBasicInfoRepository.class)
    @ApiModelProperty(value = "哪一个比赛",required = true)
    private Long gameId;

    @NotNull
    @Min(1L)
    @Exist(repository = EnterpriseBasicInfoRepository.class)
    @ApiModelProperty(value = "哪一个企业",required = true)
    private Long enterpriseId;

    @NotNull
    @Min(1L)
    @UserStatusValid(isEnable = true)
    @ApiModelProperty(value = "哪一个用户",required = true)
    private Long userId;

    @ApiModelProperty(value = "当这个接口用于运行时添加用户时，需要CEO的密码",required = false)
    private String password;
}
