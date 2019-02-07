package edu.cqupt.mislab.erp.commons.util;

import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import edu.cqupt.mislab.erp.user.model.vo.UserStudentInfoBasicVo;

public abstract class EntityVoUtil {

    public static void copyFieldsFromStudentEntityToBasicVo(UserStudentInfo userStudentInfo,UserStudentInfoBasicVo infoBasicVo){

        BeanCopyUtil.copyPropertiesSimple(userStudentInfo,infoBasicVo);

        if(userStudentInfo.getUserTeacherInfo() != null){

            infoBasicVo.setTeacherId(userStudentInfo.getUserTeacherInfo().getId());
        }
    }
}
