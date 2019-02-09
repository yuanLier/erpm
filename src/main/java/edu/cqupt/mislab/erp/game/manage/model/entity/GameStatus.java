package edu.cqupt.mislab.erp.game.manage.model.entity;

public enum  GameStatus {

    CREATE,//标识创建状态
    INITIALING,//标识正在初始化状态
    PLAYING,//标识初始化完成并正常开始运营状态
    OVER,//标识完结状态，所有的企业都已经破产或者推进完所有的周期
    ;
}
