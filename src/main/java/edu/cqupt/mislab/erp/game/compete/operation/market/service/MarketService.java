package edu.cqupt.mislab.erp.game.compete.operation.market.service;

import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketBasicTypeVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @description
 **/

public interface MarketService {

    /**
     * 获取一场比赛中使用的全部市场类型
     * @param enterpriseId 传入企业id就行，一场比赛中各个企业的市场类型是一样的
     * @return
     */
    List<MarketBasicTypeVo> getAllMarketTypes(Long enterpriseId);


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
     * 开始开拓
     * @param marketDevelopId
     * @return
     */
    MarketDisplayVo startMarketDevelop(Long marketDevelopId);


    /**
     * 修改某个市场的开拓状态
     * @param marketDevelopId
     * @param marketStatus
     * @return
     */
    MarketDisplayVo updateMarketStatus(Long marketDevelopId, MarketStatusEnum marketStatus);

}
