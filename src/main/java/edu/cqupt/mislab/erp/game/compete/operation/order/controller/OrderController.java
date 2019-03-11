package edu.cqupt.mislab.erp.game.compete.operation.order.controller;

import edu.cqupt.mislab.erp.game.compete.operation.order.service.OrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/order")
public class OrderController {

    @Autowired private OrderService orderService;

    /* 
     * @Author: chuyunfei
     * @Date: 2019/3/6 20:10
     * @Description: 以下部分为投广告操作期间的接口
     **/

    @GetMapping("/test")
    public void get(){


    }

}
