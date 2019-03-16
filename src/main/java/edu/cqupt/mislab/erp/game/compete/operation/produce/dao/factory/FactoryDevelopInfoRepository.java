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
     * @date 0:23 2019/3/11
     **/
    List<FactoryDevelopInfo> findByFactoryHoldingInfo_EnterpriseBasicInfo_Id(Long enterpriseId);

    /**
     * @author yuanyiwen
     * @description 获取某个企业处于某种状态的全部厂房
     * @date 0:23 2019/3/11
     **/
    List<FactoryDevelopInfo> findByFactoryHoldingInfo_EnterpriseBasicInfo_IdAndFactoryDevelopStatus
                    (Long enterpriseId, FactoryDevelopStatus factoryDevelopStatus);
}
