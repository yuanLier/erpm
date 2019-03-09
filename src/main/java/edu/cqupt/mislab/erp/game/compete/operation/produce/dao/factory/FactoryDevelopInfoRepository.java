package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory;

import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryDevelopStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-09 16:50
 * @description
 */
public interface FactoryDevelopInfoRepository extends JpaSpecificationExecutor, JpaRepository<FactoryDevelopInfo, Long> {
    
    /**
     * @author yuanyiwen
     * @description 获取某企业的全部厂房
     * @date 16:52 2019/3/9
     **/
    List<FactoryDevelopInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);

    /**
     * @author yuanyiwen
     * @description 获取某个企业处于某种状态的全部厂房
     * @date 20:05 2019/3/9
     **/
    List<FactoryDevelopInfo> findByEnterpriseBasicInfo_IdAndFactoryDevelopStatus(Long enterpriseId, FactoryDevelopStatus factoryDevelopStatus);
}
