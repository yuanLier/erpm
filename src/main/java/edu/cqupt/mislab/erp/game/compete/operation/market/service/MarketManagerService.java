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
     * 添加一个市场基本信息
     * @param marketBasicDto
     * @return
     */
    MarketBasicVo addMarketBasicInfo(MarketBasicDto marketBasicDto);

    /**
     * 修改市场基本信息
     * @param marketBasicId
     * @param marketBasicDto
     * @return
     */
    MarketBasicVo updateMarketBasicInfo(Long marketBasicId, MarketBasicDto marketBasicDto);

    /**
     * 关闭一个市场
     * @param marketBasicId
     * @return
     */
    MarketBasicVo closeMarketBasicInfo(Long marketBasicId);

}
