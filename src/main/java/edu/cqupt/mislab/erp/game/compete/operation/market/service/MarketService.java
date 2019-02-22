package edu.cqupt.mislab.erp.game.compete.operation.market.service;

import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketDisplayVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MarketService {

    /**
     * 获取某个企业全部市场开拓信息
     * @param enterpriseId
     * @return
     */
    List<MarketDisplayVo> findByEnterpriseId(Long enterpriseId);


    /**
     * 获取某个企业处于某种开拓状态的市场开拓信息
     * @param enterpriseId
     * @param marketStatus
     * @return
     */
    List<MarketDisplayVo> findByEnterpriseIdAndMarketStatus(Long enterpriseId, MarketStatusEnum marketStatus);


    /**
     * 修改某个市场的开拓状态
     * @param marketDevelopId
     * @param marketStatus
     * @return
     */
    MarketDisplayVo updateMarketStatus(Long marketDevelopId, MarketStatusEnum marketStatus);


    /**
     * （管理员）修改市场基本信息
     * @param marketBasicInfo
     * @return
     */
    MarketBasicInfo updateMarketBasicInfo(MarketBasicInfo marketBasicInfo);

}
