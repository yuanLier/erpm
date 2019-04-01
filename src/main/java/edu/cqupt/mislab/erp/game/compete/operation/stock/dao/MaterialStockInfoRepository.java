package edu.cqupt.mislab.erp.game.compete.operation.stock.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.MaterialStockInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MaterialStockInfoRepository extends BasicRepository<MaterialStockInfo, Long>, JpaSpecificationExecutor {

    /**
     * @author yuanyiwen
     * @description 获取某一企业的全部原材料库存信息
     * @date 21:52 2019/4/1
     **/
    List<MaterialStockInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);
}
