package edu.cqupt.mislab.erp.game.manage.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEnterpriseStatusCheckDto {

    //那一场比赛
    private Long gameId;

    //哪一个用户
    private Long userId;

    //检查是否是企业的创建者
    boolean enterpriseCreator;

    //检测是否是企业的成员
    boolean enterpriseMember;
}
