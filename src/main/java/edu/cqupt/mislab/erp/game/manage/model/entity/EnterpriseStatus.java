package edu.cqupt.mislab.erp.game.manage.model.entity;

public enum EnterpriseStatus {

    CREATE,//企业创建并等待成员加入状态
    SURE,//企业准备开始比赛
    PLAYING,//企业正常运营
    BANKRUPT,//企业破产
    OVER,//企业正常完成所有的周期完结
    ;
}
