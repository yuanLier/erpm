package edu.cqupt.mislab.erp.game.compete.operation.finance.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.vo.FinanceEnterpriseVo;
import edu.cqupt.mislab.erp.game.compete.operation.finance.service.FinanceService;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toFailResponseVoWithMessage;
import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithData;

/**
 * @author yuanyiwen
 * @create 2019-05-22 9:10
 * @description
 */
@Api(description = "学生端-财务相关")
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/finance")
public class FinanceController {

    @Autowired
    FinanceService financeService;

    @ApiOperation(value = "获取某一企业的全部财务记录")
    @GetMapping("/all")
    public WebResponseVo<List<FinanceEnterpriseVo>> getAllFinanceInfoOfEnterprise(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                                      @RequestParam Long enterpriseId) {

        List<FinanceEnterpriseVo> financeEnterpriseVoList = financeService.getAllFinanceInfoOfEnterprise(enterpriseId);

        if (financeEnterpriseVoList == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "该企业对应的财务记录为空");
        }

        return toSuccessResponseVoWithData(financeEnterpriseVoList);
    }


    @ApiOperation(value = "获取某一企业的当前（最新）财务信息")
    @GetMapping("/current")
    public WebResponseVo<FinanceEnterpriseVo> getCurrentFinanceInfo(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                        @RequestParam Long enterpriseId) {

        FinanceEnterpriseVo financeEnterpriseVo = financeService.getCurrentFinanceInfo(enterpriseId);

        if(financeEnterpriseVo == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "企业当前财务信息不存在");
        }

        return toSuccessResponseVoWithData(financeEnterpriseVo);
    }

}
