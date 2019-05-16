package edu.cqupt.mislab.erp.game.compete.operation.product.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author chuyunfei
 * @description
 **/

public interface ProductBasicInfoRepository extends BasicRepository<ProductBasicInfo,Long> {


    /**
     * 选取所有产品的最新版本
     * @return
     */
    @Query("from ProductBasicInfo p where p.enable = true group by p.productName")
    List<ProductBasicInfo> findNewestProductBasicInfos();
}
