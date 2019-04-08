package edu.cqupt.mislab.erp.game.compete.operation.order.service;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.vo.OrderDisplayVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-04-07 19:55
 * @description 按订单交货
 */
public interface OrderDeliveryService {


    /**
     * @author yuanyiwen
     * @description 获取一个企业的全部订单信息
     * @date 22:26 2019/4/7
     **/
    List<OrderDisplayVo> getAllOrderDisplayVos(Long enterpriseId);


    /**
     * @author yuanyiwen
     * @description 获取一个企业处于某种订单状态的订单信息
     * @date 22:06 2019/4/7
     **/
    List<OrderDisplayVo> getAllOrderDisplayVosOfOneStatus(Long enterpriseId, boolean orderStatus);


    /**
     * @author yuanyiwen
     * @description 按订单交货（点击确认出库按钮后再调用
     * @date 22:28 2019/4/7
     **/
    WebResponseVo<String> deliveryOrder(Long orderId);

}
