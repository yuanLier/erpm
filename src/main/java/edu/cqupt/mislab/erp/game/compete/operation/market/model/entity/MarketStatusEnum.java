package edu.cqupt.mislab.erp.game.compete.operation.market.model.entity;

import lombok.Getter;

@Getter
public enum MarketStatusEnum {

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/4 21:16
     * @Description: 市场的状态信息枚举对象
     **/

    DEVELOPED("研发成功"),
    DEVELOPING("研发中"),
    DEVELOPPAUSE("研发暂停"),
    TODEVELOP("未研发");

    private String comment;

    MarketStatusEnum(String comment) {
        this.comment = comment;
    }
}
