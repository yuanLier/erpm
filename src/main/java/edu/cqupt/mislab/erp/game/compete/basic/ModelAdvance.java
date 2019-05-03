package edu.cqupt.mislab.erp.game.compete.basic;

/**
 * @author chuyunfei
 * @description 推进某一个模块到下一个周期，每一个需要被推进的模块都需要实现在一个接口
 * @date 15:53 2019/5/2
 **/

public interface ModelAdvance {

    /**
     * 用于推进比赛的数据
     * @param gameId：推进哪一个比赛
     */
    boolean advance(Long gameId);
}
