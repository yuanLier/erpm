package edu.cqupt.mislab.erp.game.compete.operation.order.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.GameOrderInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-04-07 22:35
 * @description
 */
public interface GameOrderInfoRepository extends BasicRepository<GameOrderInfo, Long>, JpaSpecificationExecutor {

    /**
     * 选取一个比赛的全部订单信息
     * @param gameId
     * @return
     */
    List<GameOrderInfo> findByGameBasicInfo_Id(long gameId);


    /**
     * 获取一个企业的全部订单信息
     * @param enterpriseId
     * @return
     */
    List<GameOrderInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);


    /**
     * 获取一个企业的全部处于某种状态的订单信息
     * @param enterpriseId
     * @param orderStatus
     * @return
     */
    List<GameOrderInfo> findByEnterpriseBasicInfo_IdAndOrderStatus(Long enterpriseId, boolean orderStatus);


    /**
     * 获取某场比赛处于某一年时，全部生成的订单
     * @param gameId
     * @param year
     * @return
     */
    List<GameOrderInfo> findByGameBasicInfo_IdAndYear(Long gameId, Integer year);


    /**
     * 获取某场比赛处于某一年时，订单池中剩余的订单
     * @param gameId
     * @param year
     * @return
     */
    List<GameOrderInfo> findByGameBasicInfo_IdAndYearAndSelectedIsTrueAndEnterpriseBasicInfoIsNull(Long gameId, Integer year);
}
