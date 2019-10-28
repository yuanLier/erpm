package edu.cqupt.mislab.erp.game.compete.operation.iso.dao;


import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoHistoryInfo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-11 22:34
 * @description
 */
public interface IsoHistoryRepository extends BasicRepository<IsoHistoryInfo, Long> {


    /**
     * 获取某一企业在某一周期中的iso历史数据
     * @param enterpriseId
     * @return
     */
    List<IsoHistoryInfo> findByEnterpriseBasicInfo_IdAndPeriod(Long enterpriseId, Integer period);
}
