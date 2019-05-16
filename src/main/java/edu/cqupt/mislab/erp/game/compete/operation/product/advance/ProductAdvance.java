package edu.cqupt.mislab.erp.game.compete.operation.product.advance;

import edu.cqupt.mislab.erp.game.compete.basic.ModelAdvance;

/**
 * @author yuanyiwen
 * @create 2019-05-16 10:07
 * @description
 */
public class ProductAdvance implements ModelAdvance {
    @Override
    public boolean modelHistory(Long gameId) {
        return false;
    }

    @Override
    public boolean modelAdvance(Long gameId) {
        return false;
    }
}
