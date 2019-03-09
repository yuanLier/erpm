package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity;

/**
 * @author yuanyiwen
 * @create 2019-03-09 19:36
 * @description 生产线修建状态
 */
public enum  ProdlineDevelopStatus {

//    修建状态
    DEVELOPED("修建成功"),
    DEVELOPING("修建中"),
    DEVELOPPAUSE("修建暂停"),
    TODEVELOP("未修建"),

//    使用状态 todo 未修建和空闲中是否需要合并？
    INUSE("使用中"),
    NOTINUSE("空闲中");

    private String comment;

    ProdlineDevelopStatus(String comment){
        this.comment = comment;
    }
}
