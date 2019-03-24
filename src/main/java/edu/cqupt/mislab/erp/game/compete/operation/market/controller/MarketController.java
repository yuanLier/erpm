package edu.cqupt.mislab.erp.game.compete.operation.market.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.dto.MarketBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketBasicVo;
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

@Api
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/market")
public class MarketController {

    @Autowired
    private MarketService marketService;

    @ApiOperation(value = "获取某个企业的全部市场开拓信息")
    @GetMapping("/market/infos/get")
    public WebResponseVo<List<MarketDisplayVo>> findByEnterpriseId(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                @RequestParam Long enterpriseId) {

        List<MarketDisplayVo> marketDisplayVoList = marketService.findByEnterpriseId(enterpriseId);

        if(marketDisplayVoList == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "该企业对应的市场开拓不存在");
        }

        return toSuccessResponseVoWithData(marketDisplayVoList);
    }


    @ApiOperation(value = "获取某企业中处于某开拓状态的市场")
    @GetMapping("/market/infos/get/status")
    public WebResponseVo<List<MarketDisplayVo>> findByEnterpriseIdAndAndMarketStatus(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                               @RequestParam Long enterpriseId,
                                                                               @RequestParam MarketStatusEnum marketStatus) {

        List<MarketDisplayVo> marketDisplayVoList = marketService.findByEnterpriseIdAndMarketStatus(enterpriseId, marketStatus);

        if(marketDisplayVoList == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "该企业对应的市场开拓不存在");
        }

        return toSuccessResponseVoWithData(marketDisplayVoList);
    }


    @ApiOperation(value = "修改某个市场的开拓状态")
    @PostMapping("/market/infos/update/status")
    public WebResponseVo<MarketDisplayVo> updateMarketStatus(@Exist(repository = MarketDevelopInfoRepository.class)
                                                       @RequestParam Long marketDevelopId,
                                                       @RequestParam MarketStatusEnum marketStatus) {

        return toSuccessResponseVoWithData(marketService.updateMarketStatus(marketDevelopId, marketStatus));
    }


    @ApiOperation(value = "（管理员）修改市场基本信息")
    @PostMapping("/market/basic/update")
        public WebResponseVo<MarketBasicVo> updateMarketBasicInfo(@RequestBody MarketBasicDto marketBasicDto) {
        if(marketBasicDto != null) {
            return toSuccessResponseVoWithData(marketService.updateMarketBasicInfo(marketBasicDto));
        } else {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND,"所传信息为空");
        }
    }


}
