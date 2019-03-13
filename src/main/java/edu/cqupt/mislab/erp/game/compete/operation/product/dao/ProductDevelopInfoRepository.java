package edu.cqupt.mislab.erp.game.compete.operation.product.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Basic;
import java.util.List;

public interface ProductDevelopInfoRepository extends BasicRepository<ProductDevelopInfo,Long> {

    //选取某个企业的全部产品信息
    List<ProductDevelopInfo> findByEnterpriseBasicInfo_Id(long enterpriseId);

    //选取某个企业处于某种状态的数据
    List<ProductDevelopInfo> findByEnterpriseBasicInfo_IdAndProductDevelopStatus(long enterpriseId,ProductDevelopStatus developStatus);
}
