package edu.cqupt.mislab.erp.game.compete.operation.product.dao;

import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDevelopInfoRepository extends JpaRepository<ProductDevelopInfo,Long> {

    //选取某个企业的全部产品信息
    List<ProductDevelopInfo> findByEnterpriseBasicInfo_Id(long enterpriseId);

}
