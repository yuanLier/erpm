package edu.cqupt.mislab.erp.game.compete.operation.market.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.market.service.MarketService;
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
 * @description
 **/

@Api(description = "学生端-市场开拓")
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/market")
public class MarketController {

    @Autowired
    private MarketService marketService;

    @ApiOperation(value = "获取某个企业的全部市场开拓信息")
    @GetMapping
    public WebResponseVo<List<MarketDisplayVo>> findByEnterpriseId(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                @RequestParam Long enterpriseId) {

        List<MarketDisplayVo> marketDisplayVoList = marketService.findByEnterpriseId(enterpriseId);

        if(marketDisplayVoList.size() == 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "该企业对应的市场开拓不存在");
        }

        return toSuccessResponseVoWithData(marketDisplayVoList);
    }


    @ApiOperation(value = "获取某企业中处于某开拓状态的市场")
    @GetMapping("status")
    public WebResponseVo<List<MarketDisplayVo>> findByEnterpriseIdAndAndMarketStatus(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                               @RequestParam Long enterpriseId,
                                                                               @RequestParam MarketStatusEnum marketStatus) {

        List<MarketDisplayVo> marketDisplayVoList = marketService.findByEnterpriseIdAndMarketStatus(enterpriseId, marketStatus);

        if(marketDisplayVoList.size() == 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "该企业对应的市场开拓不存在");
        }

        return toSuccessResponseVoWithData(marketDisplayVoList);
    }


    @ApiOperation(value = "开始开拓")
    @PutMapping("start")
    public WebResponseVo<MarketDisplayVo> startDevelopMarket(@Exist(repository = MarketDevelopInfoRepository.class)
                                                       @RequestParam Long marketDevelopId) {

        return toSuccessResponseVoWithData(marketService.updateMarketStatus(marketDevelopId, MarketStatusEnum.DEVELOPING));
    }


    @ApiOperation(value = "暂停开拓")
    @PutMapping("pause")
    public WebResponseVo<MarketDisplayVo> updateMarketStatusToPause(@Exist(repository = MarketDevelopInfoRepository.class)
                                                             @RequestParam Long marketDevelopId) {

        return toSuccessResponseVoWithData(marketService.updateMarketStatus(marketDevelopId, MarketStatusEnum.DEVELOPPAUSE));
    }


    @ApiOperation(value = "继续开拓")
    @PutMapping("develop")
    public WebResponseVo<MarketDisplayVo> updateMarketStatusToDeveloping(@Exist(repository = MarketDevelopInfoRepository.class)
                                                             @RequestParam Long marketDevelopId) {

        return toSuccessResponseVoWithData(marketService.updateMarketStatus(marketDevelopId, MarketStatusEnum.DEVELOPING));
    }

}
