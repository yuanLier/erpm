package edu.cqupt.mislab.erp.game.manage.service;

public interface GameUserRoleService {

    //获取一个用户在某场比赛里面的角色
    GameEnterpriseUserRole getUserRoleInOneGame(Long gameId,Long userId);

    enum GameEnterpriseUserRole {
        NOT_FOUND("用户或比赛不存在"),//用户或者比赛不存在
        ENTERPRISE_CREATOR("用户已经创建了一个企业"),//企业创建者
        ENTERPRISE_MEMBER("用户已经加入一个企业"),//企业成员
        PASSERBY("用户没有加入企业也没有创建企业");//路人甲

        private String message;

        private GameEnterpriseUserRole(String message){
            this.message = message;
        }

        public String getMessage(){
            return message;
        }
    }
}
