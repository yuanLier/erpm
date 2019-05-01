package edu.cqupt.mislab.erp.game.manage.service;

import edu.cqupt.mislab.erp.game.manage.model.entity.GameEnterpriseUserRoleEnum;

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
    GameEnterpriseUserRoleEnum getUserRoleInOneGame(Long gameId, Long userId);
}
