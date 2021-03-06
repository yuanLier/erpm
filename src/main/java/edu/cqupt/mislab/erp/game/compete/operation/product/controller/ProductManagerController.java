package edu.cqupt.mislab.erp.game.compete.operation.product.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductMaterialBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.dto.ProductBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.dto.ProductMaterialBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductMaterialBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.service.ProductManagerService;
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
 * @create 2019-05-16 18:28
 * @description
 */

@Api(tags = "管理端-产品基本信息")
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/product/manager")
public class ProductManagerController {

    @Autowired
    private ProductManagerService productManagerService;

    @ApiOperation(value = "添加一个产品基本信息")
    @PostMapping
    public WebResponseVo<ProductBasicVo> addProductBasicInfo(@Valid @RequestBody ProductBasicDto productBasicDto) {

        if (productBasicDto == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "所传信息为空");
        }

        ProductDevelopStatusEnum productStatus = productBasicDto.getProductDevelopStatus();
        if ((productStatus != ProductDevelopStatusEnum.DEVELOPED) && (productStatus != ProductDevelopStatusEnum.TODEVELOP)) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "开拓状态只允许为未开拓或已开拓！");
        }

        return toSuccessResponseVoWithData(productManagerService.addProductBasicInfo(productBasicDto));
    }


    @ApiOperation(value = "修改产品基本信息")
    @PutMapping
    public WebResponseVo<ProductBasicVo> updateProductBasicInfo(@Exist(repository = ProductBasicInfoRepository.class)
                                                                @RequestParam Long productBasicId,
                                                                @Valid @RequestBody ProductBasicDto productBasicDto) {

        if (productBasicDto == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "所传信息为空");
        }

        ProductDevelopStatusEnum productStatus = productBasicDto.getProductDevelopStatus();
        if ((productStatus != ProductDevelopStatusEnum.DEVELOPED) && (productStatus != ProductDevelopStatusEnum.TODEVELOP)) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "开拓状态只允许为未开拓或已开拓！");
        }

        ProductBasicVo productBasicVo = productManagerService.updateProductBasicInfo(productBasicId, productBasicDto);
        if(productBasicVo == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "该产品信息已被关闭，请检查您的操作");
        }

        return toSuccessResponseVoWithData(productBasicVo);
    }


    @ApiOperation(value = "关闭一个产品信息",
            notes = "一旦关闭将不能重新启用，如果要启用只能重新添加一个相同的；" +
                    "所以需要前端在调用前给用户一些必要的提示信息")
    @PutMapping("/close")
    public WebResponseVo<ProductBasicVo> closeProductBasicInfo(@Exist(repository = ProductBasicInfoRepository.class)
                                                               @RequestParam Long productBasicId) {

        return toSuccessResponseVoWithData(productManagerService.closeProductBasicInfo(productBasicId));
    }


    @ApiOperation(value = "添加一个产品原材料基本信息")
    @PostMapping("/material")
    public WebResponseVo<ProductMaterialBasicVo> addProductMaterialBasicInfo(@Valid @RequestBody ProductMaterialBasicDto productMaterialBasicDto) {

        if (productMaterialBasicDto == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "所传信息为空");
        }

        return toSuccessResponseVoWithData(productManagerService.addProductMaterialBasicInfo(productMaterialBasicDto));
    }


    @ApiOperation(value = "修改产品原材料基本信息（只能修改原料数量）")
    @PutMapping("/material")
    public WebResponseVo<ProductMaterialBasicVo> updateProductMaterialBasicInfo(@Exist(repository = ProductMaterialBasicInfoRepository.class)
                                                                @RequestParam Long productMaterialBasicId,
                                                                    @RequestParam Integer number) {

        if (number < 1) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "所需原料数量至少为1");
        }

        ProductMaterialBasicVo productMaterialBasicVo = productManagerService.updateProductMaterialBasicInfo(productMaterialBasicId, number);
        if(productMaterialBasicVo == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "该产品材料信息已被关闭，请检查您的操作");
        }

        return toSuccessResponseVoWithData(productMaterialBasicVo);
    }


    @ApiOperation(value = "关闭一个产品原材料信息",
            notes = "一旦关闭将不能重新启用，如果要启用只能重新添加一个相同的；" +
                    "一个产品至少需要由一条材料构成，所以当材料只剩一种时，无法删除；" +
                    "此时用户可以选择先添加一条新的材料构成再删除原材料构成，或直接关闭该产品；" +
                    "需要前端在调用前给用户一些必要的提示信息")
    @PutMapping("material/close")
    public WebResponseVo<ProductMaterialBasicVo> closeProductMaterialBasicInfo(@Exist(repository = ProductMaterialBasicInfoRepository.class)
                                                               @RequestParam Long productMaterialBasicId) {

        ProductMaterialBasicVo productMaterialBasicVo = productManagerService.closeProductMaterialBasicInfo(productMaterialBasicId);

        if(productMaterialBasicVo == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "请检查您的操作，以确保任何时候该产品至少由一条材料构成！");
        }

        return toSuccessResponseVoWithData(productMaterialBasicVo);
    }


    @ApiOperation(value = "获取处于某种状态（可用or不可用）下的产品基本信息")
    @GetMapping("/status")
    public WebResponseVo<List<ProductBasicVo>> getProductBasicInfoOfStatus(@RequestParam boolean enable) {

        return toSuccessResponseVoWithData(productManagerService.getAllProductBasicVoOfStatus(enable));
    }


    @ApiOperation(value = "获取处于某种状态（可用or不可用）下的产品材料基本信息")
    @GetMapping("/material/status")
    public WebResponseVo<List<ProductMaterialBasicVo>> getProductMaterialBasicInfoOfStatus(@RequestParam boolean enable) {

        return toSuccessResponseVoWithData(productManagerService.getAllProductMaterialBasicVoOfStatus(enable));
    }
}