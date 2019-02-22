package edu.cqupt.mislab.erp.game.compete.operation.market.util;

import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketDisplayVo;

public class VoUtil {
    public static MarketDisplayVo castEntityToVo(MarketDevelopInfo marketDevelopInfo) {

        MarketDisplayVo marketDisplayVo = new MarketDisplayVo();

        // id同marketDevelopId
        marketDisplayVo.setId(marketDevelopInfo.getId());

        // 每期维护费用
        marketDisplayVo.setMarketMaintainCost(marketDevelopInfo.getMarketBasicInfo().getMarketMaintainCost());
        // 市场名称
        marketDisplayVo.setMarketName(marketDevelopInfo.getMarketBasicInfo().getMarketName());
        // 每期开拓费用
        marketDisplayVo.setMarketResearchCost(marketDevelopInfo.getMarketBasicInfo().getMarketResearchCost());
        // 完成开发所需要的总周期
        marketDisplayVo.setMarketResearchPeriod(marketDevelopInfo.getMarketBasicInfo().getMarketResearchPeriod());

        // 已经开拓的周期数
        marketDisplayVo.setResearchedPeriod(marketDevelopInfo.getResearchedPeriod());
        // 当前开拓状态
        marketDisplayVo.setMarketStatus(marketDevelopInfo.getMarketStatus());

        return marketDisplayVo;
    }
}
