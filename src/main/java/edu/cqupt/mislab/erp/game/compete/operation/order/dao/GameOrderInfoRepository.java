package edu.cqupt.mislab.erp.game.compete.operation.order.dao;

import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.GameOrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface GameOrderInfoRepository extends JpaRepository<GameOrderInfo, Long> {

    //选取一个比赛的全部订单信息
    List<GameOrderInfo> findByGameBasicInfo_Id(long gameId);
}
