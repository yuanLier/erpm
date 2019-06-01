package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingStatus;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-22 15:42
 * @description
 */
public interface FactoryHoldingInfoRepository extends BasicRepository<FactoryHoldingInfo, Long>, JpaSpecificationExecutor {


    /**
     * 获取某个企业所拥有的全部厂房信息
     * @param enterpriseId
     * @return
     */
    List<FactoryHoldingInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);


    /**
     * 获取一些特定条件下的厂房信息
     * @param enterpriseId 哪个企业
     * @param status 什么拥有状态（自建的 / 租来的）
     * @param enable 是否可用（true：可用 false：已出售 / 已停租 ）
     * @return
     */
    List<FactoryHoldingInfo> findByEnterpriseBasicInfo_IdAndFactoryHoldingStatusAndEnable(Long enterpriseId, FactoryHoldingStatus status, boolean enable);

}
