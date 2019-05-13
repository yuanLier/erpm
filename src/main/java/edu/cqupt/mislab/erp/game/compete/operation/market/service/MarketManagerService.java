package edu.cqupt.mislab.erp.game.compete.operation.market.service;

import edu.cqupt.mislab.erp.game.compete.operation.market.model.dto.MarketBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketBasicVo;

/**
 * @author yuanyiwen
 * @create 2019-05-13 19:52
 * @description
 */
public interface MarketManagerService {

    /**
     * 修改市场基本信息
     * @param marketBasicDto
     * @return
     */
    MarketBasicVo updateMarketBasicInfo(MarketBasicDto marketBasicDto);

}
