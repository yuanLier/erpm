package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity;

/**
 * @author yuanyiwen
 * @create 2019-03-11 20:02
 * @description 企业厂房的拥有状态
 */
public enum FactoryHoldingStatus {

    // 若厂房为企业自建，则标志为拥有中
    HOLDING("拥有中"),

    // 若该厂房非企业自建，则标志为租赁中
    LEASING("租赁中");

    private String comment;

    FactoryHoldingStatus(String comment){
        this.comment = comment;
    }
}
