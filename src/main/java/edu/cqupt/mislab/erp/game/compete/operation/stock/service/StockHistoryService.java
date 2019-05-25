package edu.cqupt.mislab.erp.game.compete.operation.stock.service;

import edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo.MaterialStockHistoryVo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo.ProductStockHistoryVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-25 21:08
 * @description
 */
public interface StockHistoryService {


    /**
     * 获取某一比赛中，处于某一周期时，各个存活中企业的材料库存情况
     * @param gameId
     * @param period
     * @return
     */
    List<MaterialStockHistoryVo> findMaterialStockHistoryVoOfGameAndPeriod(Long gameId, Integer period);


    /**
     * 获取某一比赛中，处于某一周期时，各个存活中企业的产品库存情况
     * @param gameId
     * @param period
     * @return
     */
    List<ProductStockHistoryVo> findProductStockHistoryVoOfGameAndPeriod(Long gameId, Integer period);


    /**
     * 获取某一比赛中的总期数
     * @param gameId
     * @return
     */
    Integer getTotalPeriod(Long gameId);
}
