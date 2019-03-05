package edu.cqupt.mislab.erp.game.compete.operation.product.dao;

import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductMaterialBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductMaterialBasicInfoRepository extends JpaRepository<ProductMaterialBasicInfo,Long> {

    //选取某个产品的全部启用的材料组成信息
    List<ProductMaterialBasicInfo> findByEnableIsTrueAndProductBasicInfo_Id(long productId);
}
