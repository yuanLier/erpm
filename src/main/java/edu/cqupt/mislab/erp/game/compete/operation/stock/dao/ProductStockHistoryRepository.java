package edu.cqupt.mislab.erp.game.compete.operation.stock.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.ProductStockHistoryInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-25 21:56
 * @description
 */
public interface ProductStockHistoryRepository extends BasicRepository<ProductStockHistoryInfo, Long>, JpaSpecificationExecutor {

    /**
     * 获取某一企业在某一周期的材料库存情况
     * @param enterpriseId
     * @param period
     * @return
     */
    List<ProductStockHistoryInfo> findByEnterpriseBasicInfo_IdAndPeriod(Long enterpriseId, Integer period);
}
