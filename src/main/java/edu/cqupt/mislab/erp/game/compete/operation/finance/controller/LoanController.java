package edu.cqupt.mislab.erp.game.compete.operation.finance.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.finance.dao.LoanEnterpriseRepository;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.dto.LoanEnterpriseDto;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.dto.LoanSelectDto;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.vo.LoanBasicDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.vo.LoanEnterpriseDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.finance.service.LoanService;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
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
 * @create 2019-07-11 21:55
 * @description
 */
@Api(description = "学生端-贷款相关")
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/loan")
public class LoanController {


    @Autowired
    private LoanService loanService;


    @ApiOperation(value = "获取一场比赛中的全部可贷款类型及相关信息")
    @GetMapping("type")
    public WebResponseVo<List<LoanBasicDisplayVo>> getAllLoanBasicDisplayInfo(@Exist(repository = GameBasicInfoRepository.class)
                                                                                          @RequestParam Long gameId) {

        List<LoanBasicDisplayVo> loanBasicDisplayVoList = loanService.getAllLoanBasicDisplayInfo(gameId);

        if(loanBasicDisplayVoList.size() == 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "无可用的贷款信息，请联系管理员");
        }


        return toSuccessResponseVoWithData(loanBasicDisplayVoList);
    }


    @ApiOperation(value = "提交贷款")
    @PostMapping("submit")
    public WebResponseVo<LoanEnterpriseDisplayVo> submitEnterpriseLoan(@RequestBody LoanEnterpriseDto loanEnterpriseDto) {

        LoanEnterpriseDisplayVo loanEnterpriseDisplayVo = loanService.submitEnterpriseLoan(loanEnterpriseDto);

        if(loanEnterpriseDisplayVo == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "提交失败，请检查参数信息");
        }

        return toSuccessResponseVoWithData(loanEnterpriseDisplayVo);
    }


    @ApiOperation(value = "归还贷款")
    @GetMapping("repay")
    public WebResponseVo<LoanEnterpriseDisplayVo> repayEnterpriseLoan(@Exist(repository = LoanEnterpriseRepository.class)
                                                                                  @RequestParam Long loanEnterpriseId) {

        return toSuccessResponseVoWithData(loanService.repayEnterpriseLoan(loanEnterpriseId));
    }


    @ApiOperation(value = "贷款类型与还款状态的组合查询，参数为空表示查询该属性下所有信息")
    @PostMapping
    public WebResponseVo<List<LoanEnterpriseDisplayVo>> getLoansOfEnterprise(@RequestBody LoanSelectDto loanSelectDto) {

        return toSuccessResponseVoWithData(loanService.getLoansOfEnterprise(loanSelectDto));
    }

}
