package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryDevelopInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-22 15:39
 * @description
 */
public interface FactoryDevelopInfoRepository extends BasicRepository<FactoryDevelopInfo, Long>, JpaSpecificationExecutor {

    /**
     * 获取一个企业的全部修建中厂房
     * @param enterpriseId
     * @return
     */
    List<FactoryDevelopInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);
}