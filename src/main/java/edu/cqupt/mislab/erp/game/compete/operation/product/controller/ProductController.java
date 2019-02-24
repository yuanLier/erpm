package edu.cqupt.mislab.erp.game.compete.operation.product.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.dto.ProductBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.dto.ProductMaterialBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductMaterialBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductMaterialDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.service.ProductService;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toFailResponseVoWithMessage;
import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithData;

@Api
@CrossOrigin
@RestController
@RequestMapping("/game/compete/operation/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "获取某个企业的全部产品研发信息")
    @GetMapping("/product/infos/get")
    public WebResponseVo<List<ProductDisplayVo>> findByEnterpriseId(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                @RequestParam Long enterpriseId) {

        List<ProductDisplayVo> productDisplayVoList = productService.findByEnterpriseId(enterpriseId);

        if(productDisplayVoList == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "该企业对应的产品研发不存在");
        }

        return toSuccessResponseVoWithData(productDisplayVoList);
    }


    @ApiOperation(value = "获取某企业中处于某研发状态的产品")
    @PostMapping("/product/infos/get/{status}")
    public WebResponseVo<List<ProductDisplayVo>> findByEnterpriseIdAndProductStatus(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                               @RequestParam Long enterpriseId,
                                                                               @PathVariable ProductStatusEnum productStatus) {

        List<ProductDisplayVo> productDisplayVoList = productService.findByEnterpriseIdAndProductStatus(enterpriseId, productStatus);

        if(productDisplayVoList == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "该企业对应的产品研发信息不存在");
        }

        return toSuccessResponseVoWithData(productDisplayVoList);
    }


    @ApiOperation(value = "修改某个产品的研发状态")
    @PostMapping("/product/infos/update/{status}")
    public WebResponseVo<ProductDisplayVo> updateProductStatus(@Exist(repository = ProductDevelopInfoRepository.class)
                                                       @RequestParam Long productDevelopId,
                                                       @PathVariable ProductStatusEnum productStatus) {

        return toSuccessResponseVoWithData(productService.updateProductStatus(productDevelopId, productStatus));

    }


    @ApiOperation(value = "（管理员）修改产品基本信息")
    @PostMapping("/product/basic/update")
    public WebResponseVo<ProductBasicVo> updateProductBasicInfo(@RequestBody ProductBasicDto productBasicDto) {
        if(productBasicDto != null) {
            return toSuccessResponseVoWithData(productService.updateProductBasicInfo(productBasicDto));
        }

        return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND,"所传信息为空");
    }

    @ApiOperation(value = "（管理员）修改产品基本信息")
    @PostMapping("/product/basic/update")
    public WebResponseVo<ProductMaterialBasicVo> updateProductMaterialBasicInfo(@RequestBody ProductMaterialBasicDto productMaterialBasicDto) {
        if(productMaterialBasicDto != null) {
            return toSuccessResponseVoWithData(productService.updateProductMaterialBasicInfo(productMaterialBasicDto));
        }

        return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND,"所传信息为空");

    }


    @ApiOperation(value = "获取某个企业的全部产品原材料构成情况")
    @PostMapping("/product/material/get")
    public WebResponseVo<List<ProductMaterialDisplayVo>> findMaterialByEnterpriseId(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                    @RequestParam Long enterpriseId) {

        List<ProductMaterialDisplayVo> productMaterialDisplayVoList = productService.findProductMaterialInfoByEnterpriseId(enterpriseId);

        if(productMaterialDisplayVoList == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "该企业对应的产品信息不存在");
        }

        return toSuccessResponseVoWithData(productMaterialDisplayVoList);
    }


    @ApiOperation(value = "获取某企业中处于某状态下的产品的原材料构成情况")
    @PostMapping("/product/material/get/{status}")
    public WebResponseVo<List<ProductMaterialDisplayVo>> findMaterialByEnterpriseIdAndProductStatus(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                                    @RequestParam Long enterpriseId,
                                                                                                    @PathVariable ProductStatusEnum productStatus) {

        List<ProductMaterialDisplayVo> productMaterialDisplayVoList =
                productService.findProductMaterialInfoByEnterpriseIdAndProductStatus(enterpriseId, productStatus);

        if(productMaterialDisplayVoList == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "该企业对应的产品信息不存在");
        }

        return toSuccessResponseVoWithData(productMaterialDisplayVoList);
    }

}
