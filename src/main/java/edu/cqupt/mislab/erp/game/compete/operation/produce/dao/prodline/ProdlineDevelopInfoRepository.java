package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineDevelopInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-09 19:39
 * @description
 */
public interface ProdlineDevelopInfoRepository extends JpaSpecificationExecutor, BasicRepository<ProdlineDevelopInfo, Long> {

    /**
     * 获取某个厂房中的全部建造中生产线
     * @param factoryId
     * @return
     */
    List<ProdlineDevelopInfo> findByProdlineHoldingInfo_FactoryHoldingInfo_Id(Long factoryId);
}
