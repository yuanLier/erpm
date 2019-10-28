package edu.cqupt.mislab.erp.game.compete.operation.material.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.material.dao.MaterialBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.dto.MaterialBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.vo.MaterialBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.material.service.MaterialManagerService;
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
 * @create 2019-05-25 14:29
 * @description
 */
@Api(description = "管理端-材料基本信息")
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/material/manager")
public class MaterialManagerController {

    @Autowired
    private MaterialManagerService materialManagerService;

    @ApiOperation(value = "添加一个材料基本信息")
    @PostMapping
    public WebResponseVo<MaterialBasicVo> addMaterialBasicInfo(@Valid @RequestBody MaterialBasicDto materialBasicDto) {

        if(materialBasicDto == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST,"所传信息为空");
        }

        return toSuccessResponseVoWithData(materialManagerService.addMaterialBasicInfo(materialBasicDto));
    }


    @ApiOperation(value = "修改材料基本信息")
    @PutMapping
    public WebResponseVo<MaterialBasicVo> updateMaterialBasicInfo(@Exist(repository = MaterialBasicInfoRepository.class)
                                                        @RequestParam Long materialBasicId,
                                                        @Valid @RequestBody MaterialBasicDto materialBasicDto) {

        if(materialBasicDto == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST,"所传信息为空");
        }

        return toSuccessResponseVoWithData(materialManagerService.updateMaterialBasicInfo(materialBasicId, materialBasicDto));
    }


    @ApiOperation(value = "关闭一个材料基本信息",
            notes = "一旦关闭将不能重新启用，如果要启用只能重新添加一个相同的；" +
                    "且关闭原材料信息之前必确保所有使用该材料的产品基本信息均已关闭；" +
                    "所以需要前端在调用前给用户一些必要的提示信息")
    @PutMapping("/close")
    public WebResponseVo<MaterialBasicVo> closeMaterialBasicInfo(@Exist(repository = MaterialBasicInfoRepository.class)
                                                       @RequestParam Long materialBasicId) {

        MaterialBasicVo materialBasicVo = materialManagerService.closeMaterialBasicInfo(materialBasicId);

        if(materialBasicVo == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "材料信息关闭失败！请确保所有使用了该材料的产品信息已被关闭");
        }

        return toSuccessResponseVoWithData(materialBasicVo);
    }


    @ApiOperation(value = "获取处于某种状态（可用or不可用）下的材料基本信息")
    @GetMapping("/status")
    public WebResponseVo<List<MaterialBasicVo>> getMaterialBasicInfoOfStatus(@RequestParam boolean enable) {

        return toSuccessResponseVoWithData(materialManagerService.getAllMaterialBasicVoOfStatus(enable));
    }
}
