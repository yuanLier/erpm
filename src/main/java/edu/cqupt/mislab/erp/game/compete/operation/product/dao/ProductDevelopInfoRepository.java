package edu.cqupt.mislab.erp.game.compete.operation.product.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatusEnum;

import java.util.List;

/**
 * @author chuyunfei
 * @description
 **/

public interface ProductDevelopInfoRepository extends BasicRepository<ProductDevelopInfo,Long> {

    /**
     * 选取某个企业的全部产品信息
     * @param enterpriseId
     * @return
     */
    List<ProductDevelopInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);

    /**
     * 选取某个企业处于某种状态的数据
     * @param enterpriseId
     * @param developStatus
     * @return
     */
    List<ProductDevelopInfo> findByEnterpriseBasicInfo_IdAndProductDevelopStatus(Long enterpriseId,ProductDevelopStatusEnum developStatus);
}
