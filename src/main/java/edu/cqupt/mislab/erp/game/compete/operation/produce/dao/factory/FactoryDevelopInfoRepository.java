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
     * 获取一个企业的全部处于某种修建状态（修建中/修建完成）的厂房
     * @param enterpriseId
     * @param developed
     * @return
     */
    List<FactoryDevelopInfo> findByEnterpriseBasicInfo_IdAndDeveloped(Long enterpriseId, boolean developed);

}