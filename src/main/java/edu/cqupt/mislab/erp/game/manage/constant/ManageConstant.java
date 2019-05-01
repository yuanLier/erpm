package edu.cqupt.mislab.erp.game.manage.constant;

/**
 * @author chuyunfei
 * @description 
 * @date 22:40 2019/4/26
 **/

public interface ManageConstant {

    /**
     * @author chuyunfei
     * @description 前端连接websocket需要携带的哪一个比赛的参数名称
     * @date 22:40 2019/4/26
     **/
    String WEBSOCKET_REQUEST_ATTR_GAME_ID = "gameId ：";

    /**
     * @author chuyunfei
     * @description 用于前端接收识别后端的信息，企业创建
     * @date 22:40 2019/4/26
     **/
    String ENTERPRISE_CREATE_KEY_NAME = "ENTERPRISE_CREATE ：";

    /**
     * @author chuyunfei
     * @description 用于前端接收识别后端的信息，企业删除
     * @date 22:40 2019/4/26
     **/
    String ENTERPRISE_DELETE_KEY_NAME = "ENTERPRISE_DELETE ：";

    /**
     * @author chuyunfei
     * @description 用于前端接收识别后端的信息，企业确认
     * @date 22:40 2019/4/26
     **/
    String ENTERPRISE_SURE_KEY_NAME = "ENTERPRISE_SURE ：";

    /**
     * @author chuyunfei
     * @description 用于前端接收识别后端的信息，比赛删除
     * @date 22:40 2019/4/26
     **/
    String GAME_DELETE_KEY_NAME = "GAME_DELETE ：";

    /**
     * @author chuyunfei
     * @description 用于前端接收识别后端的信息，比赛开始初始化
     * @date 22:40 2019/4/26
     **/
    String GAME_TO_INIT_KEY_NAME = "GAME_TO_INIT ：";

    /**
     * @author chuyunfei
     * @description 用于前端接收识别后端的信息，比赛初始化完成
     * @date 22:40 2019/4/26
     **/
    String GAME_INIT_COMPLETE = "GAME_INIT_COMPLETE ：";
}
