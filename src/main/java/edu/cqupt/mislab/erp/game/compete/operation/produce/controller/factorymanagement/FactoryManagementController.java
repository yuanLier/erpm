package edu.cqupt.mislab.erp.game.compete.operation.produce.controller.factorymanagement;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryHoldingInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineProduceInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.*;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineDevelopStatus;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineDevelopDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineDevelopVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.service.factorymanagement.FactoryManagementService;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
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
 * @create 2019-03-24 17:58
 * @description
 */
@Api
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/produce/factorymanagement")
public class FactoryManagementController {

    @Autowired
    private FactoryManagementService factoryManagementService;


    @ApiOperation(value = "获取全部可新建的类型生产线")
    @GetMapping("/prodline/all/type/get")
    public WebResponseVo<List<ProdlineDevelopVo>> getAllProdlineTypeVos() {
        List<ProdlineDevelopVo> prodlineDevelopVoList = factoryManagementService.getAllProdlineDevelopVos();

        if(prodlineDevelopVoList.size() == 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "最新版本下的生产线类型获取失败，请联系管理员");
        }

        return toSuccessResponseVoWithData(prodlineDevelopVoList);
    }



    @ApiOperation(value = "新建生产线")
    @PostMapping("/prodline/develop")
    public WebResponseVo<ProdlineDevelopDisplayVo> buildProdlineOfHoldingFactory(@Exist(repository = ProdlineBasicInfoRepository.class)
                                                                                             @RequestParam Long prodlineBasicId,
                                                                                 @Exist(repository = ProductDevelopInfoRepository.class)
                                                                                             @RequestParam Long productId,
                                                                                 @Exist(repository = FactoryHoldingInfoRepository.class)
                                                                                             @RequestParam Long factoryId,
                                                                                 @Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                                             @RequestParam Long enterpriseId) {

        ProdlineDevelopDisplayVo prodlineDevelopDisplayVo = factoryManagementService.buildProdlineOfHoldingFactory(prodlineBasicId, productId, factoryId, enterpriseId);

        if(prodlineDevelopDisplayVo == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.INTERNAL_SERVER_ERROR, "生产线新建失败！请联系开发人员");
        }

        return toSuccessResponseVoWithData(prodlineDevelopDisplayVo);
    }




    @ApiOperation(value = "暂停安装")
    @PutMapping("/prodline/develop/pause")
    public WebResponseVo<ProdlineDevelopDisplayVo> updateProdlineDevelopStatusToPause(@Exist(repository = ProdlineDevelopInfoRepository.class)
                                                                                                  @RequestParam Long prodlineDevelopId) {

        ProdlineDevelopDisplayVo prodlineDevelopDisplayVo = factoryManagementService.updateProdlineDevelopStatus(prodlineDevelopId, ProdlineDevelopStatus.DEVELOPPAUSE);

        if(prodlineDevelopDisplayVo == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.INTERNAL_SERVER_ERROR, "生产线暂停安装失败！请联系开发人员");
        }

        return toSuccessResponseVoWithData(prodlineDevelopDisplayVo);
    }





    @ApiOperation(value = "继续安装")
    @PutMapping("/prodline/develop/developing")
    public WebResponseVo<ProdlineDevelopDisplayVo> updateProdlineDevelopStatusToDeveloping(@Exist(repository = ProdlineDevelopInfoRepository.class)
                                                                                               @RequestParam Long prodlineDevelopId) {

        ProdlineDevelopDisplayVo prodlineDevelopDisplayVo = factoryManagementService.updateProdlineDevelopStatus(prodlineDevelopId, ProdlineDevelopStatus.DEVELOPING);

        if(prodlineDevelopDisplayVo == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.INTERNAL_SERVER_ERROR, "生产线继续安装失败！请联系开发人员");
        }

        return toSuccessResponseVoWithData(prodlineDevelopDisplayVo);
    }



    @ApiOperation(value = "查看厂房详情")
    @GetMapping("/factory/detail/get")
    public WebResponseVo<FactoryDetailVo> getFactoryDetailVo(@Exist(repository = FactoryHoldingInfoRepository.class)
                                                                 @RequestParam Long factoryId) {

        FactoryDetailVo factoryDetailVo = factoryManagementService.getFactoryDetailVo(factoryId);

        return toSuccessResponseVoWithData(factoryDetailVo);
    }



    @ApiOperation(value = "获取全部可新建类型厂房")
    @GetMapping("/factory/type/all/get")
    public WebResponseVo<List<FactoryDevelopVo>> getAllFactoryTypeVos() {

        List<FactoryDevelopVo> factoryDevelopVoList = factoryManagementService.getAllFactoryDevelopVos();

        if(factoryDevelopVoList.size() == 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "最新版本下的厂房类型获取失败，请联系管理员");
        }

        return toSuccessResponseVoWithData(factoryDevelopVoList);
    }



    @ApiOperation(value = "新建厂房")
    @PostMapping("/factory/develop")
    public WebResponseVo<FactoryDevelopDisplayVo> buildFactoryOfEnterprise(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                               @RequestParam Long enterpriseId,
                                                                           @Exist(repository = FactoryBasicInfoRepository.class)
                                                                                @RequestParam Long factoryBasicId) {

        FactoryDevelopDisplayVo factoryDevelopDisplayVo = factoryManagementService.buildFactoryOfEnterprise(enterpriseId, factoryBasicId);

        if(factoryDevelopDisplayVo == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.INTERNAL_SERVER_ERROR, "厂房新建失败！请联系开发人员");
        }

        return toSuccessResponseVoWithData(factoryDevelopDisplayVo);
    }




    @ApiOperation(value = "暂停修建")
    @PutMapping("/factory/develop/pause")
    public WebResponseVo<FactoryDevelopDisplayVo> updateFactoryDevelopStatusToPause(@Exist(repository = FactoryDevelopInfoRepository.class)
                                                                                                @RequestParam Long factoryDevelopId) {

        FactoryDevelopDisplayVo factoryDevelopDisplayVo = factoryManagementService.updateFactoryDevelopStatus(factoryDevelopId, false);

        if(factoryDevelopDisplayVo == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.INTERNAL_SERVER_ERROR, "厂房暂停修建失败!请联系开发人员");
        }

        return toSuccessResponseVoWithData(factoryDevelopDisplayVo);
    }


    @ApiOperation(value = "继续修建")
    @PutMapping("/factory/develop/developing")
    public WebResponseVo<FactoryDevelopDisplayVo> updateFactoryDevelopStatusToDeveloping(@Exist(repository = FactoryDevelopInfoRepository.class)
                                                                                             @RequestParam Long factoryDevelopId) {

        FactoryDevelopDisplayVo factoryDevelopDisplayVo = factoryManagementService.updateFactoryDevelopStatus(factoryDevelopId, true);

        if(factoryDevelopDisplayVo == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.INTERNAL_SERVER_ERROR, "厂房继续修建失败!请联系开发人员");
        }

        return toSuccessResponseVoWithData(factoryDevelopDisplayVo);
    }



    @ApiOperation(value = "获取一个企业全部修建中的厂房")
    @GetMapping("/factory/develop/all/get")
    public WebResponseVo<List<FactoryDevelopDisplayVo>> getAllFactoryDevelopVos(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                                @RequestParam Long enterpriseId) {

        List<FactoryDevelopDisplayVo> factoryDevelopDisplayVoList = factoryManagementService.getAllFactoryDevelopVos(enterpriseId);

        return toSuccessResponseVoWithData(factoryDevelopDisplayVoList);
    }



    @ApiOperation(value = "查看修建中的厂房详情")
    @GetMapping("/factory/develop/detail/get")
    public WebResponseVo<FactoryDevelopDetailVo> getFactoryDevelopDetailVo(@Exist(repository = FactoryDevelopInfoRepository.class)
                                                                               @RequestParam Long factoryDevelopId) {

        FactoryDevelopDetailVo factoryDevelopDetailVo = factoryManagementService.getFactoryDevelopDetailVo(factoryDevelopId);

        return toSuccessResponseVoWithData(factoryDevelopDetailVo);
    }



    @ApiOperation(value = "厂房出售")
    @PostMapping("/factory/sell")
    public WebResponseVo<String> factorySell(@Exist(repository = FactoryHoldingInfoRepository.class)
                                                         @RequestParam Long factoryId) {

        return factoryManagementService.factorySell(factoryId);
    }



    @ApiOperation(value = "生产线出售")
    @PostMapping("prodline/sell")
    public WebResponseVo<String> prodlineSell(@Exist(repository = ProdlineProduceInfoRepository.class)
                                                          @RequestParam Long prodlineProductId) {

        return factoryManagementService.prodlineSell(prodlineProductId);
    }



    @ApiOperation(value = "获取全部能租赁的厂房类型")
    @GetMapping("factory/lease/type/all/get")
    public WebResponseVo<List<FactoryLeaseVo>> getAllFactoryLeaseVos() {

        List<FactoryLeaseVo> factoryLeaseVoList = factoryManagementService.getAllFactoryLeaseVos();

        if(factoryLeaseVoList.size() == 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "最新版本下的厂房类型获取失败，请联系管理员");
        }

        return toSuccessResponseVoWithData(factoryLeaseVoList);
    }



    @ApiOperation(value = "租厂房")
    @PostMapping("factory/lease")
    public WebResponseVo<FactoryDisplayVo> factoryLease(@Exist(repository = FactoryBasicInfoRepository.class)
                                                                    @RequestParam Long factoryBasicId,
                                                        @Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                    @RequestParam Long enterpriseId) {

        FactoryDisplayVo factoryDisplayVo = factoryManagementService.factoryLease(factoryBasicId, enterpriseId);

        if(factoryDisplayVo == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.INTERNAL_SERVER_ERROR, "厂房租赁失败!请联系开发人员");
        }

        return toSuccessResponseVoWithData(factoryDisplayVo);
    }



    @ApiOperation(value = "暂停租赁")
    @PutMapping("factory/lease/pause")
    public WebResponseVo<FactoryDisplayVo> updateFactoryLeaseStatusToPause(@Exist(repository = FactoryHoldingInfoRepository.class)
                                                                                @RequestParam Long factoryId) {

        FactoryDisplayVo factoryDisplayVo = factoryManagementService.updateFactoryLeaseStatus(factoryId, false);

        if (factoryDisplayVo == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.INTERNAL_SERVER_ERROR, "暂停租赁失败!请联系开发人员");
        }

        return toSuccessResponseVoWithData(factoryDisplayVo);
    }


    @ApiOperation(value = "继续租赁")
    @PutMapping("factory/lease/leasing")
    public WebResponseVo<FactoryDisplayVo> updateFactoryLeaseStatusToLeasing(@Exist(repository = FactoryHoldingInfoRepository.class)
                                                                        @RequestParam Long factoryId) {

        FactoryDisplayVo factoryDisplayVo = factoryManagementService.updateFactoryLeaseStatus(factoryId, true);

        if (factoryDisplayVo == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.INTERNAL_SERVER_ERROR, "继续租赁失败!请联系开发人员");
        }

        return toSuccessResponseVoWithData(factoryDisplayVo);
    }



}
