package edu.cqupt.mislab.erp.game.compete.operation.order.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.OrderPredictionInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderPredictionInfoRepository extends BasicRepository<OrderPredictionInfo,Long> {

    //选取某个比赛的全部预测信息
    List<OrderPredictionInfo> findByGameBasicInfo_Id(long gameId);
}
