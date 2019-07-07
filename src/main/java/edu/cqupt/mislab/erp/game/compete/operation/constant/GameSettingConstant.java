package edu.cqupt.mislab.erp.game.compete.operation.constant;

/**
 * @author yuanyiwen
 * @create 2019-07-06 18:05
 * @description 比赛设定中的常量（主要是订单会这一部分的
 */
public interface GameSettingConstant {


    /**
     * 选取研发比率超过HOLDING_RATE的市场产品，研发比率不足HOLDING_RATE的ISO
     */
    Double HOLDING_RATE = 0.65;

    /**
     * 订单总数设置为企业总数的ENTERPRISE_SIZE_RATE倍
     */
    Double ENTERPRISE_SIZE_RATE = 2.2;

}
