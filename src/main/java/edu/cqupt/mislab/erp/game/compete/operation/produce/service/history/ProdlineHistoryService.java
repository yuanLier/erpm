package edu.cqupt.mislab.erp.game.compete.operation.produce.service.history;

import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineHistoryVo;

import java.util.List;
import java.util.Map;

/**
 * @author yuanyiwen
 * @create 2019-06-05 17:40
 * @description
 */
public interface ProdlineHistoryService {

    /**
     * 获取一场比赛中各个周期时，各存活汇总企业的生产线变动情况
     * @param gameId
     * @return
     */
    Map<Integer, List<ProdlineHistoryVo>> findProdlineHistoryVoOfGame(Long gameId);


    /**
     * 获取某一比赛中，处于某一周期时，各个存活中企业的生产线变动情况
     * @param gameId
     * @param period
     * @return
     */
    List<ProdlineHistoryVo> findProdlineHistoryVoOfGameAndPeriod(Long gameId, Integer period);


    /**
     * 获取某一比赛中的总期数
     * @param gameId
     * @return
     */
    Integer getTotalPeriod(Long gameId);
}
