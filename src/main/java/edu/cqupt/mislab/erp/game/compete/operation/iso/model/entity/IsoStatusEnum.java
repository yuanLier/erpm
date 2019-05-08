package edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity;

import lombok.Getter;

/**
 * @Author: chuyunfei
 * @Date: 2019/3/4 20:26
 * @Description: ISO认证信息的状态枚举对象
 **/

@Getter
public enum IsoStatusEnum {

    //
    DEVELOPED("研发成功"),
    DEVELOPING("研发中"),
    DEVELOPPAUSE("研发暂停"),
    TODEVELOP("未研发");

    private String comment;

    IsoStatusEnum(String comment) {
        this.comment = comment;
    }
}
