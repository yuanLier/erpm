package edu.cqupt.mislab.erp.user.service;

import edu.cqupt.mislab.erp.user.model.dto.UserStudentInfoSearchDto;
import edu.cqupt.mislab.erp.user.model.dto.UserStudentInfoUpdateDto;
import edu.cqupt.mislab.erp.user.model.entity.MajorInfo;
import edu.cqupt.mislab.erp.user.model.entity.UserAvatarInfo;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import edu.cqupt.mislab.erp.user.model.vo.UserStudentInfoBasicVo;
import org.springframework.data.domain.Example;

import java.util.List;

public interface StudentService {

    UserStudentInfoBasicVo getStudentBasicInfoByAccount(String userAccount);

    boolean checkStudentAccountAndPassword(String userAccount,String userPassword);

    boolean resetUserStudentPassword(String userAccount,String oldPassword,String newPassword);

    UserStudentInfoBasicVo updateStudentBasicInfo(UserStudentInfoUpdateDto updateDto);

    Example<UserStudentInfo> getUserStudentBasicInfoExampleBySearchDto(UserStudentInfoSearchDto searchDto);

    List<UserAvatarInfo> getUserAvatarInfos();

    List<MajorInfo> getAgencyInfos();

    MajorInfo getAgencyInfo(Long majorInfo);

    boolean checkAvatarExist(Long userAvatarInfo);

    UserAvatarInfo getAvatarInfo(Long userAvatarInfo);

    UserStudentInfo getUserStudentBasicInfoById(Long studentId);

    List<UserStudentInfo> getStudentsByTeacherId(Long teacherId);
}
