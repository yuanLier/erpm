package edu.cqupt.mislab.erp.game.compete.operation.order.dao;

import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.EnterpriseMaterialOrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EnterpriseMaterialOrderInfoRepository extends JpaSpecificationExecutor, JpaRepository<EnterpriseMaterialOrderInfo, Long> {
}
