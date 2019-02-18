package edu.cqupt.mislab.erp.game.compete.operation.product.dao;

import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketToDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductToDevelopInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductToDevelopInfoRepository extends JpaSpecificationExecutor, JpaRepository<ProductToDevelopInfo, Long> {
}
