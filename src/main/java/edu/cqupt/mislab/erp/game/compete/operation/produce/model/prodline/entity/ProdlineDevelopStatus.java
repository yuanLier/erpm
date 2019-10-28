package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity;

/**
 * @author yuanyiwen
 * @create 2019-03-09 19:36
 * @description 生产线修建状态
 */
public enum  ProdlineDevelopStatus {

    //
    DEVELOPING("修建中"),
    DEVELOPPAUSE("修建暂停"),
    DEVELOPED("修建成功");

    private String comment;

    ProdlineDevelopStatus(String comment){
        this.comment = comment;
    }
}
