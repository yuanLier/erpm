package edu.cqupt.mislab.erp.game.compete.operation.product.dao;

import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketDevelopedInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopedInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductDevelopedInfoRepository extends JpaSpecificationExecutor, JpaRepository<ProductDevelopedInfo, Long> {
}
