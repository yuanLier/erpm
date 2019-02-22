package edu.cqupt.mislab.erp.game.compete.operation.order.dao;

import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.GameOrderChooseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GameOrderChooseInfoRepository extends JpaSpecificationExecutor, JpaRepository<GameOrderChooseInfo, Long> {

    //查看某个比赛进行到那一轮了
    GameOrderChooseInfo findByOrderModelInfoBasicInfo_GameBasicInfo_Id(long gameId);

    //查看某个比赛的订单是否已经被初始化过了
    boolean existsByOrderModelInfoBasicInfo_GameBasicInfo_IdAndFinishedIsFalse(long gameId);
}
