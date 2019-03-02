package edu.cqupt.mislab.erp.game.compete.basic;

import java.util.List;

public interface GameModelInit {

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/1 20:15
     * @Description: 用于初始化某个比赛的某个模块，如果返回值为null标识比赛初始化正常完成，否则每一条数据
     * 都是一个错误原因
     **/
    List<String> initGameModel(Long gameId);
}
