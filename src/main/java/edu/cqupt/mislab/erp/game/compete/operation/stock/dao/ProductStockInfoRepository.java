package edu.cqupt.mislab.erp.game.compete.operation.stock.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.ProductStockInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-04-01 22:01
 * @description
 */
public interface ProductStockInfoRepository extends BasicRepository<ProductStockInfo, Long>, JpaSpecificationExecutor {

    /**
     * 获取某一企业的全部产品库存信息
     * @param enterpriseId
     * @return
     */
    List<ProductStockInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);


    /**
     * 获取某一企业的某一产品的库存信息
     * @param enterpriseId
     * @param productId
     * @return
     */
    ProductStockInfo findByEnterpriseBasicInfo_IdAndProductBasicInfo_Id(Long enterpriseId, Long productId);

}
