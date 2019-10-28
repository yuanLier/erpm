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
     * 获取一个企业的全部订单信息
     * @param enterpriseId
     * @return
     */
    List<OrderDisplayVo> getAllOrderDisplayVos(Long enterpriseId);


    /**
     * 获取一个企业处于某种订单状态的订单信息
     * @param enterpriseId
     * @param orderStatus
     * @return
     */
    List<OrderDisplayVo> getAllOrderDisplayVosOfOneStatus(Long enterpriseId, boolean orderStatus);


    /**
     * 按订单交货（点击确认出库按钮后再调用
     * @param orderId
     * @return
     */
    WebResponseVo<String> deliveryOrder(Long orderId);

}
