package edu.cqupt.mislab.erp.game.compete.operation.stock.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.ProductStockInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductStockInfoRepository extends BasicRepository<ProductStockInfo, Long>, JpaSpecificationExecutor {

    /**
     * @author yuanyiwen
     * @description 获取某一企业的全部产品库存信息
     * @date 22:01 2019/4/1
     **/
    List<ProductStockInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);
}
