package edu.cqupt.mislab.erp.game.compete.operation.material.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.MaterialBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MaterialBasicInfoRepository extends BasicRepository<MaterialBasicInfo, Long> {

    @Query("from MaterialBasicInfo m where m.enable = true group by m.materialName")
    List<MaterialBasicInfo> findNewestMaterialBasicInfos();//选取所有材料的最新版本
}
