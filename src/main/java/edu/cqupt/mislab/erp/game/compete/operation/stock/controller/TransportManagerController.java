package edu.cqupt.mislab.erp.game.compete.operation.stock.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.stock.dao.TransportBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.dto.TransportBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo.TransportBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.service.TransportManagerService;
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
 * @create 2019-05-25 18:33
 * @description
 */
@Api(description = "管理端-运输方式基本信息")
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/transport/manager")
public class TransportManagerController {

    @Autowired
    private TransportManagerService transportManagerService;

    @ApiOperation(value = "添加一个运输方式")
    @PostMapping
    public WebResponseVo<TransportBasicVo> addTransportBasicInfo(@Valid @RequestBody TransportBasicDto transportBasicDto) {

        if (transportBasicDto == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "所传信息为空");
        }

        return toSuccessResponseVoWithData(transportManagerService.addTransportBasicInfo(transportBasicDto));
    }


    @ApiOperation(value = "修改运输方式基本信息")
    @PutMapping
    public WebResponseVo<TransportBasicVo> updateTransportBasicInfo(@Exist(repository = TransportBasicInfoRepository.class)
                                                                    @RequestParam Long transportBasicId,
                                                                    @Valid @RequestBody TransportBasicDto transportBasicDto) {

        if (transportBasicDto == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "所传信息为空");
        }

        return toSuccessResponseVoWithData(transportManagerService.updateTransportBasicInfo(transportBasicId, transportBasicDto));
    }


    @ApiOperation(value = "关闭一个运输方式基本信息",
            notes = "一旦关闭将不能重新启用，如果要启用只能重新添加一个相同的；" +
                    "所以需要前端在调用前给用户一些必要的提示信息")
    @PutMapping("/close")
    public WebResponseVo<TransportBasicVo> closeTransportBasicInfo(@Exist(repository = TransportBasicInfoRepository.class)
                                                                   @RequestParam Long transportBasicId) {

        return toSuccessResponseVoWithData(transportManagerService.closeTransportBasicInfo(transportBasicId));
    }


    @ApiOperation(value = "获取处于某种状态（可用or不可用）下的运输方式基本信息")
    @GetMapping("/status")
    public WebResponseVo<List<TransportBasicVo>> getTransportBasicInfoOfStatus(@RequestParam boolean enable) {

        return toSuccessResponseVoWithData(transportManagerService.getAllTransportBasicVoOfStatus(enable));
    }
}