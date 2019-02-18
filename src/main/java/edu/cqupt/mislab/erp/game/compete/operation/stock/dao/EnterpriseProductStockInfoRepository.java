package edu.cqupt.mislab.erp.game.compete.operation.stock.dao;

import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.EnterpriseMaterialStockInfo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.EnterpriseProductStockInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EnterpriseProductStockInfoRepository extends JpaSpecificationExecutor, JpaRepository<EnterpriseProductStockInfo, Long> {
}
