package edu.cqupt.mislab.erp.game.manage.constant;

public interface ManageConstant {

    //前端连接websocket需要携带的哪一个比赛的参数名称
    String WEBSOCKET_REQUEST_ATTR_GAME_ID = "gameId";

    //用于前端接收识别后端的信息
    String ENTERPRISE_DELETE_KEY_NAME = "ENTERPRISE_DELETE";

    //用于前端接收识别后端的信息
    String GAME_DELETE_KEY_NAME = "ENTERPRISE_DELETE";

    //用于前端接收识别后端的信息
    String GAME_INIT_COMPLETE = "GAME_INIT_COMPLETE";
}
