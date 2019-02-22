package edu.cqupt.mislab.erp.game.compete.operation.product.model.entity;

public enum  ProductStatusEnum {

    UNDEVELOP("未研发"),
    DEVELOPING("研发中"),
    DEVELOPED("已研发");

    String name;

    ProductStatusEnum(String name) {
        this.name = name;
    }
}
