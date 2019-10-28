package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineHoldingInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineHoldingStatus;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-10 23:56
 * @description
 */
public interface ProdlineHoldingInfoRepository extends JpaSpecificationExecutor, BasicRepository<ProdlineHoldingInfo, Long> {

    /**
     * 根据厂房id获取厂房中处于某种状态的生产线
     * @param factoryId
     * @param prodlineHoldingStatus
     * @return
     */
    List<ProdlineHoldingInfo> findByFactoryHoldingInfo_IdAndProdlineHoldingStatus(Long factoryId, ProdlineHoldingStatus prodlineHoldingStatus);


    /**
     * 获取某一企业全部处于某种拥有状态（修建中or生产中or已出售or由于厂房停租而不可用）的生产线
     * @param enterpriseId
     * @param prodlineHoldingStatus
     * @return
     */
    List<ProdlineHoldingInfo> findByEnterpriseBasicInfo_IdAndProdlineHoldingStatus(Long enterpriseId, ProdlineHoldingStatus prodlineHoldingStatus);


    /**
     * 获取某一企业全部拥有的生产线
     * @param enterpriseId
     * @return
     */
    List<ProdlineHoldingInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);


    /**
     * 获取某一厂房中的全部生产线
     * @param factoryId
     * @return
     */
    List<ProdlineHoldingInfo> findByFactoryHoldingInfo_Id(Long factoryId);

}
