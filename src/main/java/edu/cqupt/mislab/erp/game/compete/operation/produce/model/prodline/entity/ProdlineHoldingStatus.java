package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity;

/**
 * @author yuanyiwen
 * @create 2019-03-11 20:16
 * @description 企业中生产线的拥有状态
 */
public enum ProdlineHoldingStatus {

    // 企业开始修建生产线时处于该状态
    DEVELOPING("修建中"),

    // 修建完成后转入该状态
    PRODUCING("生产中"),

    // 生产线出售后更新为该状态（生产线出售均为一次性到账
    SELLED("已出售"),

    // 出售到账后更新为该状态
    ARRIVAL("已到账");

    private String comment;

    ProdlineHoldingStatus(String comment){
        this.comment = comment;
    }
}
