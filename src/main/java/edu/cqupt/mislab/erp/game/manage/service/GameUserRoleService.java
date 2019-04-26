package edu.cqupt.mislab.erp.game.manage.service;

import lombok.Getter;

/**
 * @author chuyunfei
 * @description 用户状态管理
 * @date 21:52 2019/4/26
 **/

public interface GameUserRoleService {

    /**
     * @author chuyunfei
     * @description 获取一个用户在某场比赛里面的状态
     * @date 21:49 2019/4/26
     **/
    GameEnterpriseUserRole getUserRoleInOneGame(Long gameId,Long userId);

    @Getter
    enum GameEnterpriseUserRole {
        //
        NOT_FOUND("用户或比赛不存在"),
        ENTERPRISE_CREATOR("用户已经创建了一个企业"),
        ENTERPRISE_MEMBER("用户已经加入一个企业"),
        PASSERBY("用户没有加入企业也没有创建企业");

        private String message;

        GameEnterpriseUserRole(String message){
            this.message = message;
        }
    }
}
