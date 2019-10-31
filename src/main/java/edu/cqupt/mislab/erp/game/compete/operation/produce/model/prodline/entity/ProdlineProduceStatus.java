package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity;

/**
 * @author yuanyiwen
 * @create 2019-03-10 17:30
 * @description 生产线的生产状态
 */
public enum  ProdlineProduceStatus {

    //
    TOPRODUCE("待生产"),
    PRODUCING("生产中"),
    PRODUCEPAUSE("生产暂停"),
    PRODUCED("生产完成"),

    // 当且仅当holdingInfoStatus为NOT_USABLE的时候，生产状态转为不可用
    NOT_USABLE("不可用"),

    // 当且仅当holdingInfoStatus为SOLD的时候，生产状态转为已出售
    SOLD("已出售"),

    // 转产完成后转入待生产状态
    TRANSFERRING("转产中");

    private String comment;

    ProdlineProduceStatus(String comment){
        this.comment = comment;
    }
}
