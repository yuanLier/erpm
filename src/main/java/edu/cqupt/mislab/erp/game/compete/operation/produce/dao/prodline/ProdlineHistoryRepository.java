package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineHistoryInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-06-05 16:11
 * @description
 */
public interface ProdlineHistoryRepository extends BasicRepository<ProdlineHistoryInfo, Long>, JpaSpecificationExecutor {

    /**
     * 获取某一企业某一周期的全部生产线历史数据记录
     * @param enterpriseId
     * @param period
     * @return
     */
    List<ProdlineHistoryInfo> findByEnterpriseBasicInfo_IdAndPeriod(Long enterpriseId, Integer period);


    /**
     * 获取某一企业某一周期的由某种操作产生的全部生产线历史数据记录
     * @param enterpriseId
     * @param period
     * @param operation
     * @return
     */
    List<ProdlineHistoryInfo> findByEnterpriseBasicInfo_IdAndPeriodAndOperation(Long enterpriseId, Integer period, String operation);


    /**
     * 计算某一企业截止到某一周期时的，可投入使用的总生产线数
     * @param enterpriseId
     * @param period
     * @return
     */
    @Query(value = "select sum(p.mount) from prodline_history_info p where p.enterprise_basic_info_id = ?1 and p.period <= ?2", nativeQuery = true)
    Long sumAmount(Long enterpriseId, Integer period);
}
