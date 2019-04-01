package edu.cqupt.mislab.erp.game.compete.operation.stock.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.MaterialOrderInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author yuanyiwen
 * @create 2019-04-01 18:53
 * @description
 */
public interface MaterialOrderInfoRepository extends BasicRepository<MaterialOrderInfo, Long>, JpaSpecificationExecutor {
}
