package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineProduceInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineProduceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-10 23:57
 * @description
 */
public interface ProdlineProduceInfoRepository extends JpaSpecificationExecutor, BasicRepository<ProdlineProduceInfo, Long> {


    /**
     * @author yuanyiwen
     * @description 获取某个厂房中的全部生产线
     * @date 1:02 2019/3/14
     **/
    List<ProdlineProduceInfo> findByProdlineHoldingInfo_FactoryHoldingInfo_Id(Long factoryId);


    /**
     * @author yuanyiwen
     * @description 获取某个厂房中处于某种生产状态的全部生产线
     * @date 0:21 2019/3/11
     **/
    List<ProdlineProduceInfo> findByProdlineHoldingInfo_FactoryHoldingInfo_IdAndProdlineProduceStatus
            (Long factoryId, ProdlineProduceStatus prodlineProduceStatus);

    /**
     * @author yuanyiwen
     * @description 获取某个厂房中可生产某种产品的处于某种状态的全部生产线
     * @date 15:45 2019/3/12
     **/
    List<ProdlineProduceInfo> findByProdlineHoldingInfo_FactoryHoldingInfo_IdAndProductDevelopInfo_IdAndProdlineProduceStatus
            (Long factoryId, Long productionId, ProdlineProduceStatus prodlineProduceStatus);


    /**
     * @author yuanyiwen
     * @description 获取某个企业中可生产某种产品的处于某种状态的全部生产线
     * @date 9:29 2019/3/15
     **/
    List<ProdlineProduceInfo> findByProdlineHoldingInfo_EnterpriseBasicInfo_IdAndProductDevelopInfo_IdAndProdlineProduceStatus
            (Long enterpriseId, Long productId, ProdlineProduceStatus prodlineProduceStatus);

    /**
     * @author yuanyiwen
     * @description 获取某个企业中可生产某种产品的未处于某种状态的全部生产线
     * @date 15:10 2019/3/15
     **/
    List<ProdlineProduceInfo> findByProdlineHoldingInfo_EnterpriseBasicInfo_IdAndProductDevelopInfo_IdAndProdlineProduceStatusIsNot
            (Long enterpriseId, Long productId, ProdlineProduceStatus prodlineProduceStatus);

}
