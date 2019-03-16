package edu.cqupt.mislab.erp.game.compete.operation.produce.controller.productionplan;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.service.ProductionPlanService;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductTypeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toFailResponseVoWithMessage;
import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithData;

/**
 * @author yuanyiwen
 * @create 2019-03-09 20:21
 * @description
 */
@Api
@CrossOrigin
@RestController
@RequestMapping("/game/compete/operation/produce/productionplan")
public class ProductionPlanController {

    @Autowired
    private ProductionPlanService productionPlanService;

//    @ApiOperation(value = "获取某个企业的全部可生产的产品类型")
//    @GetMapping("/product/type/get")
//    public WebResponseVo<List<ProductTypeVo>> getProductTypeVo(Long enterpriseId) {
//        List<ProductTypeVo> productTypeVoList = productionPlanService.getProductTypeOfEnterprise(enterpriseId);
//
//        if(productTypeVoList == null) {
//            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "该企业没有可生产的产品");
//        }
//
//        return toSuccessResponseVoWithData(productTypeVoList);
//    }
//
//    @ApiOperation(value = "获取某个企业的全部可使用的厂房类型")
//    @GetMapping("/factory/type/get")
//    public WebResponseVo<List<FactoryTypeVo>> getFactoryTypeVo(Long enterpriseId) {
//        List<FactoryTypeVo> factoryTypeVoList = productionPlanService.getFactoryTypeOfEnterprise(enterpriseId);
//
//        if(factoryTypeVoList == null) {
//            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "该企业没有空闲厂房");
//        }
//
//        return toSuccessResponseVoWithData(factoryTypeVoList);
//    }
//
//    @ApiOperation(value = "获取某个厂房中全部可使用的生产线类型")
//    @GetMapping("/prodline/type/get")
//    public WebResponseVo<List<ProdlineTypeVo>> getProdlineTypeVo(Long factoryId) {
//        List<ProdlineTypeVo> prodlineTypeVoList = productionPlanService.getProdlineTypeOfEnterprise(factoryId);
//
//        if(prodlineTypeVoList == null) {
//            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "该厂房中没有空闲生产线");
//        }
//
//        return toSuccessResponseVoWithData(prodlineTypeVoList);
//    }

}
