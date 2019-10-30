package edu.cqupt.mislab.erp.game.compete.operation.finance.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.finance.dao.LoanBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.dto.LoanBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.vo.LoanBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.finance.service.LoanManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toFailResponseVoWithMessage;
import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithData;

/**
 * @author yuanyiwen
 * @create 2019-07-13 20:16
 * @description
 */
@Api(tags = "管理端-贷款基本信息")
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/finance/manager")
public class LoanManagerController {

    @Autowired
    private LoanManagerService loanManagerService;

    @ApiOperation(value = "添加一个贷款基本信息")
    @PostMapping
    public WebResponseVo<LoanBasicVo> addLoanBasicInfo(@Valid @RequestBody LoanBasicDto loanBasicDto) {

        if (loanBasicDto == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "所传信息为空");
        }

        return toSuccessResponseVoWithData(loanManagerService.addLoanBasicInfo(loanBasicDto));
    }


    @ApiOperation(value = "修改贷款基本信息")
    @PutMapping
    public WebResponseVo<LoanBasicVo> updateLoanBasicInfo(@Exist(repository = LoanBasicInfoRepository.class)
                                                          @RequestParam Long loanBasicId,
                                                          @Valid @RequestBody LoanBasicDto loanBasicDto) {

        if (loanBasicDto == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "所传信息为空");
        }

        return toSuccessResponseVoWithData(loanManagerService.updateLoanBasicInfo(loanBasicId, loanBasicDto));
    }


    @ApiOperation(value = "关闭一个贷款基本信息",
            notes = "一旦关闭将不能重新启用，如果要启用只能重新添加一个相同的；" +
                    "所以需要前端在调用前给用户一些必要的提示信息")
    @PutMapping("/close")
    public WebResponseVo<LoanBasicVo> closeLoanBasicInfo(@Exist(repository = LoanBasicInfoRepository.class)
                                                         @RequestParam Long loanBasicId) {

        return toSuccessResponseVoWithData(loanManagerService.closeLoanBasicInfo(loanBasicId));
    }


    @ApiOperation(value = "获取处于某种状态（可用or不可用）下的贷款基本信息")
    @GetMapping("/status")
    public WebResponseVo<List<LoanBasicVo>> getLoanBasicInfoOfStatus(@RequestParam boolean enable) {

        return toSuccessResponseVoWithData(loanManagerService.getAllLoanBasicVoOfStatus(enable));
    }
}