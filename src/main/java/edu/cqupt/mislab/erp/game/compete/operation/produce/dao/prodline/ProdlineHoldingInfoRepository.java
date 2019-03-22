package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineHoldingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author yuanyiwen
 * @create 2019-03-10 23:56
 * @description
 */
public interface ProdlineHoldingInfoRepository extends JpaSpecificationExecutor, BasicRepository<ProdlineHoldingInfo, Long> {
}
