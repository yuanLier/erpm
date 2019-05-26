package edu.cqupt.mislab.erp.game.compete.operation.stock.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.MaterialOrderInfo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.TransportStatusEnum;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-04-01 18:53
 * @description
 */
public interface MaterialOrderInfoRepository extends BasicRepository<MaterialOrderInfo, Long>, JpaSpecificationExecutor {

    /**
     * 获取一个企业的全部原材料订单采购情况
     * @param enterpriseId
     * @return
     */
    List<MaterialOrderInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);


    /**
     * 获取某一企业处于某种状态下的采购订单
     * @param enterpriseId
     * @param transportStatusEnum
     * @return
     */
    List<MaterialOrderInfo> findByEnterpriseBasicInfo_IdAndTransportStatus(Long enterpriseId, TransportStatusEnum transportStatusEnum);
}
