package edu.cqupt.mislab.erp.game.compete.operation.market.advance;

import edu.cqupt.mislab.erp.game.compete.basic.ModelAdvance;

/**
 * @author yuanyiwen
 * @create 2019-05-13 20:14
 * @description
 */
public class MarketAdvance implements ModelAdvance {

    @Override
    public boolean modelHistory(Long gameId) {
        return false;
    }

    @Override
    public boolean modelAdvance(Long gameId) {
        return false;
    }
}
