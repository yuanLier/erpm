package edu.cqupt.mislab.erp.game.compete.operation.advance;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.basic.impl.ModelAdvanceService;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

        return modelAdvanceService.advance(enterpriseId);
    }
}
