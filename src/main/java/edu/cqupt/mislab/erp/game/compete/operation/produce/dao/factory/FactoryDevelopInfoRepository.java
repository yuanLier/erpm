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
     * @author yuanyiwen
     * @description 获取一个企业的全部修建中厂房
     * @date 11:43 2019/4/7
     **/
    List<FactoryDevelopInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);
}