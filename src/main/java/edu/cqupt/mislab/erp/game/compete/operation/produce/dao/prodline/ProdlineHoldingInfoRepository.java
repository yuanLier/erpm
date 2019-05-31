package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineHoldingInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineHoldingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-10 23:56
 * @description
 */
public interface ProdlineHoldingInfoRepository extends JpaSpecificationExecutor, BasicRepository<ProdlineHoldingInfo, Long> {

    /**
     * 根据厂房id获取处于某种状态的生产线
     * @param factoryId
     * @param prodlineHoldingStatus
     * @return
     */
    List<ProdlineHoldingInfo> findByFactoryHoldingInfo_IdAndProdlineHoldingStatus(Long factoryId, ProdlineHoldingStatus prodlineHoldingStatus);
}
