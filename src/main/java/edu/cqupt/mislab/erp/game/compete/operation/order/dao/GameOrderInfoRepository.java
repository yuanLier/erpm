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
     * 获取某场比赛处于某年时，综合企业已有的市场产品ISO选出的订单
     *  （没办法，jpa中and的优先级比or高，所以只能拆开写，中间用or连接
     *  （或者也可以改成 @Query，然后手动用括号表达逻辑顺序
     * @param gameId0 哪场比赛
     * @param year0 哪一年的订单
     * @param isoBasicIdList0 iso认证率不足某一比率的isoId
     * @param marketIdList 市场占有率高于某一比率的marketId
     * @param gameId1 哪场比赛，值同gameId0
     * @param year1 哪一年的订单，值同year0
     * @param isoBasicIdList1 iso认证率不足某一比率的isoId，同上
     * @param productIdList 产品研发率高于某一比率的productId
     * @return
     */
    List<GameOrderInfo> findByGameBasicInfo_IdAndYearAndIsoBasicInfo_IdNotInAndMarketBasicInfo_IdInOrGameBasicInfo_IdAndYearAndIsoBasicInfo_IdNotInAndProductBasicInfo_IdIn(Long gameId0, Integer year0, List<Long> isoBasicIdList0, List<Long> marketIdList,
                                                                                                                                                                            Long gameId1, Integer year1, List<Long> isoBasicIdList1, List<Long> productIdList);

    /**
     * 获取某场比赛处于某一年时，可被选取的订单
     * @param gameId
     * @param year
     * @return
     */
    List<GameOrderInfo> findByGameBasicInfo_IdAndYearAndSelectedIsTrue(Long gameId, Integer year);
}
