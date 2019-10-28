package edu.cqupt.mislab.erp.game.compete.operation.market.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketHistoryInfo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-15 20:13
 * @description
 */
public interface MarketHistoryRepository extends BasicRepository<MarketHistoryInfo, Long> {

    /**
     * 获取某一企业在某一周期中的市场基本信息历史数据
     * @param enterpriseId
     * @return
     */
    List<MarketHistoryInfo> findByEnterpriseBasicInfo_IdAndPeriod(Long enterpriseId, Integer period);
}