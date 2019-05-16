package edu.cqupt.mislab.erp.game.compete.operation.product.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.dto.ProductBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.service.ProductManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toFailResponseVoWithMessage;
import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithData;

/**
 * @author yuanyiwen
 * @create 2019-05-16 18:28
 * @description
 */

@Api
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

        ProductDevelopStatusEnum productStatus = productBasicDto.getProductStatus();
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

        ProductDevelopStatusEnum productStatus = productBasicDto.getProductStatus();
        if ((productStatus != ProductDevelopStatusEnum.DEVELOPED) && (productStatus != ProductDevelopStatusEnum.TODEVELOP)) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "开拓状态只允许为未开拓或已开拓！");
        }

        return toSuccessResponseVoWithData(productManagerService.updateProductBasicInfo(productBasicId, productBasicDto));
    }


    @ApiOperation(value = "关闭一个产品信息",
            notes = "一旦关闭将不能重新启用，如果要启用只能重新添加一个相同的；" +
                    "所以需要前端在调用前给用户一些必要的提示信息")
    @PutMapping("/close")
    public WebResponseVo<ProductBasicVo> closeProductBasicInfo(@Exist(repository = ProductBasicInfoRepository.class)
                                                               @RequestParam Long productBasicId) {

        return toSuccessResponseVoWithData(productManagerService.closeProductBasicInfo(productBasicId));
    }
}