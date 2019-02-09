package edu.cqupt.mislab.erp.game.manage.model.dto;

import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("用户加入某个企业")
public class EnterpriseJoinDto {

    @NotNull
    @Min(1L)
    @Exist(repository = GameBasicInfoRepository.class)
    @ApiModelProperty("哪一个比赛")
    private Long gameId;

    @NotNull
    @Min(1L)
    @Exist(repository = EnterpriseBasicInfoRepository.class)
    @ApiModelProperty("哪一个企业")
    private Long enterpriseId;

    @NotNull
    @Min(1L)
    @Exist(repository = UserStudentRepository.class)
    @ApiModelProperty("哪一个用户")
    private Long userId;
}
