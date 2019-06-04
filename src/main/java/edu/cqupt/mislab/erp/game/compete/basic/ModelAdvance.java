package edu.cqupt.mislab.erp.game.compete.basic;

/**
 * @author yuanyiwen
 * @create 2019-05-12 11:32
 * @description 周期推进模板方法
 */
public interface ModelAdvance {

    /**
     * 这个方法用来记录比赛期间各模块的历史数据
     * @param gameId
     * @return
     */
    boolean modelHistory(Long gameId);

    /**
     * 这个方法用来实现各模块自己的周期推进
     * @param gameId
     * @return
     */
    boolean modelAdvance(Long gameId);

    /**
     * 推进模板
     * @param gameId
     * @return
     */
    default boolean advance(Long gameId) {

        // 历史记录是否成功
        boolean historyBoolean = modelHistory(gameId);

        // 周期推进是否成功
        boolean advanceBoolean = modelAdvance(gameId);

        return historyBoolean && advanceBoolean;
    }
}
