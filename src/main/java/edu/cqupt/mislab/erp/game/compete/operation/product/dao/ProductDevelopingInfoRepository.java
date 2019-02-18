package edu.cqupt.mislab.erp.game.compete.operation.product.dao;

import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketDevelopingInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductDevelopingInfoRepository extends JpaSpecificationExecutor, JpaRepository<ProductDevelopingInfo, Long> {
}
