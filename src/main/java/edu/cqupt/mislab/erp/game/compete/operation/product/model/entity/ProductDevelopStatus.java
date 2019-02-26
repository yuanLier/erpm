package edu.cqupt.mislab.erp.game.compete.operation.product.model.entity;

import lombok.Getter;

@Getter
public enum ProductDevelopStatus {

    /**
     * 产品研发的状态信息
     */
    DEVELOPED("研发成功"),
    DEVELOPING("研发中"),
    DEVELOPPAUSE("研发暂停"),
    TODEVELOP("未研发");

    private String comment;

    ProductDevelopStatus(String comment){
        this.comment = comment;
    }
}
