package edu.cqupt.mislab.erp.game.compete.operation.order.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.order.dao.GameOrderInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.vo.OrderDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.order.service.OrderDeliveryService;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithData;

@Api(description = "学生端-订单管理")
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/order")
public class OrderController {


    @Autowired
    private OrderDeliveryService orderDeliveryService;


    /* 
     * @Author: chuyunfei
     * @Date: 2019/3/6 20:10
     * @Description: 以下部分为投广告操作期间的接口 -----但还没写
     *            --> 所以以下其实是销售管理即按订单交货之类的接口 你可爱的yyw留
     **/


    @ApiOperation(value = "获取一个企业的全部订单信息")
    @GetMapping("/all/get")
    WebResponseVo<List<OrderDisplayVo>> getAllOrderDisplayVos(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                      @RequestParam Long enterpriseId) {

        List<OrderDisplayVo> orderDisplayVoList = orderDeliveryService.getAllOrderDisplayVos(enterpriseId);

        return toSuccessResponseVoWithData(orderDisplayVoList);
    }


    @ApiOperation(value = "获取一个企业的全部未交货订单信息")
    @GetMapping("/undelivery/get")
    WebResponseVo<List<OrderDisplayVo>> getAllUnDeliveryOrderDisplayVos(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                            @RequestParam Long enterpriseId) {

        List<OrderDisplayVo> orderDisplayVoList = orderDeliveryService.getAllOrderDisplayVosOfOneStatus(enterpriseId,false);

        return toSuccessResponseVoWithData(orderDisplayVoList);
    }


    @ApiOperation(value = "获取一个企业的全部已交货订单信息")
    @GetMapping("/deliveried/get")
    WebResponseVo<List<OrderDisplayVo>> getAllDeliveriedOrderDisplayVos(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                            @RequestParam Long enterpriseId) {

        List<OrderDisplayVo> orderDisplayVoList = orderDeliveryService.getAllOrderDisplayVosOfOneStatus(enterpriseId,true);

        return toSuccessResponseVoWithData(orderDisplayVoList);
    }

    @ApiOperation(value = "提交订单")
    @PostMapping("/delivery")
    WebResponseVo<String> deliveryOrder(@Exist(repository = GameOrderInfoRepository.class)
                                            @RequestParam Long orderId) {

        return orderDeliveryService.deliveryOrder(orderId);
    }




}
