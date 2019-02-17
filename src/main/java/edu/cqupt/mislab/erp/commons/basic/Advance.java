package edu.cqupt.mislab.erp.commons.basic;

public interface Advance {

    /**
     * 用于推进比赛的数据
     * @param gameId：推进哪一个比赛
     */
    boolean advance(Long gameId);
}
