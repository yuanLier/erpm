package edu.cqupt.mislab.erp.game.compete.operation.product.dao;

import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductDevelopInfoRepository extends JpaRepository<ProductDevelopInfo, Long>, JpaSpecificationExecutor {

    /**
     * 获取某个企业的全部产品产品研发信息
     * @param enterpriseId
     * @return
     */
    List<ProductDevelopInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);


    /**
     * 获取某个企业处于某种研发状态的产品
     * @param enterpriseId
     * @param productStatus
     * @return
     */
    List<ProductDevelopInfo> findByEnterpriseBasicInfo_IdAndProductStatus(Long enterpriseId, ProductStatusEnum productStatus);


    /**
     * 通过id获取产品研发信息
     * @param productDevelopId
     * @return
     */
    ProductDevelopInfo findOne(Long productDevelopId);
}
