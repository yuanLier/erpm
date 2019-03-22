package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryBasicInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author yuanyiwen
 * @create 2019-03-22 15:25
 * @description
 */
public interface FactoryBasicInfoRepository extends BasicRepository<FactoryBasicInfo, Long>, JpaSpecificationExecutor {
}
