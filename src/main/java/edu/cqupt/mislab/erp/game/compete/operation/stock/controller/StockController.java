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

import javax.validation.Valid;
import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toFailResponseVoWithMessage;
import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithData;

/**
 * @author yuanyiwen
 * @description
 **/

@Api
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @ApiOperation(value = "展示某一企业的原材料库存信息")
    @GetMapping("/material")
    public WebResponseVo<List<MaterialStockDisplayVo>> getMaterialStockVosOfEnterprise(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                                                   @RequestParam Long enterpriseId) {

        List<MaterialStockDisplayVo> materialStockDisplayVoList = stockService.getMaterialStockVosOfEnterprise(enterpriseId);

        return toSuccessResponseVoWithData(materialStockDisplayVoList);
    }


    @ApiOperation(value = "展示当前设定下的全部可用的运输方式")
    @GetMapping("/transport")
    public WebResponseVo<List<TransportMethodDisplayVo>> getAllTransportVos() {

        List<TransportMethodDisplayVo> transportMethodDisplayVoList = stockService.getAllTransportVos();

        if (transportMethodDisplayVoList.size() == 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "无可用的运输方式，请联系管理员");
        }

        return toSuccessResponseVoWithData(transportMethodDisplayVoList);
    }

    @ApiOperation(value = "根据id获取某种运输方式详情")
    @GetMapping("/transport/detail")
    public WebResponseVo<TransportMethodDisplayVo> getTransportVoyId(@Exist(repository = TransportBasicInfoRepository.class)
                                                                                 @RequestParam Long transportBasicId) {

        TransportMethodDisplayVo transportMethodDisplayVo = stockService.getTransportVoyId(transportBasicId);

        return toSuccessResponseVoWithData(transportMethodDisplayVo);
    }


    @ApiOperation(value = "提交订单")
    @PostMapping("/material/order/submit")
    public WebResponseVo<List<MaterialOrderDisplayVo>> submitMaterialOrder(@RequestBody @Valid List<MaterialOrderDto> materialOrderDtoList) {

        if(materialOrderDtoList == null || materialOrderDtoList.size() == 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "订单未有效提交！");
        }

        List<MaterialOrderDisplayVo> materialOrderDisplayVoList = stockService.submitMaterialOrder(materialOrderDtoList);

        if (materialOrderDisplayVoList.size() == 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.INTERNAL_SERVER_ERROR, "订单信息保存失败！请联系开发人员");
        }

        return toSuccessResponseVoWithData(materialOrderDisplayVoList);
    }


    @ApiOperation(value = "获取一个企业的全部订单情况")
    @PostMapping("/material/order/all")
    public WebResponseVo<List<MaterialOrderDisplayVo>> getAllMaterialOrdersOfEnterprise(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                                            @RequestParam Long enterpriseId) {

        List<MaterialOrderDisplayVo> materialOrderDisplayVoList = stockService.getAllMaterialOrdersOfEnterprise(enterpriseId);

        return toSuccessResponseVoWithData(materialOrderDisplayVoList);
    }


    @ApiOperation(value = "更新材料运送状态至已审核")
    @PutMapping("/material/transport/status/checked")
    public WebResponseVo<MaterialOrderDisplayVo> updateTransportStatusToChecked(@Exist(repository = MaterialOrderInfoRepository.class)
                                                                                   @RequestParam Long materialOrderId) {

        MaterialOrderDisplayVo materialOrderDisplayVo = stockService.updateTransportStatus(materialOrderId, TransportStatusEnum.CHECKED);

        if(materialOrderDisplayVo == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.INTERNAL_SERVER_ERROR, "审核失败！请联系开发人员");
        }

        return toSuccessResponseVoWithData(materialOrderDisplayVo);
    }


    @ApiOperation(value = "根据材料采购订单id获取原料入库的具体信息")
    @GetMapping("/material/detail")
    public WebResponseVo<MaterialOrderDetailVo> getMaterialOrderDetailVos(@Exist(repository = MaterialOrderInfoRepository.class)
                                                                                      @RequestParam Long materialOrderId) {

        MaterialOrderDetailVo materialOrderDetailVo = stockService.getMaterialOrderDetailVos(materialOrderId);

        return toSuccessResponseVoWithData(materialOrderDetailVo);
    }


    @ApiOperation(value = "展示某一企业的产品库存信息")
    @GetMapping("/product")
    public WebResponseVo<List<ProductStockDisplayVo>> getProductStockVosOfEnterprise(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                                                 @RequestParam Long enterpriseId) {

        List<ProductStockDisplayVo> productStockDisplayVoList = stockService.getProductStockVosOfEnterprise(enterpriseId);

        return toSuccessResponseVoWithData(productStockDisplayVoList);
    }


    @ApiOperation(value = "原材料售卖")
    @PostMapping("/material/sell")
    public WebResponseVo<String> sellMaterial(@Exist(repository = MaterialStockInfoRepository.class)
                                                          @RequestParam Long materialStockId,
                                              @RequestParam Integer sellNumber) {

        if(sellNumber < 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "出售数量小于0！请重新选择");
        }

        return stockService.sellMaterial(materialStockId, sellNumber);
    }


    @ApiOperation(value = "产品售卖")
    @PostMapping("/product/sell")
    public WebResponseVo<String> sellProduct(@Exist(repository = ProductStockInfoRepository.class)
                                                         @RequestParam Long productStockId,
                                             @RequestParam Integer sellNumber) {

        if(sellNumber < 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "出售数量小于0！请重新选择");
        }

        return stockService.sellProduct(productStockId, sellNumber);
    }
}
