package edu.cqupt.mislab.erp.game.compete.operation.stock.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.MaterialStockInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-04-03 18:53
 * @description
 */
public interface MaterialStockInfoRepository extends BasicRepository<MaterialStockInfo, Long>, JpaSpecificationExecutor {

    /**
     * 获取某一企业的全部原材料库存信息
     * @param enterpriseId
     * @return
     */
    List<MaterialStockInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);

    /**
     * 根据原料id和企业id唯一确定一个原料存储信息
     * @param enterpriseId
     * @param materialId
     * @return
     */
    MaterialStockInfo findByEnterpriseBasicInfo_IdAndMaterialBasicInfo_Id(Long enterpriseId, Long materialId);
}
