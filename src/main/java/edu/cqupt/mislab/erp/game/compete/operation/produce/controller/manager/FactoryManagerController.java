package edu.cqupt.mislab.erp.game.compete.operation.produce.controller.manager;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.dto.FactoryBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.service.factorymanagement.FactoryManagerService;
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
 * @create 2019-06-05 22:31
 * @description
 */
@Api(description = "管理端-厂房基本信息")
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/factorymanagement/factory/manager")
public class FactoryManagerController {

    @Autowired
    private FactoryManagerService factoryManagerService;

    @ApiOperation(value = "添加一个厂房基本信息")
    @PostMapping
    public WebResponseVo<FactoryBasicVo> addFactoryBasicInfo(@Valid @RequestBody FactoryBasicDto factoryBasicDto) {

        if (factoryBasicDto == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "所传信息为空");
        }

        return toSuccessResponseVoWithData(factoryManagerService.addFactoryBasicInfo(factoryBasicDto));
    }


    @ApiOperation(value = "修改厂房基本信息")
    @PutMapping
    public WebResponseVo<FactoryBasicVo> updateFactoryBasicInfo(@Exist(repository = FactoryBasicInfoRepository.class)
                                                                @RequestParam Long factoryBasicId,
                                                                @Valid @RequestBody FactoryBasicDto factoryBasicDto) {

        if (factoryBasicDto == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "所传信息为空");
        }

        return toSuccessResponseVoWithData(factoryManagerService.updateFactoryBasicInfo(factoryBasicId, factoryBasicDto));
    }


    @ApiOperation(value = "关闭一个厂房",
            notes = "一旦关闭将不能重新启用，如果要启用只能重新添加一个相同的；" +
                    "所以需要前端在调用前给用户一些必要的提示信息")
    @PutMapping("/close")
    public WebResponseVo<FactoryBasicVo> closeFactoryBasicInfo(@Exist(repository = FactoryBasicInfoRepository.class)
                                                               @RequestParam Long factoryBasicId) {

        return toSuccessResponseVoWithData(factoryManagerService.closeFactoryBasicInfo(factoryBasicId));
    }


    @ApiOperation(value = "获取处于某种状态（可用or不可用）下的厂房基本信息")
    @GetMapping("/status")
    public WebResponseVo<List<FactoryBasicVo>> getFactoryBasicInfoOfStatus(@RequestParam boolean enable) {

        return toSuccessResponseVoWithData(factoryManagerService.getAllFactoryBasicVoOfStatus(enable));
    }
}

































