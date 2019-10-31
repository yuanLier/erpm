package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineHoldingStatus;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineProduceInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineProduceStatus;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-10 23:57
 * @description
 */
public interface ProdlineProduceInfoRepository extends JpaSpecificationExecutor, BasicRepository<ProdlineProduceInfo, Long> {


    /**
     * 获取某个厂房中的全部生产线
     * @param factoryId
     * @return
     */
    List<ProdlineProduceInfo> findByProdlineHoldingInfo_FactoryHoldingInfo_Id(Long factoryId);


    /**
     * 获取某个厂房中未处于某种状态的全部生产线
     * @param factoryId
     * @param prodlineHoldingStatus
     * @return
     */
    List<ProdlineProduceInfo> findByProdlineHoldingInfo_FactoryHoldingInfo_IdAndProdlineHoldingInfo_ProdlineHoldingStatusIsNot(Long factoryId, ProdlineHoldingStatus prodlineHoldingStatus);


    /**
     * 获取某个厂房中处于某种生产状态的全部生产线
     * @param factoryId
     * @param prodlineProduceStatus
     * @return
     */
    List<ProdlineProduceInfo> findByProdlineHoldingInfo_FactoryHoldingInfo_IdAndProdlineProduceStatus
            (Long factoryId, ProdlineProduceStatus prodlineProduceStatus);

    /**
     * 获取某个厂房中可生产某种产品的处于某种状态的全部生产线
     * @param factoryId
     * @param productionId
     * @param prodlineProduceStatus
     * @return
     */
    List<ProdlineProduceInfo> findByProdlineHoldingInfo_FactoryHoldingInfo_IdAndProductDevelopInfo_IdAndProdlineProduceStatus
            (Long factoryId, Long productionId, ProdlineProduceStatus prodlineProduceStatus);


    /**
     * 获取某个企业中可生产某种产品的处于某种状态的全部生产线
     * @param enterpriseId
     * @param productId
     * @param prodlineProduceStatus
     * @return
     */
    List<ProdlineProduceInfo> findByProdlineHoldingInfo_EnterpriseBasicInfo_IdAndProductDevelopInfo_IdAndProdlineProduceStatus
            (Long enterpriseId, Long productId, ProdlineProduceStatus prodlineProduceStatus);

    /**
     * 获取某个企业中可生产某种产品的未处于某种状态的全部生产线
     * @param enterpriseId
     * @param productId
     * @param prodlineProduceStatus
     * @return
     */
    List<ProdlineProduceInfo> findByProdlineHoldingInfo_EnterpriseBasicInfo_IdAndProductDevelopInfo_IdAndProdlineProduceStatusIsNot
            (Long enterpriseId, Long productId, ProdlineProduceStatus prodlineProduceStatus);


    /**
     * 获取某个企业中处于某种生产状态的全部生产线
     * @param enterpriseId
     * @param status
     * @return
     */
    List<ProdlineProduceInfo> findByProdlineHoldingInfo_EnterpriseBasicInfo_IdAndAndProdlineProduceStatus
            (Long enterpriseId, ProdlineProduceStatus status);
}
