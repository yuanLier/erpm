package edu.cqupt.mislab.erp.game.compete.operation.stock.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.stock.dao.MaterialOrderInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.dao.MaterialStockInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.dao.ProductStockInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.dao.TransportBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.dto.MaterialOrderDto;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.TransportStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo.*;
import edu.cqupt.mislab.erp.game.compete.operation.stock.service.StockService;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toFailResponseVoWithMessage;
import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithData;
import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithNoData;

@Api
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/stock")
public class StockController {

    @Autowired
    private StockService stockService;


    @ApiOperation(value = "展示某一企业的原材料库存信息")
    @GetMapping("/material/info/get")
    public WebResponseVo<List<MaterialStockDisplayVo>> getMaterialStockVosOfEnterprise(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                                                   @RequestParam Long enterpriseId) {

        List<MaterialStockDisplayVo> materialStockDisplayVoList = stockService.getMaterialStockVosOfEnterprise(enterpriseId);

        if(materialStockDisplayVoList == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "该企业无原材料库存");
        }

        return toSuccessResponseVoWithData(materialStockDisplayVoList);
    }


    @ApiOperation(value = "展示当前设定下的全部可用的运输方式")
    @GetMapping("/transport/info/get")
    public WebResponseVo<List<TransportMethodDisplayVo>> getAllTransportVos() {

        List<TransportMethodDisplayVo> transportMethodDisplayVoList = stockService.getAllTransportVos();

        if (transportMethodDisplayVoList == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "无可用的运输方式，请联系管理员");
        }

        return toSuccessResponseVoWithData(transportMethodDisplayVoList);
    }

    @ApiOperation(value = "根据id获取某种运输方式详情")
    @GetMapping("/transport/detail/get")
    public WebResponseVo<TransportMethodDisplayVo> getTransportVoyId(@Exist(repository = TransportBasicInfoRepository.class)
                                                                                 @RequestParam Long transportBasicId) {

        TransportMethodDisplayVo transportMethodDisplayVo = stockService.getTransportVoyId(transportBasicId);

        if(transportMethodDisplayVo == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "详情获取失败");
        }

        return toSuccessResponseVoWithData(transportMethodDisplayVo);
    }


    @ApiOperation(value = "提交订单")
    @PostMapping("/material/order/submit")
    public WebResponseVo<List<MaterialOrderDisplayVo>> submitMaterialOrder(@RequestBody List<MaterialOrderDto> materialOrderDtoList) {

        List<MaterialOrderDisplayVo> materialOrderDisplayVoList = stockService.submitMaterialOrder(materialOrderDtoList);

        if (materialOrderDisplayVoList == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.INTERNAL_SERVER_ERROR, "订单提交失败！");
        }

        return toSuccessResponseVoWithData(materialOrderDisplayVoList);
    }


    @ApiOperation(value = "更新材料运送状态至已审核")
    @PutMapping("/material/transport/status/checked")
    public WebResponseVo<MaterialOrderDisplayVo> updateTransportStatusToChecked(@Exist(repository = MaterialOrderInfoRepository.class)
                                                                                   @RequestParam Long materialOrderId) {

        MaterialOrderDisplayVo materialOrderDisplayVo = stockService.updateTransportStatus(materialOrderId, TransportStatusEnum.CHECKED);

        if(materialOrderDisplayVo == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.INTERNAL_SERVER_ERROR, "审核失败！请稍后再试");
        }

        return toSuccessResponseVoWithData(materialOrderDisplayVo);
    }


    @ApiOperation(value = "根据材料采购订单id获取原料入库的具体信息")
    @GetMapping("/material/detail/get")
    public WebResponseVo<MaterialOrderDetailVo> getMaterialOrderDetailVos(@Exist(repository = MaterialOrderInfoRepository.class)
                                                                                      @RequestParam Long materialOrderId) {

        MaterialOrderDetailVo materialOrderDetailVo = stockService.getMaterialOrderDetailVos(materialOrderId);

        if(materialOrderDetailVo == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "详情获取失败");
        }

        return toSuccessResponseVoWithData(materialOrderDetailVo);
    }


    @ApiOperation(value = "展示某一企业的产品库存信息")
    @GetMapping("/product/info/get")
    public WebResponseVo<List<ProductStockDisplayVo>> getProductStockVosOfEnterprise(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                                                 @RequestParam Long enterpriseId) {

        List<ProductStockDisplayVo> productStockDisplayVoList = stockService.getProductStockVosOfEnterprise(enterpriseId);

        if(productStockDisplayVoList == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "该企业产品库存为空");
        }

        return toSuccessResponseVoWithData(productStockDisplayVoList);
    }


    @ApiOperation(value = "原材料售卖")
    @PostMapping("/material/sell")
    public WebResponseVo<String> sellMaterial(@Exist(repository = MaterialStockInfoRepository.class)
                                                          @RequestParam Long materialStockId,
                                              @RequestParam Integer sellNumber) {

        String errorMessage = stockService.sellMaterial(materialStockId, sellNumber);

        if(errorMessage != null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.INTERNAL_SERVER_ERROR, errorMessage);
        }

        return toSuccessResponseVoWithNoData();
    }


    @ApiOperation(value = "产品售卖")
    @PostMapping("/product/sell")
    public WebResponseVo<String> sellProduct(@Exist(repository = ProductStockInfoRepository.class)
                                                         @RequestParam Long productStockId,
                                             @RequestParam Integer sellNumber) {

        String errorMessage = stockService.sellProduct(productStockId, sellNumber);

        if(errorMessage != null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.INTERNAL_SERVER_ERROR, errorMessage);
        }

        return toSuccessResponseVoWithNoData();
    }

}
