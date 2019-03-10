package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline;

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
public interface ProdlineProduceInfoRepository extends JpaSpecificationExecutor, JpaRepository<ProdlineProduceInfo, Long> {
    /**
     * @author yuanyiwen
     * @description 获取某个厂房中处于某种生产状态的全部生产线
     * @date 0:21 2019/3/11
     **/
    List<ProdlineProduceInfo> findByProdlineHoldingInfo_FactoryDevelopInfo_IdAndProdlineProduceStatus(Long factoryId, ProdlineProduceStatus prodlineProduceStatus);
}
