package edu.cqupt.mislab.erp.game.compete.operation.produce.advance.factorymanagement;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.commons.validators.annotations.GameStatusValid;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryHistoryVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.service.factorymanagement.FactoryHistoryService;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameStatusEnum;
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
 * @create 2019-06-03 22:36
 * @description
 */
@Api(description = "历史数据-企业厂房占有率分析")
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/factorymanager/factory/history")
public class FactoryHistoryController {

    @Autowired
    private FactoryHistoryService factoryHistoryService;

    @ApiOperation(value = "获取某一比赛中处于某一周期的各个企业的全部厂房拥有情况")
    @GetMapping
    public WebResponseVo<List<FactoryHistoryVo>> factoryHistory(@Exist(repository = GameBasicInfoRepository.class)
                                                        @GameStatusValid(requireStatus = GameStatusEnum.OVER)
                                                        @RequestParam Long gameId,
                                                                @RequestParam Integer period) {

        if(period <= 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "请求的周期数应大于0");
        }

        return toSuccessResponseVoWithData(factoryHistoryService.findFactoryHistoryVoOfGameAndPeriod(gameId, period));
    }

}