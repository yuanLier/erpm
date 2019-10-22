package edu.cqupt.mislab.erp.game.compete.operation.produce.service.history;

import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryHistoryVo;

import java.util.List;
import java.util.Map;

/**
 * @author yuanyiwen
 * @create 2019-06-02 20:22
 * @description
 */
public interface FactoryHistoryService {


    /**
     * 获取一场比赛中各个周期时，各存活汇总企业的厂房变动情况
     * @param gameId
     * @return
     */
    Map<Integer, List<FactoryHistoryVo>> findFactoryHistoryVoOfGame(Long gameId);

    /**
     * 获取某一比赛中，处于某一周期时，各个存活中企业的厂房变动情况
     * @param gameId
     * @param period
     * @return
     */
    List<FactoryHistoryVo> findFactoryHistoryVoOfGameAndPeriod(Long gameId, Integer period);

    /**
     * 获取某一比赛中的总期数
     * @param gameId
     * @return
     */
    Integer getTotalPeriod(Long gameId);
}
