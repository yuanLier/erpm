package edu.cqupt.mislab.erp.game.compete.operation.stock.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.GameTransportBasicInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-04-01 18:54
 * @description
 */
public interface GameTransportBasicInfoRepository extends BasicRepository<GameTransportBasicInfo, Long>, JpaSpecificationExecutor {

    /**
     * 选取当前版本下的全部运输方式
     * @return
     */
    List<GameTransportBasicInfo> findByGameBasicInfo_Id(Long gameId);

}
