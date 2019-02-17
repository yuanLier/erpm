package edu.cqupt.mislab.erp.game.manage.constant;

public interface ManageConstant {

    //前端连接websocket需要携带的哪一个比赛的参数名称
    String WEBSOCKET_REQUEST_ATTR_GAME_ID = "gameId";

    //用于前端接收识别后端的信息，企业创建
    String ENTERPRISE_CREATE_KEY_NAME = "ENTERPRISE_CREATE";

    //用于前端接收识别后端的信息，企业删除
    String ENTERPRISE_DELETE_KEY_NAME = "ENTERPRISE_DELETE";

    //用于前端接收识别后端的信息，企业确认
    String ENTERPRISE_SURE_KEY_NAME = "ENTERPRISE_SURE";

    //用于前端接收识别后端的信息，比赛删除
    String GAME_DELETE_KEY_NAME = "GAME_DELETE";

    //用于前端接收识别后端的信息，比赛开始初始化
    String GAME_TO_INIT_KEY_NAME = "GAME_TO_INIT";

    //用于前端接收识别后端的信息，比赛初始化完成
    String GAME_INIT_COMPLETE = "GAME_INIT_COMPLETE";
}
