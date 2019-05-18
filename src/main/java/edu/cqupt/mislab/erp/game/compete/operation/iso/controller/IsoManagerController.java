package edu.cqupt.mislab.erp.game.compete.operation.iso.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.dto.IsoBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.service.IsoManagerService;
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
 * @create 2019-05-08 21:06
 * @description Iso管理端
 */
@Api
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/iso/manager")
public class IsoManagerController {

    @Autowired
    private IsoManagerService isoManagerService;

    @ApiOperation(value = "添加一个iso")
    @PostMapping
    public WebResponseVo<IsoBasicVo> addIsoBasicInfo(@Valid @RequestBody IsoBasicDto isoBasicDto) {

        if(isoBasicDto == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST,"所传信息为空");
        }

        IsoStatusEnum isoStatus = isoBasicDto.getIsoStatus();
        if((isoStatus != IsoStatusEnum.DEVELOPED ) && (isoStatus != IsoStatusEnum.TODEVELOP)) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "认证状态只允许为未认证或已认证！");
        }

        return toSuccessResponseVoWithData(isoManagerService.addIsoBasicInfo(isoBasicDto));
    }


    @ApiOperation(value = "修改iso基本信息")
    @PutMapping
    public WebResponseVo<IsoBasicVo> updateIsoBasicInfo(@Exist(repository = IsoBasicInfoRepository.class)
                                                            @RequestParam Long isoBasicId,
                                                        @Valid @RequestBody IsoBasicDto isoBasicDto) {

        if(isoBasicDto == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST,"所传信息为空");
        }

        IsoStatusEnum isoStatus = isoBasicDto.getIsoStatus();
        if((isoStatus != IsoStatusEnum.DEVELOPED ) && (isoStatus != IsoStatusEnum.TODEVELOP)) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "认证状态只允许为未认证或已认证！");
        }

        return toSuccessResponseVoWithData(isoManagerService.updateIsoBasicInfo(isoBasicId, isoBasicDto));
    }


    @ApiOperation(value = "关闭一个iso",
            notes = "一旦关闭将不能重新启用，如果要启用只能重新添加一个相同的；" +
                    "所以需要前端在调用前给用户一些必要的提示信息")
    @PutMapping("/close")
    public WebResponseVo<IsoBasicVo> closeIsoBasicInfo(@Exist(repository = IsoBasicInfoRepository.class)
                                                                   @RequestParam Long isoBasicId) {

        return toSuccessResponseVoWithData(isoManagerService.closeIsoBasicInfo(isoBasicId));
    }


    @ApiOperation(value = "获取处于某种状态（可用or不可用）下的iso基本信息")
    @GetMapping("/status")
    public WebResponseVo<List<IsoBasicVo>> getIsoBasicInfoOfStatus(@RequestParam boolean enable) {

        return toSuccessResponseVoWithData(isoManagerService.getAllIsoBasicVoOfStatus(enable));
    }
}
