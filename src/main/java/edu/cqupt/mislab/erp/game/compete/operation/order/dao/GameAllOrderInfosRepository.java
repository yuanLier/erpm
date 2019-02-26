package edu.cqupt.mislab.erp.game.compete.operation.order.dao;

import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.GameAllOrderInfos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GameAllOrderInfosRepository extends JpaSpecificationExecutor, JpaRepository<GameAllOrderInfos, Long> {
}
