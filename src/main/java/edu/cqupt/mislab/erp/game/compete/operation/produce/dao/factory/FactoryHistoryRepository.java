package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHistoryInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-06-02 20:17
 * @description
 */
public interface FactoryHistoryRepository extends BasicRepository<FactoryHistoryInfo, Long>, JpaSpecificationExecutor {

    /**
     * 获取某一企业某一周期的全部厂房历史数据记录
     * @param enterpriseId
     * @param period
     * @return
     */
    List<FactoryHistoryInfo> findByEnterpriseBasicInfo_IdAndPeriod(Long enterpriseId, Integer period);


    /**
     * 获取某一企业某一周期的由某种操作产生的全部厂房历史数据记录
     * @param enterpriseId
     * @param period
     * @param operation
     * @return
     */
    List<FactoryHistoryInfo> findByEnterpriseBasicInfo_IdAndPeriodAndOperation(Long enterpriseId, Integer period, String operation);


    /**
     * 计算某一企业截止到某一周期时的，可投入使用的总厂房数
     * @param enterpriseId
     * @param period
     * @return
     */
    @Query(value = "select sum(f.mount) from factory_history_info f where f.enterprise_basic_info_id = ?1 and f.period <= ?2", nativeQuery = true)
    Long sumAmount(Long enterpriseId, Integer period);
}
