package edu.cqupt.mislab.erp.game.compete.operation.stock.dao;

import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.EnterpriseProductStockInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseProductStockInfoRepository extends JpaRepository<EnterpriseProductStockInfo, Long> {
}
