package edu.cqupt.mislab.erp.game.compete.operation.advance;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.basic.impl.ModelAdvanceService;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toFailResponseVoWithMessage;
import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithData;

/**
 * @author yuanyiwen
 * @create 2019-07-09 21:56
 * @description 这是对外暴露的周期推进的接口，“进入下一周期”
 */
@Api(description = "学生端-周期推进")
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/advance")
public class AdvanceController {

    @Autowired
    private ModelAdvanceService modelAdvanceService;

    @ApiOperation(value = "企业进入下一周期")
    @GetMapping
    public WebResponseVo advance(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                     @RequestParam Long enterpriseId) {

        EnterpriseBasicInfo enterpriseBasicInfo = modelAdvanceService.advance(enterpriseId);

        // 若不允许推进，即还有企业未进入下一年
        if(enterpriseBasicInfo == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.FORBIDDEN, "比赛将在全部企业完成推进后统一进入下一年，请耐心等待");
        }

        // 否则，推进成功
        Integer currentPeriod = enterpriseBasicInfo.getEnterpriseCurrentPeriod();
        Integer currentYear = enterpriseBasicInfo.getGameBasicInfo().getGameCurrentYear();
        Integer totalPeriod = enterpriseBasicInfo.getGameBasicInfo().getGameInitBasicInfo().getPeriodOfOneYear();
        Integer periodOfYear = (currentPeriod % totalPeriod == 0) ? totalPeriod : currentPeriod % totalPeriod;

        return toSuccessResponseVoWithData("推进成功，当前企业处于第" + currentYear + "年第" + periodOfYear + "周期");
    }
}
