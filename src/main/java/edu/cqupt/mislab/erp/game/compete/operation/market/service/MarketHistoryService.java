package edu.cqupt.mislab.erp.game.compete.operation.market.service;

import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketHistoryVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-15 20:03
 * @description
 */
public interface MarketHistoryService {

    /**
     * 获取某一比赛中，处于某一周期时，各个存活中企业的市场开拓情况
     * @param gameId
     * @param period
     * @return
     */
    List<MarketHistoryVo> findMarketHistoryVoOfGameAndPeriod(Long gameId, Integer period);


    /**
     * 获取某一比赛中的总期数
     * @param gameId
     * @return
     */
    Integer getTotalPeriod(Long gameId);
}
