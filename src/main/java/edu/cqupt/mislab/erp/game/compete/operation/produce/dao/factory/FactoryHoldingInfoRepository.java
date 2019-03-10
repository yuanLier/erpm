package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory;

import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author yuanyiwen
 * @create 2019-03-10 23:54
 * @description
 */
public interface FactoryHoldingInfoRepository extends JpaSpecificationExecutor, JpaRepository<FactoryHoldingInfo, Long> {

}
