package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author yuanyiwen
 * @create 2019-03-22 15:42
 * @description
 */
public interface FactoryHoldingInfoRepository extends BasicRepository<FactoryHoldingInfo, Long>, JpaSpecificationExecutor {
}
