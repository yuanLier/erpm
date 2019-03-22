package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-22 15:42
 * @description
 */
public interface FactoryHoldingInfoRepository extends BasicRepository<FactoryHoldingInfo, Long>, JpaSpecificationExecutor {


    /**
     * @author yuanyiwen
     * @description 获取某个企业所拥有的全部厂房信息
     * @date 21:17 2019/3/22
     **/
    List<FactoryHoldingInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);
}
