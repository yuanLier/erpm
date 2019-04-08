package edu.cqupt.mislab.erp.game.compete.operation.order.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.GameOrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface GameOrderInfoRepository extends BasicRepository<GameOrderInfo, Long> {

    //选取一个比赛的全部订单信息
    List<GameOrderInfo> findByGameBasicInfo_Id(long gameId);


    /**
     * @author yuanyiwen
     * @description 获取一个企业的全部订单信息
     * @date 22:35 2019/4/7
     **/
    List<GameOrderInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);


    /**
     * @author yuanyiwen
     * @description 获取一个企业的全部处于某种状态的订单信息
     * @date 22:36 2019/4/7
     **/
    List<GameOrderInfo> findByEnterpriseBasicInfo_IdAndOrderStatus(Long enterpriseId, boolean orderStatus);

}
