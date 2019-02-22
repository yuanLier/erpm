package edu.cqupt.mislab.erp.game.compete.operation.product.dao;

import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductMaterialBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductMaterialBasicInfoRepository extends JpaRepository<ProductMaterialBasicInfo, Long>, JpaSpecificationExecutor {

    /**
     * 根据产品id查询该产品对应的所有的原材料构成情况
     */
    List<ProductMaterialBasicInfo> findByProductBasicInfo_Id(Long productBasicInfoId);
}
