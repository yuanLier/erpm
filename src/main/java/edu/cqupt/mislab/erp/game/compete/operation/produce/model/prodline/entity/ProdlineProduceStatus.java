package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity;

/**
 * @author yuanyiwen
 * @create 2019-03-10 17:30
 * @description 生产线的生产状态
 */
public enum  ProdlineProduceStatus {

    TOPRODUCE("未生产"),
    PRODUCING("生产中"),
    PRODUCEPAUSE("生产暂停"),
    PRODUCED("生产完成");

    private String comment;

    ProdlineProduceStatus(String comment){
        this.comment = comment;
    }
}
