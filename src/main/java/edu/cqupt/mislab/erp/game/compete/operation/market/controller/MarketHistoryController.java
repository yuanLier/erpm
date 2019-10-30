package edu.cqupt.mislab.erp.game.compete.operation.market.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.commons.validators.annotations.GameStatusValid;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketHistoryVo;
import edu.cqupt.mislab.erp.game.compete.operation.market.service.MarketHistoryService;
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
 * @create 2019-05-15 20:21
 * @description
 */
@Api(tags = "历史数据-综合市场占有率分析")
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/market/history")
public class MarketHistoryController {

    @Autowired
    private MarketHistoryService marketHistoryService;


    @ApiOperation(value = "获取某一比赛中处于某一周期的各个企业的全部市场开拓情况")
    @GetMapping
    public WebResponseVo<List<MarketHistoryVo>> marketHistory(@Exist(repository = GameBasicInfoRepository.class)
                                                        @GameStatusValid(requireStatus = GameStatusEnum.OVER)
                                                        @RequestParam Long gameId,
                                                              @RequestParam Integer period) {

        if(period <= 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "请求的周期数应大于0");
        }

        return toSuccessResponseVoWithData(marketHistoryService.findMarketHistoryVoOfGameAndPeriod(gameId, period));
    }


    @ApiOperation(value = "获取某一比赛中可选择查看的最大周期")
    @GetMapping("/period")
    public WebResponseVo<Integer> getTotalPeriod(@Exist(repository = GameBasicInfoRepository.class)
                                                 @GameStatusValid(requireStatus = GameStatusEnum.OVER)
                                                 @RequestParam Long gameId) {

        return toSuccessResponseVoWithData(marketHistoryService.getTotalPeriod(gameId));
    }

}
