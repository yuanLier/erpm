package edu.cqupt.mislab.erp.game.compete.operation.market.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.dto.MarketBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.market.service.MarketManagerService;
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
 * @create 2019-05-13 20:02
 * @description
 */

@Api(description = "管理端-市场基本信息")
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/market/manager")
public class MarketManagerController {

    @Autowired
    private MarketManagerService marketManagerService;

    @ApiOperation(value = "添加一个市场基本信息")
    @PostMapping
    public WebResponseVo<MarketBasicVo> addMarketBasicInfo(@Valid @RequestBody MarketBasicDto marketBasicDto) {

        if(marketBasicDto == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST,"所传信息为空");
        }

        MarketStatusEnum marketStatus = marketBasicDto.getMarketStatus();
        if((marketStatus != MarketStatusEnum.DEVELOPED ) && (marketStatus != MarketStatusEnum.TODEVELOP)) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "开拓状态只允许为未开拓或已开拓！");
        }

        return toSuccessResponseVoWithData(marketManagerService.addMarketBasicInfo(marketBasicDto));
    }


    @ApiOperation(value = "修改市场基本信息")
    @PutMapping
    public WebResponseVo<MarketBasicVo> updateMarketBasicInfo(@Exist(repository = MarketBasicInfoRepository.class)
                                                        @RequestParam Long marketBasicId,
                                                        @Valid @RequestBody MarketBasicDto marketBasicDto) {

        if(marketBasicDto == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST,"所传信息为空");
        }

        MarketStatusEnum marketStatus = marketBasicDto.getMarketStatus();
        if((marketStatus != MarketStatusEnum.DEVELOPED ) && (marketStatus != MarketStatusEnum.TODEVELOP)) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "开拓状态只允许为未开拓或已开拓！");
        }

        return toSuccessResponseVoWithData(marketManagerService.updateMarketBasicInfo(marketBasicId, marketBasicDto));
    }


    @ApiOperation(value = "关闭一个市场信息",
            notes = "一旦关闭将不能重新启用，如果要启用只能重新添加一个相同的；" +
                    "所以需要前端在调用前给用户一些必要的提示信息")
    @PutMapping("/close")
    public WebResponseVo<MarketBasicVo> closeMarketBasicInfo(@Exist(repository = MarketBasicInfoRepository.class)
                                                       @RequestParam Long marketBasicId) {

        return toSuccessResponseVoWithData(marketManagerService.closeMarketBasicInfo(marketBasicId));
    }

    @ApiOperation(value = "获取处于某种状态（可用or不可用）下的市场基本信息")
    @GetMapping("/status")
    public WebResponseVo<List<MarketBasicVo>> getMarketBasicInfoOfStatus(@RequestParam boolean enable) {

        return toSuccessResponseVoWithData(marketManagerService.getAllMarketBasicVoOfStatus(enable));
    }
}
