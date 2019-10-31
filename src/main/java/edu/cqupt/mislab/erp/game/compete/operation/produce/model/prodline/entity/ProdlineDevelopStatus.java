package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity;

/**
 * @author yuanyiwen
 * @create 2019-03-09 19:36
 * @description 生产线修建状态
 */
public enum  ProdlineDevelopStatus {

    //
    DEVELOPING("安装中"),
    DEVELOPPAUSE("安装暂停"),
    DEVELOPED("安装成功");

    private String comment;

    ProdlineDevelopStatus(String comment){
        this.comment = comment;
    }
}
