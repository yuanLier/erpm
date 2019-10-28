package edu.cqupt.mislab.erp.game.compete.operation.order.service.impl;

import edu.cqupt.mislab.erp.commons.constant.FinanceOperationConstant;
import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.game.compete.operation.finance.service.FinanceService;
import edu.cqupt.mislab.erp.game.compete.operation.order.dao.GameOrderInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.GameOrderInfo;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.vo.OrderDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.order.service.OrderDeliveryService;
import edu.cqupt.mislab.erp.game.compete.operation.stock.dao.ProductStockInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.ProductStockInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toFailResponseVoWithMessage;
import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithNoData;

/**
 * @author yuanyiwen
 * @create 2019-04-07 19:55
 * @description
 */
@Service
public class OrderDeliveryServiceImpl implements OrderDeliveryService {

    @Autowired
    private GameOrderInfoRepository gameOrderInfoRepository;
    @Autowired
    private ProductStockInfoRepository productStockInfoRepository;

    @Autowired
    private FinanceService financeService;

    @Override
    public List<OrderDisplayVo> getAllOrderDisplayVos(Long enterpriseId) {

        List<GameOrderInfo> gameOrderInfoList = gameOrderInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId);

        List<OrderDisplayVo> orderDisplayVoList = new ArrayList<>();
        for (GameOrderInfo gameOrderInfo : gameOrderInfoList) {
            orderDisplayVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(gameOrderInfo));
        }

        return orderDisplayVoList;
    }

    @Override
    public List<OrderDisplayVo> getAllOrderDisplayVosOfOneStatus(Long enterpriseId, boolean orderStatus) {

        List<GameOrderInfo> gameOrderInfoList = gameOrderInfoRepository.findByEnterpriseBasicInfo_IdAndOrderStatus(enterpriseId, orderStatus);

        List<OrderDisplayVo> orderDisplayVoList = new ArrayList<>();
        for (GameOrderInfo gameOrderInfo : gameOrderInfoList) {
            orderDisplayVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(gameOrderInfo));
        }

        return orderDisplayVoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WebResponseVo<String> deliveryOrder(Long orderId) {

        // 根据订单id获取订单
        GameOrderInfo gameOrderInfo = gameOrderInfoRepository.findOne(orderId);

        // 获取企业中该产品的库存信息
        Long enterpriseId = gameOrderInfo.getEnterpriseBasicInfo().getId();
        Long productId = gameOrderInfo.getProductBasicInfo().getId();
        ProductStockInfo productStockInfo = productStockInfoRepository.findByEnterpriseBasicInfo_IdAndProductBasicInfo_Id(enterpriseId, productId);

        // 若库存数小于订单所需数量，交货失败
        if(productStockInfo.getProductNumber() < gameOrderInfo.getProductNumber()) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "交货失败！库存数量不足");
        }

        // 更新库存数量并进行持久化
        productStockInfo.setProductNumber(productStockInfo.getProductNumber()-gameOrderInfo.getProductNumber());
        productStockInfoRepository.save(productStockInfo);

        // 更新订单状态至已交货，并进行持久化
        gameOrderInfo.setOrderStatus(true);
        gameOrderInfoRepository.save(gameOrderInfo);

        // 当场打钱
        String changeOperating = FinanceOperationConstant.ORDER_DELIVERY;
        Double changeAmount = gameOrderInfo.getPrice()*gameOrderInfo.getProductNumber();
        financeService.updateFinanceInfo(enterpriseId, changeOperating, changeAmount, false, true);

        return toSuccessResponseVoWithNoData();
    }
}
