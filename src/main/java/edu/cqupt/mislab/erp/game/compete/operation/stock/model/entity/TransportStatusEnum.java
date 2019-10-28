package edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity;

/**
 * @author yuanyiwen
 * @create 2019-03-31 22:28
 * @description 运输状态
 */
public enum TransportStatusEnum {

    //
    TOCHECK("待审核"),
    CHECKED("已审核"),

    // 用户选定已审核后，下一周期将转入运输中
    TRANSPORTING("运输中"),
    ARRIVED("已送达");

    private String comment;

    TransportStatusEnum(String comment) {
        this.comment = comment;
    }

}
