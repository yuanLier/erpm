package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity;

/**
 * @author yuanyiwen
 * @create 2019-03-09 16:48
 * @description 企业厂房的修建状态
 */
public enum  FactoryDevelopStatus {

//    厂房的修建状态
    DEVELOPED("修建成功"),
    DEVELOPING("修建中"),
    DEVELOPPAUSE("修建暂停"),
    TODEVELOP("未修建"),

//    厂房的生产使用状态
    ISFULL("无空闲生产线"),
    NOTFULL("有空闲生产线");

    private String comment;

    FactoryDevelopStatus(String comment){
        this.comment = comment;
    }
}
