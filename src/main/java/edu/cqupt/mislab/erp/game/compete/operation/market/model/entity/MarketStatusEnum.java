package edu.cqupt.mislab.erp.game.compete.operation.market.model.entity;

import lombok.Getter;

@Getter
public enum MarketStatusEnum {

    DEVELOPED("研发成功"),
    DEVELOPING("研发中"),
    DEVELOPPAUSE("研发暂停"),
    TODEVELOP("未研发");

    private String comment;

    MarketStatusEnum(String comment) {
        this.comment = comment;
    }
}
