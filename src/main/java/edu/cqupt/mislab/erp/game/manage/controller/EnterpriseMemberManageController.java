package edu.cqupt.mislab.erp.game.manage.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseUtil;
import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.EnterpriseStatusValid;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.commons.validators.annotations.UserStatusValid;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.dto.EnterpriseJoinDto;
import edu.cqupt.mislab.erp.game.manage.model.dto.UserContributionRateSureDto;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseStatusEnum;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseDetailInfoVo;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseMemberDisplayVo;
import edu.cqupt.mislab.erp.game.manage.service.EnterpriseMemberManageService;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author chuyunfei
 * @description
 * @date 22:28 2019/4/26
 **/

@Api
@Validated
@CrossOrigin
@RestController
@RequestMapping("/game/manage/enterprise/member")
public class EnterpriseMemberManageController {

    @Autowired private EnterpriseMemberManageService enterpriseMemberManageService;

    @ApiOperation("用户加入一个企业")
    @PostMapping("/join")
    public WebResponseVo<Long> joinOneEnterprise(
            @Valid @RequestBody EnterpriseJoinDto joinDto){

        return enterpriseMemberManageService.joinOneEnterprise(joinDto);
    }

    @ApiOperation("用户退出一个企业")
    @DeleteMapping("/out")
    public WebResponseVo<String> outOneEnterprise(
            @UserStatusValid(isEnable = true) @RequestParam Long userId,
            @EnterpriseStatusValid(enterpriseStatus = EnterpriseStatusEnum.CREATE) @RequestParam Long enterpriseId){

        return enterpriseMemberManageService.outOneEnterprise(userId,enterpriseId);
    }

    @ApiOperation("获取指定企业的全部成员的详细信息")
    @GetMapping("/enterpriseMemberInfos/get")
    public WebResponseVo<List<EnterpriseMemberDisplayVo>> getOneEnterpriseMemberInfos(
            @Exist(repository = EnterpriseBasicInfoRepository.class) @RequestParam Long enterpriseId){

        List<EnterpriseMemberDisplayVo> displayVos = enterpriseMemberManageService.getOneEnterpriseMemberInfos(enterpriseId);

        if(displayVos == null){

            return WebResponseUtil.toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND,"该企业还没有成员信息");
        }

        return WebResponseUtil.toSuccessResponseVoWithData(displayVos);
    }

    @ApiOperation("确定成员的贡献度")
    @PostMapping("/enterpriseMember/gameContributionRate/sure")
    public WebResponseVo<String> sureGameContributionRate(
            @Valid @RequestBody UserContributionRateSureDto rateSureDto){

        return enterpriseMemberManageService.sureGameContributionRate(rateSureDto);
    }

    @ApiOperation("获取用户处于比赛中的哪个企业")
    @GetMapping("/enterpriseMember/enterprise")
    public WebResponseVo<EnterpriseDetailInfoVo> getEnterpriseByMember(@Exist(repository = UserStudentRepository.class)
                                                                                   @RequestParam Long userId,
                                                                       @Exist(repository = GameBasicInfoRepository.class)
                                                                               @RequestParam Long gameId) {

        EnterpriseDetailInfoVo enterpriseDetailInfoVo = enterpriseMemberManageService.getEnterpriseOfMember(userId, gameId);

        if(enterpriseDetailInfoVo == null) {
            return WebResponseUtil.toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "用户未加入该企业");
        }

        return WebResponseUtil.toSuccessResponseVoWithData(enterpriseDetailInfoVo);
    }
}
