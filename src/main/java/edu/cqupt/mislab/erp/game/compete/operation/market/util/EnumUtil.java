package edu.cqupt.mislab.erp.game.compete.operation.market.util;

import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketStatusEnum;

/**
 * 检查前端传来的值是否属于某个市场状态
 */
public class EnumUtil {

    public static boolean isInclude(String key) {
        boolean include = false;
        for(MarketStatusEnum marketStatus : MarketStatusEnum.values()) {
            if(marketStatus.toString().equals(key)) {
                include = true;
                break;
            }
        }
        return include;
    }

}
