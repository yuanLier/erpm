package edu.cqupt.mislab.erp.game.compete.operation.stock.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.MaterialStockHistoryInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-25 20:57
 * @description
 */
public interface MaterialStockHistoryRepository extends BasicRepository<MaterialStockHistoryInfo, Long>, JpaSpecificationExecutor {

    /**
     * 获取某一企业在某一周期的材料库存情况
     * @param enterpriseId
     * @param period
     * @return
     */
    List<MaterialStockHistoryInfo> findByEnterpriseBasicInfo_IdAndPeriod(Long enterpriseId, Integer period);
}
