package edu.cqupt.mislab.erp.game.compete.operation.order.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.EnterpriseAdInfo;

import java.util.List;

/**
 * @author chuyunfei
 * @description
 **/

public interface EnterpriseAdInfoRepository extends BasicRepository<EnterpriseAdInfo, Long> {

    /**
     * 获取需要选单的广告
     * @param gameId
     * @param year
     * @param productId
     * @param marketId
     * @return
     */
    List<EnterpriseAdInfo> findByEnterpriseBasicInfo_GameBasicInfo_IdAndYearAndProductBasicInfo_IdAndMarketBasicInfo_IdAndFinishedIsFalse(long gameId,int year,long productId,long marketId);
}
