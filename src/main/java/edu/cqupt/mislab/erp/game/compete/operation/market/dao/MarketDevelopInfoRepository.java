package edu.cqupt.mislab.erp.game.compete.operation.market.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketStatusEnum;

import java.util.List;

/**
 * @author yuanyiwen
 * @description 
 **/

public interface MarketDevelopInfoRepository extends BasicRepository<MarketDevelopInfo, Long> {

    /**
     * 获取某个企业的全部市场开拓信息
     * @param enterpriseId
     * @return
     */
    List<MarketDevelopInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);


    /**
     * 获取某场比赛中的全部市场开拓信息
     * @param gameId
     * @return
     */
    List<MarketDevelopInfo> findByEnterpriseBasicInfo_GameBasicInfo_Id(Long gameId);


    /**
     * 获取某个企业处于某种开拓状态的市场
     * @param enterpriseId
     * @param marketStatus
     * @return
     */
    List<MarketDevelopInfo> findByEnterpriseBasicInfo_IdAndMarketStatus(Long enterpriseId, MarketStatusEnum marketStatus);

}
