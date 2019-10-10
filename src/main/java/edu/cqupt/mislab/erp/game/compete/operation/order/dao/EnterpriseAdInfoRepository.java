package edu.cqupt.mislab.erp.game.compete.operation.order.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.EnterpriseAdInfo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author chuyunfei
 * @description
 **/

public interface EnterpriseAdInfoRepository extends BasicRepository<EnterpriseAdInfo, Long> {


    /**
     * 获取某一企业投放的全部广告
     * @param enterpriseId
     * @return
     */
    List<EnterpriseAdInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);


    /**
     * 获取某场比赛中处于某一年的全部广告
     * @param gameId
     * @param year
     * @return
     */
    List<EnterpriseAdInfo> findByEnterpriseBasicInfo_GameBasicInfo_IdAndYear(Long gameId, Integer year);


    /**
     * 获取某场比赛中，某年投了广告的企业总数
     * @param year
     * @param gameId
     * @return
     */
    @Query(value =
            "select count(distinct ad.enterprise_basic_info_id) " +
            "from erpm.enterprise_ad_info ad " +
            "where ad.year = ?1 " +
            "and ad.enterprise_basic_info_id in " +
            "(select e.id from erpm.enterprise_basic_info e where e.game_basic_info_id = ?2)",
            nativeQuery = true)
    Long distinctByEnterpriseOfOneYear(Integer year, Long gameId);
}
