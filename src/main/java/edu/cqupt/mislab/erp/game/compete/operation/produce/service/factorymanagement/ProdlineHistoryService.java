package edu.cqupt.mislab.erp.game.compete.operation.produce.service.factorymanagement;

import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineHistoryVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-06-05 17:40
 * @description
 */
public interface ProdlineHistoryService {

    List<ProdlineHistoryVo> findProdlineHistoryVoOfGameAndPeriod(Long gameId, Integer period);
}
