package edu.cqupt.mislab.erp.game.compete.operation.market.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.dto.MarketBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.market.service.MarketManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toFailResponseVoWithMessage;
import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithData;

/**
 * @author yuanyiwen
 * @create 2019-05-13 20:02
 * @description
 */

@Api
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/market")
public class MarketManagerController {

    @Autowired
    private MarketManagerService marketManagerService;


    @ApiOperation(value = "修改市场基本信息")
    @PutMapping
    public WebResponseVo<MarketBasicVo> updateMarketBasicInfo(@RequestBody MarketBasicDto marketBasicDto) {

        if(marketBasicDto == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST,"所传信息为空");
        }

        return toSuccessResponseVoWithData(marketManagerService.updateMarketBasicInfo(marketBasicDto));
    }
}
