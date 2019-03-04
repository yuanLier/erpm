package edu.cqupt.mislab.erp.game.compete.operation.product.dao;

import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductBasicInfoRepository extends JpaRepository<ProductBasicInfo,Long> {

    @Query(value = "select * from product_basic_info where id in (select max(id) from product_basic_info group by product_name)",nativeQuery = true)
    List<ProductBasicInfo> findNewestProductBasicInfos();//选取所有材料的最新版本
}
