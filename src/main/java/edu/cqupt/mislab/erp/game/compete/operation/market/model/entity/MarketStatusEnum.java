package edu.cqupt.mislab.erp.game.compete.operation.market.model.entity;

import lombok.Getter;

/**
 * @Author: chuyunfei
 * @Date: 2019/3/4 21:16
 * @Description: 市场的状态信息枚举对象
 **/

@Getter
public enum MarketStatusEnum {

    //
    DEVELOPED("开拓成功"),
    DEVELOPING("开拓中"),
    DEVELOPPAUSE("开拓暂停"),
    TODEVELOP("未开拓");

    private String comment;

    MarketStatusEnum(String comment) {
        this.comment = comment;
    }
}
