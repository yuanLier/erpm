package edu.cqupt.mislab.erp.game.compete.operation.produce.controller.productionplan;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineProduceInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryProdlineTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineProduceStatus;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineDetailVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProductProduceVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.service.productionplan.ProductionPlanService;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductTypeVo;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithData;

/**
 * @author yuanyiwen
 * @create 2019-03-09 20:21
 * @description
 */
@Api(description = "学生端-生产计划")
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/produce/productionplan")
public class ProductionPlanController {

    @Autowired
    private ProductionPlanService productionPlanService;


    @ApiOperation(value = "获取某个企业的全部可生产的产品类型")
    @GetMapping("/product/type")
    public WebResponseVo<List<ProductTypeVo>> getProducableProduct(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                               @RequestParam Long enterpriseId) {

        List<ProductTypeVo> productTypeVoList = productionPlanService.getProducableProduct(enterpriseId);

        return toSuccessResponseVoWithData(productTypeVoList);
    }


    @ApiOperation(value = "获取企业中的可生产某种产品的全部生产线及所在厂房的类型")
    @GetMapping("/prodline/type")
    public WebResponseVo<List<FactoryProdlineTypeVo>> getProducableFactoryAndProdline(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                                            @RequestParam Long enterpriseId,
                                                                                      @Exist(repository = ProductDevelopInfoRepository.class)
                                                                                            @RequestParam Long productId) {

        List<FactoryProdlineTypeVo> factoryProdlineTypeVoList = productionPlanService.getProducableFactoryAndProdline(enterpriseId, productId);

        return toSuccessResponseVoWithData(factoryProdlineTypeVoList);
    }


    @ApiOperation(value = "获取企业中某一产品的全部生产情况")
    @GetMapping("/prodline/produce")
    public WebResponseVo<List<ProductProduceVo>> getProductProduceVosOfOneProduct(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                                          @RequestParam Long enterpriseId,
                                                                                  @Exist(repository = ProductDevelopInfoRepository.class)
                                                                                          @RequestParam Long productId) {

        List<ProductProduceVo> productProduceVoList = productionPlanService.getProductProduceVosOfOneProduct(enterpriseId, productId);

        return toSuccessResponseVoWithData(productProduceVoList);
    }


    @ApiOperation(value = "查看产品的某一生产情况")
    @GetMapping("/factory/display")
    public WebResponseVo<FactoryDisplayVo> getFactoryDisplayVo(@Exist(repository = ProdlineProduceInfoRepository.class)
                                                                       @RequestParam Long prodlineProduceId) {

        return toSuccessResponseVoWithData(productionPlanService.getFactoryDisplayVo(prodlineProduceId));
    }


    @ApiOperation(value = "获取企业全部的生产信息")
    @GetMapping("/factory/display/all")
    public WebResponseVo<List<FactoryDisplayVo>> getAllFactoryDisplayVos(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                                 @RequestParam Long enterpriseId) {

        List<FactoryDisplayVo> factoryDisplayVoList = productionPlanService.getAllFactoryDisplayVos(enterpriseId);

        return toSuccessResponseVoWithData(factoryDisplayVoList);
    }


    @ApiOperation(value = "获取生产线详情")
    @GetMapping("/prodline/detail")
    public WebResponseVo<ProdlineDetailVo> getProdlineDetailVo(@Exist(repository = ProdlineProduceInfoRepository.class)
                                                                       @RequestParam Long prodlineId) {

        return toSuccessResponseVoWithData(productionPlanService.getProdlineDetailVo(prodlineId));
    }



    @ApiOperation(value = "开始生产")
    @PutMapping("/prodline/produce")
    public WebResponseVo<ProductProduceVo> productProduction(@Exist(repository = ProdlineProduceInfoRepository.class)
                                                                     @RequestParam Long prodlineId) {

        ProductProduceVo productProduceVo = productionPlanService.productProduction(prodlineId);

        return toSuccessResponseVoWithData(productProduceVo);
    }

    @ApiOperation(value = "暂停生产")
    @PutMapping("/prodline/produce/pause")
    public WebResponseVo<ProductProduceVo> updateProduceStatusToPausing(@Exist(repository = ProdlineProduceInfoRepository.class)
                                                                            @RequestParam Long prodlineId) {

        ProductProduceVo productProduceVo = productionPlanService.updateProduceStatus(prodlineId, ProdlineProduceStatus.PRODUCEPAUSE);

        return toSuccessResponseVoWithData(productProduceVo);
    }


    @ApiOperation(value = "继续生产")
    @PutMapping("/prodline/produce/producing")
    public WebResponseVo<ProductProduceVo> updateProduceStatusToProducing(@Exist(repository = ProdlineProduceInfoRepository.class)
                                                                            @RequestParam Long prodlineId) {

        ProductProduceVo productProduceVo = productionPlanService.updateProduceStatus(prodlineId, ProdlineProduceStatus.PRODUCING);

        return toSuccessResponseVoWithData(productProduceVo);
    }

}
