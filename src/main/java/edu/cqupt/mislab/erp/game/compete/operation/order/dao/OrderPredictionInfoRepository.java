package edu.cqupt.mislab.erp.game.compete.operation.order.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.OrderPredictionInfo;

import java.util.List;

/**
 * @author chuyunfei
 * @description 
 **/

public interface OrderPredictionInfoRepository extends BasicRepository<OrderPredictionInfo,Long> {

    /**
     * 选取某个比赛的全部预测信息
     * @param gameId
     * @return
     */
    List<OrderPredictionInfo> findByGameBasicInfo_Id(long gameId);
}
