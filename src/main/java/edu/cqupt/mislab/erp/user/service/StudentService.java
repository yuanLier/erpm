package edu.cqupt.mislab.erp.user.service;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.user.model.dto.UserStudentInfoRegisterDto;
import edu.cqupt.mislab.erp.user.model.dto.UserStudentInfoUpdateDto;
import edu.cqupt.mislab.erp.user.model.entity.MajorInfo;
import edu.cqupt.mislab.erp.user.model.entity.UserAvatarInfo;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import edu.cqupt.mislab.erp.user.model.vo.UserStudentInfoBasicVo;
import org.springframework.data.domain.Example;

import java.util.List;

public interface StudentService {

    //通过用户的Id去获取用户的基本信息VO对象
    UserStudentInfoBasicVo getStudentBasicInfoByUserId(Long userId);

    //检查账户和密码是否一致
    Long checkStudentAccountAndPassword(String userAccount,String userPassword);

    //更改用户的密码
    boolean resetUserStudentPassword(Long userId,String oldPassword,String newPassword);

    //更改账户的个人信息
    UserStudentInfoBasicVo updateStudentBasicInfo(UserStudentInfoUpdateDto updateDto);

    List<UserAvatarInfo> getUserAvatarInfos();

    List<MajorInfo> getAgencyInfos();

    MajorInfo getAgencyInfo(Long majorInfo);

    UserAvatarInfo getAvatarInfo(Long userAvatarInfo);

    //学生账号注册接口
    WebResponseVo<String> userStudentRegister(UserStudentInfoRegisterDto registerDto);

    //通过账户获取基本数据信息，只能由于判断账户是否存在
    UserStudentInfoBasicVo getStudentBasicInfoByAccount(String userAccount);
}
