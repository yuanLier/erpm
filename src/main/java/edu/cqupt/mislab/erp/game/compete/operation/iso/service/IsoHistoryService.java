package edu.cqupt.mislab.erp.game.compete.operation.iso.service;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoHistoryVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-12 18:16
 * @description
 */
public interface IsoHistoryService {

    /**
     * 获取某一比赛中，处于某一周期时，各个存活中企业的iso认证情况
     * @param gameId
     * @param period
     * @return
     */
    List<IsoHistoryVo> findIsoHistoryVoOfGameAndPeriod(Long gameId, Integer period);


    /**
     * 获取某一比赛中的总期数
     * @param gameId
     * @return
     */
    Integer getTotalPeriod(Long gameId);
}
