package edu.cqupt.mislab.erp.game.manage.model.entity;

/**
 * @Author: chuyunfei
 * @Date: 2019/3/5 19:07
 * @Description: 比赛状态信息
 */

public enum GameStatusEnum {

    /*
    标识创建状态，这个状态可以被删除，可以成员添加等
     */
    CREATE,
    /*
    标识正在初始化状态，由于这个过程可能比较长，所以出于这个状态的比赛用户将无法进行操作
     */
    INITIALING,
    /*
    标识初始化完成并正常开始运营状态
     */
    PLAYING,
    /*
    标识完结状态，所有的企业都已经破产或者推进完所有的周期
     */
    OVER,
    ;
}
