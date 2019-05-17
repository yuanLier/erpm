package edu.cqupt.mislab.erp.game.compete.operation.product.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductHistoryInfo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-16 21:17
 * @description
 */
public interface ProductHistoryRepository extends BasicRepository<ProductHistoryInfo, Long> {

    /**
     * 获取某一企业在某一周期中的产品基本信息历史数据
     * @param enterpriseId
     * @return
     */
    List<ProductHistoryInfo> findByEnterpriseBasicInfo_IdAndPeriod(Long enterpriseId, Integer period);

}
