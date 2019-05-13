package edu.cqupt.mislab.erp.game.compete.operation.market.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketDisplayVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @description 
 **/

public interface MarketDevelopInfoRepository extends BasicRepository<MarketDevelopInfo, Long> {

    /**
     * 获取某个企业的全部市场开拓信息
     * @param enterpriseId
     * @return
     */
    List<MarketDevelopInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);


    /**
     * 获取某个企业处于某种开拓状态的市场
     * @param enterpriseId
     * @param marketStatus
     * @return
     */
    List<MarketDevelopInfo> findByEnterpriseBasicInfo_IdAndMarketStatus(Long enterpriseId, MarketStatusEnum marketStatus);

}
