package edu.cqupt.mislab.erp.game.compete.operation.product.service;

import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductHistoryVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-16 21:38
 * @description
 */
public interface ProductHistoryService {
    /**
     * 获取某一比赛中，处于某一周期时，各个存活中企业的产品研发情况
     * @param gameId
     * @param period
     * @return
     */
    List<ProductHistoryVo> findProductHistoryVoOfGameAndPeriod(Long gameId, Integer period);


    /**
     * 获取某一比赛中的总期数
     * @param gameId
     * @return
     */
    Integer getTotalPeriod(Long gameId);
}
