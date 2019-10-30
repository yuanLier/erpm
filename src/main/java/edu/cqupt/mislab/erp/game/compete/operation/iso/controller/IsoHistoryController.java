package edu.cqupt.mislab.erp.game.compete.operation.iso.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.commons.validators.annotations.GameStatusValid;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoHistoryVo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.service.IsoHistoryService;
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
 * @create 2019-05-12 21:57
 * @description
 */
@Api(tags = "历史数据-企业iso认证比率分析")
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/iso/history")
public class IsoHistoryController {

    @Autowired
    private IsoHistoryService isoHistoryService;


    @ApiOperation(value = "获取某一比赛中处于某一周期的各个企业的全部iso认证情况")
    @GetMapping
    public WebResponseVo<List<IsoHistoryVo>> isoHistory(@Exist(repository = GameBasicInfoRepository.class)
                                                            @GameStatusValid(requireStatus = GameStatusEnum.OVER)
                                                            @RequestParam Long gameId,
                                                        @RequestParam Integer period) {

        if(period <= 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "请求的周期数应大于0");
        }

        return toSuccessResponseVoWithData(isoHistoryService.findIsoHistoryVoOfGameAndPeriod(gameId, period));
    }


    @ApiOperation(value = "获取某一比赛中可选择查看的最大周期")
    @GetMapping("/period")
    public WebResponseVo<Integer> getTotalPeriod(@Exist(repository = GameBasicInfoRepository.class)
                                                     @GameStatusValid(requireStatus = GameStatusEnum.OVER)
                                                     @RequestParam Long gameId) {

        return toSuccessResponseVoWithData(isoHistoryService.getTotalPeriod(gameId));
    }
}
