package edu.cqupt.mislab.erp.game.compete.operation.stock.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.TransportBasicInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-04-01 18:54
 * @description
 */
public interface TransportBasicInfoRepository extends BasicRepository<TransportBasicInfo, Long>, JpaSpecificationExecutor {

    /**
     * 选取当前版本下的全部运输方式
     * @return
     */
    @Query("from TransportBasicInfo t where t.enable = true group by t.transportName")
    List<TransportBasicInfo> findNewestTransportBasicInfos();


    /**
     * 获取处于某一状态（可用or不可用）下的全部运输方式基本信息
     * @param enable
     * @return
     */
    List<TransportBasicInfo> findByEnable(boolean enable);
}
