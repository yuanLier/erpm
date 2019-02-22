package edu.cqupt.mislab.erp.game.compete.operation.order.dao;

import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.AllOrderInfos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AllOrderInfosRepository extends JpaSpecificationExecutor, JpaRepository<AllOrderInfos, Long> {
}
