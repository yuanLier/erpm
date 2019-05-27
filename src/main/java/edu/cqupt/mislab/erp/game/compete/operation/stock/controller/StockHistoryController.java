package edu.cqupt.mislab.erp.game.compete.operation.stock.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.commons.validators.annotations.GameStatusValid;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo.MaterialStockHistoryVo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo.ProductStockHistoryVo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.service.StockHistoryService;
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
 * @create 2019-05-25 21:44
 * @description
 */
@Api(description = "历史数据-材料与产品库存变动分析")
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/stock/history")
public class StockHistoryController {

    @Autowired
    private StockHistoryService stockHistoryService;
    

    @ApiOperation(value = "获取某一比赛中处于某一周期的各个企业的全部材料库存情况")
    @GetMapping("/material")
    public WebResponseVo<List<MaterialStockHistoryVo>> materialStockHistory(@Exist(repository = GameBasicInfoRepository.class)
                                                        @GameStatusValid(requireStatus = GameStatusEnum.OVER)
                                                        @RequestParam Long gameId,
                                                                    @RequestParam Integer period) {

        if(period <= 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "请求的周期数应大于0");
        }

        return toSuccessResponseVoWithData(stockHistoryService.findMaterialStockHistoryVoOfGameAndPeriod(gameId, period));
    }


    @ApiOperation(value = "获取某一比赛中处于某一周期的各个企业的全部材料库存情况")
    @GetMapping("/product")
    public WebResponseVo<List<ProductStockHistoryVo>> productStockHistory(@Exist(repository = GameBasicInfoRepository.class)
                                                                    @GameStatusValid(requireStatus = GameStatusEnum.OVER)
                                                                    @RequestParam Long gameId,
                                                                   @RequestParam Integer period) {

        if(period <= 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "请求的周期数应大于0");
        }

        return toSuccessResponseVoWithData(stockHistoryService.findProductStockHistoryVoOfGameAndPeriod(gameId, period));
    }


    @ApiOperation(value = "获取某一比赛中可选择查看的最大周期")
    @GetMapping("/period")
    public WebResponseVo<Integer> getTotalPeriod(@Exist(repository = GameBasicInfoRepository.class)
                                                 @GameStatusValid(requireStatus = GameStatusEnum.OVER)
                                                 @RequestParam Long gameId) {

        return toSuccessResponseVoWithData(stockHistoryService.getTotalPeriod(gameId));
    }

}