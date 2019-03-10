package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity;

/**
 * @author yuanyiwen
 * @create 2019-03-10 23:42
 * @description 厂房的租赁状态
 */
public enum FactoryLeaseStatus {

    LEASING("租赁中"),
    LEASEPAUSE("暂停租赁"),
    TOLEASE("未租赁");

    private String comment;

    FactoryLeaseStatus(String comment){
        this.comment = comment;
    }
}
