package edu.cqupt.mislab.erp.commons.util;

import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseMemberInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseDetailInfoVo;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseMemberDisplayVo;
import edu.cqupt.mislab.erp.game.manage.model.vo.GameDetailInfoVo;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import edu.cqupt.mislab.erp.user.model.vo.UserStudentInfoBasicVo;

/**
 * 用于转换Entity和VO的工具类，方法全部使用重载，第一个参数是Entity对象，第二个参数是VO对象
 */
public abstract class EntityVoUtil {

    public static void copyFieldsFromEntityToVo(UserStudentInfo userStudentInfo,UserStudentInfoBasicVo infoBasicVo){

        BeanCopyUtil.copyPropertiesSimple(userStudentInfo,infoBasicVo);

        if(userStudentInfo.getUserTeacherInfo() != null){

            infoBasicVo.setTeacherId(userStudentInfo.getUserTeacherInfo().getId());
        }
    }

    public static void copyFieldsFromEntityToVo(GameBasicInfo gameBasicInfo,GameDetailInfoVo detailInfoVo){

        BeanCopyUtil.copyPropertiesSimple(gameBasicInfo,detailInfoVo);

        detailInfoVo.setCreatorName(gameBasicInfo.getGameCreator().getStudentName());
        detailInfoVo.setPeriod(gameBasicInfo.getGameInitInfo().getPeriod());
        detailInfoVo.setTotalYear(gameBasicInfo.getGameInitInfo().getTotalYear());

        if(gameBasicInfo.getEnterpriseBasicInfos() == null){
            detailInfoVo.setEnterpriseNumber(0);
        }else {
            detailInfoVo.setEnterpriseNumber(gameBasicInfo.getEnterpriseBasicInfos().size());
        }
    }

    public static void copyFieldsFromEntityToVo(EnterpriseBasicInfo enterpriseBasicInfo,EnterpriseDetailInfoVo enterpriseDetailInfoVo){

        enterpriseDetailInfoVo.setId(enterpriseBasicInfo.getId());
        enterpriseDetailInfoVo.setCeoId(enterpriseBasicInfo.getEnterpriseCeo().getId());
        enterpriseDetailInfoVo.setCeoName(enterpriseBasicInfo.getEnterpriseCeo().getStudentName());
        enterpriseDetailInfoVo.setEnterpriseName(enterpriseBasicInfo.getEnterpriseName());
        enterpriseDetailInfoVo.setGameId(enterpriseBasicInfo.getGameInfo().getId());
        enterpriseDetailInfoVo.setEnterpriseStatus(enterpriseBasicInfo.getEnterpriseStatus());

        if(enterpriseBasicInfo.getMemberInfos() == null){
            enterpriseDetailInfoVo.setEnterpriseMemberNumber(0);
        }else {
            enterpriseDetailInfoVo.setEnterpriseMemberNumber(enterpriseBasicInfo.getMemberInfos().size());
        }
    }

    public static void copyFieldsFromEntityToVo(EnterpriseMemberInfo enterpriseMemberInfo,EnterpriseMemberDisplayVo displayVo){

        displayVo.setId(enterpriseMemberInfo.getId());
        displayVo.setStudentAccount(enterpriseMemberInfo.getStudentInfo().getStudentAccount());
        displayVo.setStudentName(enterpriseMemberInfo.getStudentInfo().getStudentName());
        displayVo.setCollege(enterpriseMemberInfo.getStudentInfo().getMajorInfo().getCollege().getCollege());
        displayVo.setMajor(enterpriseMemberInfo.getStudentInfo().getMajorInfo().getMajor());
        displayVo.setGameContributionRate(enterpriseMemberInfo.getGameContributionRate());
        displayVo.setAvatarLocation(enterpriseMemberInfo.getStudentInfo().getUserAvatarInfo().getAvatarLocation());
    }
}
