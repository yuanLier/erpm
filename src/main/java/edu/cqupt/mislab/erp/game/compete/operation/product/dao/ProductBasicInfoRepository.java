package edu.cqupt.mislab.erp.game.compete.operation.product.dao;

import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductBasicInfoRepository extends JpaRepository<ProductBasicInfo, Long>, JpaSpecificationExecutor {
}
