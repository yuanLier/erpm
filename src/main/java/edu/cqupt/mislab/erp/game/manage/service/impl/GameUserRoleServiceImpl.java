package edu.cqupt.mislab.erp.game.manage.service.impl;

import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseMemberInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameEnterpriseUserRoleEnum;
import edu.cqupt.mislab.erp.game.manage.service.GameUserRoleService;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chuyunfei
 * @description
 * @date 22:38 2019/4/26
 **/

@Service
public class GameUserRoleServiceImpl implements GameUserRoleService {

    @Autowired private GameBasicInfoRepository gameBasicInfoRepository;
    @Autowired private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired private EnterpriseMemberInfoRepository enterpriseMemberInfoRepository;
    @Autowired private UserStudentRepository userStudentRepository;

    @Override
    public GameEnterpriseUserRoleEnum getUserRoleInOneGame(Long gameId, Long userId){

        //查看比赛信息是否合理，比赛是否存在
        final GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);

        if(gameBasicInfo == null){

            return GameEnterpriseUserRoleEnum.NOT_FOUND;
        }

        //判断比赛创建者是否存在
        final UserStudentInfo userStudentInfo = userStudentRepository.findByIdAndAccountEnable(userId,true);

        if(userStudentInfo == null){

            return GameEnterpriseUserRoleEnum.NOT_FOUND;
        }

        //判断该用户是否已经在该比赛中创建了一个企业
        if(enterpriseBasicInfoRepository.existsByUserStudentInfo_IdAndGameBasicInfo_Id(userId,gameId)){

            return GameEnterpriseUserRoleEnum.ENTERPRISE_CREATOR;
        }

        //判断该用户是否已经加入了一个企业
        if(enterpriseMemberInfoRepository.existsByUserStudentInfo_IdAndEnterpriseBasicInfo_GameBasicInfo_Id(userId,gameId)){

            return GameEnterpriseUserRoleEnum.ENTERPRISE_MEMBER;
        }

        return GameEnterpriseUserRoleEnum.PASSERBY;
    }
}
