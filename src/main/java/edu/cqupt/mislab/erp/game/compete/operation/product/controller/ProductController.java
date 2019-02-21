package edu.cqupt.mislab.erp.game.compete.operation.product.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductMaterialBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductMaterialVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.service.ProductService;
import edu.cqupt.mislab.erp.game.compete.operation.product.util.EnumUtil;
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
    @PostMapping("/product/infos/get")
    public WebResponseVo<List<ProductDisplayVo>> findByEnterpriseId(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                @RequestParam Long enterpriseId) {

        List<ProductDisplayVo> productDisplayVoList = productService.findByEnterpriseId(enterpriseId);

        if(productDisplayVoList == null) {
            return toFailResponseVoWithMessage(null, "该企业对应的产品研发不存在");
        }

        return toSuccessResponseVoWithData(productDisplayVoList);
    }


    @ApiOperation(value = "获取某企业中处于某研发状态的产品")
    @PostMapping("/product/infos/get/{status}")
    public WebResponseVo<List<ProductDisplayVo>> findByEnterpriseIdAndProductStatus(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                               @RequestParam Long enterpriseId,
                                                                               @PathVariable ProductStatusEnum productStatus) {

        if(!EnumUtil.isInclude(productStatus.toString())) {
            return toFailResponseVoWithMessage(null, "产品研发状态有误");
        }

        List<ProductDisplayVo> productDisplayVoList = productService.findByEnterpriseIdAndProductStatus(enterpriseId, productStatus);

        if(productDisplayVoList == null) {
            return toFailResponseVoWithMessage(null, "该企业对应的产品研发信息不存在");
        }

        return toSuccessResponseVoWithData(productDisplayVoList);
    }


    @ApiOperation(value = "修改某个产品的研发状态")
    @PostMapping("/product/infos/update/{status}")
    public WebResponseVo<ProductDisplayVo> updateProductStatus(@Exist(repository = ProductDevelopInfoRepository.class)
                                                       @RequestParam Long productDevelopId,
                                                       @PathVariable ProductStatusEnum productStatus) {

        if(!EnumUtil.isInclude(productStatus.toString())) {
            return toFailResponseVoWithMessage(null, "产品研发状态有误");
        }

        return toSuccessResponseVoWithData(productService.updateProductStatus(productDevelopId, productStatus));

    }


    @ApiOperation(value = "（管理员）修改产品基本信息")
    @PostMapping("/product/basic/update")
    public WebResponseVo<ProductBasicInfo> updateProductBasicInfo(@RequestBody ProductBasicInfo productBasicInfo) {
        if(productBasicInfo != null) {
            return toSuccessResponseVoWithData(productService.updateProductBasicInfo(productBasicInfo));
        } else {
            return toFailResponseVoWithMessage(null,"所传信息为空");
        }
    }

    @ApiOperation(value = "（管理员）修改产品基本信息")
    @PostMapping("/product/basic/update")
    public WebResponseVo<ProductMaterialBasicInfo> updateProductMaterialBasicInfo(@RequestBody ProductMaterialBasicInfo productMaterialBasicInfo) {
        if(productMaterialBasicInfo != null) {
            return toSuccessResponseVoWithData(productService.updateProductMaterialBasicInfo(productMaterialBasicInfo));
        } else {
            return toFailResponseVoWithMessage(null,"所传信息为空");
        }
    }


    @ApiOperation(value = "获取某个企业的全部产品原材料构成情况")
    @PostMapping("/product/material/get")
    public WebResponseVo<List<ProductMaterialVo>> findMaterialByEnterpriseId(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                    @RequestParam Long enterpriseId) {

        List<ProductMaterialVo> productMaterialVoList = productService.findProductMaterialInfoByEnterpriseId(enterpriseId);

        if(productMaterialVoList == null) {
            return toFailResponseVoWithMessage(null, "该企业对应的产品信息不存在");
        }

        return toSuccessResponseVoWithData(productMaterialVoList);
    }


    @ApiOperation(value = "获取某企业中处于某状态下的产品的原材料构成情况")
    @PostMapping("/product/material/get/{status}")
    public WebResponseVo<List<ProductMaterialVo>> findMaterialByEnterpriseIdAndProductStatus(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                                    @RequestParam Long enterpriseId,
                                                                                    @PathVariable ProductStatusEnum productStatus) {

        if(!EnumUtil.isInclude(productStatus.toString())) {
            return toFailResponseVoWithMessage(null, "产品状态有误");
        }

        List<ProductMaterialVo> productMaterialVoList =
                productService.findProductMaterialInfoByEnterpriseIdAndProductStatus(enterpriseId, productStatus);

        if(productMaterialVoList == null) {
            return toFailResponseVoWithMessage(null, "该企业对应的产品信息不存在");
        }

        return toSuccessResponseVoWithData(productMaterialVoList);
    }

}
