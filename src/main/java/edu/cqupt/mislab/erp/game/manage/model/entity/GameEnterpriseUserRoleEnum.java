package edu.cqupt.mislab.erp.game.manage.model.entity;

import lombok.Getter;

/**
 * @author chuyunfei
 * @create 2019-05-01 18:43
 * @description
 */
@Getter
public enum GameEnterpriseUserRoleEnum {

    //
    NOT_FOUND("用户或比赛不存在"),
    ENTERPRISE_CREATOR("用户已经创建了一个企业"),
    ENTERPRISE_MEMBER("用户已经加入一个企业"),
    PASSERBY("用户没有加入企业也没有创建企业");

    private String message;

    GameEnterpriseUserRoleEnum(String message){
        this.message = message;
    }

}
