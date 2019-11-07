package edu.cqupt.mislab.erp.game.compete.operation.constant;

/**
 * @author yuanyiwen
 * @create 2019-07-06 18:05
 * @description 比赛设定中的常量（主要是订单会这一部分的
 */
public interface GameSettingConstant {

    /**
     * 订单总数设置为企业总数的ENTERPRISE_SIZE_RATE倍
     */
    Double ENTERPRISE_SIZE_RATE = 2.2;


    /**
     * 系统分配的初始化金额 todo 设置为管理员可更改
     */
    Double INIT_FINANCE = 100000D;

    /***
     * 一次性贷款的最大额度 todo 设置为管理员可改
     */
    Double MAX_LOAN_AMOUNT = 100000000D;
}
