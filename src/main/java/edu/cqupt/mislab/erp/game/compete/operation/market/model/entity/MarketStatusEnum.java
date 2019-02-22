package edu.cqupt.mislab.erp.game.compete.operation.market.model.entity;

public enum MarketStatusEnum {
    UNDEVELOP("未开拓"),
    DEVELOPING("开拓中"),
    DEVELOPED("开拓完成");

    String name;

    MarketStatusEnum(String name) {
        this.name = name;
    }
}
