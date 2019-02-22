package edu.cqupt.mislab.erp.game.compete.operation.order.constant;

public interface OrderConstant {

    /*
    等待选单会开始的消息前缀，后边跟的数值是还有几个参加了该场比赛却还没有投广告费的人数
     */
    String WAITE_TO_START_CHOOSE_KEY_NAME = "WAITE_TO_START_CHOOSE_KEY_NAME";

    /*
    订单选择的间隔时间，也就是最长的选择时间，单位是毫秒
     */
    Long CHOOSE_INTERVALS_TIME = 60L*1000L;
}
