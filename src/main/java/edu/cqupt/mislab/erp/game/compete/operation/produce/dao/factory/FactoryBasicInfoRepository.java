package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory;

import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author yuanyiwen
 * @create 2019-03-09 16:35
 * @description
 */
public interface FactoryBasicInfoRepository extends JpaSpecificationExecutor, JpaRepository<FactoryBasicInfo, Long> {
}
