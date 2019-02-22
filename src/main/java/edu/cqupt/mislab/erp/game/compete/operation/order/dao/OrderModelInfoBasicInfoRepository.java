package edu.cqupt.mislab.erp.game.compete.operation.order.dao;

import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.OrderModelInfoBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderModelInfoBasicInfoRepository extends JpaSpecificationExecutor, JpaRepository<OrderModelInfoBasicInfo, Long> {
}
