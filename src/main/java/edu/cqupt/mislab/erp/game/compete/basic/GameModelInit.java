package edu.cqupt.mislab.erp.game.compete.basic;

public interface GameModelInit {

    /**
     * 用于初始化某个比赛的某个模块
     * @param gameId：初始化哪一个比赛
     */
    boolean initGameModel(Long gameId);
}
