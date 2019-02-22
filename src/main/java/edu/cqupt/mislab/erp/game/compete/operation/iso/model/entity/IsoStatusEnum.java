package edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity;

public enum IsoStatusEnum {
    UNDEVELOP("未认证"),
    DEVELOPING("认证中"),
    DEVELOPED("认证完毕");

    private String name;

    IsoStatusEnum(String name) {
        this.name = name;
    }
}
