package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineDevelopStatus;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-09 19:39
 * @description
 */
public interface ProdlineDevelopInfoRepository extends JpaSpecificationExecutor, BasicRepository<ProdlineDevelopInfo, Long> {

    /**
     * 获取某个厂房中的全部安装中生产线
     * @param factoryId
     * @return
     */
    List<ProdlineDevelopInfo> findByProdlineHoldingInfo_FactoryHoldingInfo_Id(Long factoryId);


    /**
     * 获取某个厂房中的全部未安装完成的生产线
     * @param factoryId
     * @param prodlineDevelopStatus
     * @return
     */
    List<ProdlineDevelopInfo> findByProdlineHoldingInfo_FactoryHoldingInfo_IdAndProdlineDevelopStatusIsNot(Long factoryId, ProdlineDevelopStatus prodlineDevelopStatus);


    /**
     * 获取某个企业中全部处于某种安装状态（安装中/安装暂停/安装完成）的生产线
     * @param enterpriseId
     * @param status
     * @return
     */
    List<ProdlineDevelopInfo> findByProdlineHoldingInfo_EnterpriseBasicInfo_IdAndProdlineDevelopStatus(Long enterpriseId, ProdlineDevelopStatus status);


    /**
     * 根据holdingIddevelopInfo获取prodlineDevelopInfo
     * @param prodlineHoldingInfoId
     * @return
     */
    ProdlineDevelopInfo findByProdlineHoldingInfo_Id(Long prodlineHoldingInfoId);

}
