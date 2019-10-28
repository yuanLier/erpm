package edu.cqupt.mislab.erp.game.compete.operation.material.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.MaterialBasicInfo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author chuyunfei
 * @description
 **/

public interface MaterialBasicInfoRepository extends BasicRepository<MaterialBasicInfo, Long> {

    /**
     * 选取所有材料的最新版本
     * @return
     */
    @Query("from MaterialBasicInfo m where m.enable = true group by m.materialName")
    List<MaterialBasicInfo> findNewestMaterialBasicInfos();


    /**
     * 获取处于某种状态（可用or不可用）的材料基本信息
     * @param enable
     * @return
     */
    List<MaterialBasicInfo> findByEnable(boolean enable);
}
