package edu.cqupt.mislab.erp.game.compete.operation.product.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductMaterialDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.service.ProductService;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
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
 * @description
 **/

@Api(description = "学生端-产品研发")
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation("获取一场比赛中使用的全部产品类型")
    @GetMapping("/type")
    public WebResponseVo<List<ProductTypeVo>> getAllProductTypes(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                     @RequestParam Long enterpriseId) {

        return toSuccessResponseVoWithData(productService.getAllProductTypes(enterpriseId));
    }

    @ApiOperation(value = "获取某个企业的全部产品研发信息")
    @GetMapping
    public WebResponseVo<List<ProductDisplayVo>> findByEnterpriseId(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                @RequestParam Long enterpriseId) {

        List<ProductDisplayVo> productDisplayVoList = productService.findByEnterpriseId(enterpriseId);

        if(productDisplayVoList.size() == 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "该企业对应的产品研发不存在");
        }

        return toSuccessResponseVoWithData(productDisplayVoList);
    }


    @ApiOperation(value = "获取某企业中处于某研发状态的产品")
    @GetMapping("status")
    public WebResponseVo<List<ProductDisplayVo>> findByEnterpriseIdAndProductStatus(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                               @RequestParam Long enterpriseId,
                                                                                    @RequestParam ProductDevelopStatusEnum productDevelopStatus) {

        List<ProductDisplayVo> productDisplayVoList = productService.findByEnterpriseIdAndProductStatus(enterpriseId, productDevelopStatus);

        if(productDisplayVoList.size() == 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "该企业对应的产品研发信息不存在");
        }

        return toSuccessResponseVoWithData(productDisplayVoList);
    }


    @ApiOperation(value = "开始研发")
    @PutMapping("start")
    public WebResponseVo<ProductDisplayVo> startDevelopProduct(@Exist(repository = ProductDevelopInfoRepository.class)
                                                       @RequestParam Long productDevelopId) {

        return toSuccessResponseVoWithData(productService.startProduct(productDevelopId));

    }


    @ApiOperation(value = "暂停研发")
    @PutMapping("pause")
    public WebResponseVo<ProductDisplayVo> updateProductStatusToPause(@Exist(repository = ProductDevelopInfoRepository.class)
                                                               @RequestParam Long productDevelopId) {

        return toSuccessResponseVoWithData(productService.updateProductStatus(productDevelopId, ProductDevelopStatusEnum.DEVELOPPAUSE));

    }


    @ApiOperation(value = "继续研发")
    @PutMapping("develop")
    public WebResponseVo<ProductDisplayVo> updateProductStatusToDeveloping(@Exist(repository = ProductDevelopInfoRepository.class)
                                                               @RequestParam Long productDevelopId) {

        return toSuccessResponseVoWithData(productService.updateProductStatus(productDevelopId, ProductDevelopStatusEnum.DEVELOPING));

    }

    @ApiOperation(value = "获取某个企业的全部产品原材料构成情况")
    @GetMapping("material")
    public WebResponseVo<List<ProductMaterialDisplayVo>> findMaterialByEnterpriseId(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                    @RequestParam Long enterpriseId) {

        List<ProductMaterialDisplayVo> productMaterialDisplayVoList = productService.findProductMaterialInfoByEnterpriseId(enterpriseId);

        if(productMaterialDisplayVoList.size() == 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "该企业对应的产品信息不存在");
        }

        return toSuccessResponseVoWithData(productMaterialDisplayVoList);
    }


    @ApiOperation(value = "获取某企业中处于某状态下的产品的原材料构成情况")
    @GetMapping("material/status")
    public WebResponseVo<List<ProductMaterialDisplayVo>> findMaterialByEnterpriseIdAndProductStatus(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                                    @RequestParam Long enterpriseId,
                                                                                                    @RequestParam ProductDevelopStatusEnum productDevelopStatus) {

        List<ProductMaterialDisplayVo> productMaterialDisplayVoList =
                productService.findProductMaterialInfoByEnterpriseIdAndProductStatus(enterpriseId, productDevelopStatus);

        if(productMaterialDisplayVoList.size() == 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "该企业对应的产品信息不存在");
        }

        return toSuccessResponseVoWithData(productMaterialDisplayVoList);
    }

}
