package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineDevelopInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-09 19:39
 * @description
 */
public interface ProdlineDevelopInfoRepository extends JpaSpecificationExecutor, BasicRepository<ProdlineDevelopInfo, Long> {

    /**
     * @author yuanyiwen
     * @description 获取某个厂房中的全部建造中生产线
     * @date 0:22 2019/3/11
     **/
    List<ProdlineDevelopInfo> findByProdlineHoldingInfo_FactoryHoldingInfo_Id(Long factoryId);
}
