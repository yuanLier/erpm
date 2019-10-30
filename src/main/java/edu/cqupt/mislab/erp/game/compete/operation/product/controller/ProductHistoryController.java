package edu.cqupt.mislab.erp.game.compete.operation.product.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.commons.validators.annotations.GameStatusValid;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductHistoryVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.service.ProductHistoryService;
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
 * @create 2019-05-16 21:45
 * @description
 */
@Api(tags = "历史数据-产品市场占有率分析")
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/product/history")
public class ProductHistoryController {

    @Autowired
    private ProductHistoryService productHistoryService;


    @ApiOperation(value = "获取某一比赛中处于某一周期的各个企业的全部产品研发情况")
    @GetMapping
    public WebResponseVo<List<ProductHistoryVo>> productHistory(@Exist(repository = GameBasicInfoRepository.class)
                                                                @GameStatusValid(requireStatus = GameStatusEnum.OVER)
                                                                @RequestParam Long gameId,
                                                                @RequestParam Integer period) {

        if (period <= 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "请求的周期数应大于0");
        }

        return toSuccessResponseVoWithData(productHistoryService.findProductHistoryVoOfGameAndPeriod(gameId, period));
    }


    @ApiOperation(value = "获取某一比赛中可选择查看的最大周期")
    @GetMapping("/period")
    public WebResponseVo<Integer> getTotalPeriod(@Exist(repository = GameBasicInfoRepository.class)
                                                 @GameStatusValid(requireStatus = GameStatusEnum.OVER)
                                                 @RequestParam Long gameId) {

        return toSuccessResponseVoWithData(productHistoryService.getTotalPeriod(gameId));
    }
}