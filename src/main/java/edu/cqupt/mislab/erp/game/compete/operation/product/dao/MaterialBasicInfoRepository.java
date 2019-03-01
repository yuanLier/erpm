package edu.cqupt.mislab.erp.game.compete.operation.product.dao;

import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.MaterialBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MaterialBasicInfoRepository extends JpaRepository<MaterialBasicInfo,Long> {

    @Query(value = "select * from material_basic_info where id in (select max(id) from material_basic_info group by material_name)",nativeQuery = true)
    List<MaterialBasicInfo> findNewestMaterialBasicInfos();//选取所有材料的最新版本
}
