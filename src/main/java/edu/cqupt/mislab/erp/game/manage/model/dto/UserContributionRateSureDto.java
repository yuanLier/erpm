package edu.cqupt.mislab.erp.game.manage.model.dto;

import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.commons.validators.annotations.UserStatusValid;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author chuyunfei
 * @description
 * @date 20:47 2019/4/26
 **/

@Data
@Builder
@ApiModel("用户企业成员的贡献度确认")
public class UserContributionRateSureDto {

    @NotNull
    @Min(1L)
    @UserStatusValid(isEnable = true)
    @ApiModelProperty(value = "企业创建者的ID",required = true)
    private Long creatorId;

    @NotNull
    @Min(1L)
    @Exist(repository = EnterpriseBasicInfoRepository.class)
    @ApiModelProperty(value = "企业的ID",required = true)
    private Long enterpriseId;

    @NotNull
    @ApiModelProperty(value = "每一个成员的贡献率",required = true)
    private Map<Long,Integer> rates;
}
