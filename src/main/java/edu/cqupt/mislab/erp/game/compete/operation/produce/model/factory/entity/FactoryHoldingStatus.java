package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity;

/**
 * @author yuanyiwen
 * @create 2019-03-11 20:02
 * @description 企业厂房的拥有状态
 */
public enum  FactoryHoldingStatus {

    // 企业开始修建厂房时处于该状态
    DEVELOPING("修建中"),

    // 修建完成后转入该状态
    ISHOLDING("拥有中"),

    // 厂房出售后更新为该状态
    SELLED("已出售"),

    // 若该厂房非企业自建，则标志为该状态
    LEASING("租赁中");

    private String comment;

    FactoryHoldingStatus(String comment){
        this.comment = comment;
    }
}
