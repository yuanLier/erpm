package edu.cqupt.mislab.erp.game.compete.operation.produce.service.factorymanagement;

import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryHistoryVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-06-02 20:22
 * @description
 */
public interface FactoryHistoryService {

    /**
     * 获取某一比赛中，处于某一周期时，各个存活中企业的厂房变动情况
     * @param gameId
     * @param period
     * @return
     */
    List<FactoryHistoryVo> findFactoryHistoryVoOfGameAndPeriod(Long gameId, Integer period);
}
