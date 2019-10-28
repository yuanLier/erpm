package edu.cqupt.mislab.erp.user.service;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.user.model.dto.UserStudentInfoRegisterDto;
import edu.cqupt.mislab.erp.user.model.dto.UserStudentInfoUpdateDto;
import edu.cqupt.mislab.erp.user.model.entity.MajorBasicInfo;
import edu.cqupt.mislab.erp.user.model.entity.UserAvatarInfo;
import edu.cqupt.mislab.erp.user.model.vo.UserStudentInfoBasicVo;

import java.util.List;

/**
 * @author chuyunfei
 * @description 
 * @date 21:35 2019/4/22
 **/

public interface StudentService {


    /**
     * @author chuyunfei
     * @description 通过用户的Id去获取用户的基本信息VO对象
     * @date 21:34 2019/4/22
     **/
    UserStudentInfoBasicVo getStudentBasicInfoByUserId(Long userId);


    /**
     * @author chuyunfei
     * @description 检查账户和密码是否一致（若一致，返回用户id
     * @date 21:34 2019/4/22
     **/
    Long checkStudentAccountAndPassword(String userAccount,String userPassword);


    /**
     * @author chuyunfei
     * @description 更改用户的密码
     * @date 21:34 2019/4/22
     **/
    Boolean resetUserStudentPassword(Long userId,String oldPassword,String newPassword);


    /**
     * @author chuyunfei
     * @description 更改账户的个人信息
     * @date 21:34 2019/4/22
     **/
    UserStudentInfoBasicVo updateStudentBasicInfo(UserStudentInfoUpdateDto updateDto);


    /**
     * @author chuyunfei
     * @description 获取全部头像
     * @date 21:34 2019/4/22
     **/
    List<UserAvatarInfo> getUserAvatarInfos();


    /**
     * @author chuyunfei
     * @description 获取全部专业
     * @date 21:34 2019/4/22
     **/
    List<MajorBasicInfo> getAgencyInfos();


    /**
     * @author chuyunfei
     * @description 根据专业id获取专业信息
     * @date 21:34 2019/4/22
     **/
    MajorBasicInfo getAgencyInfo(Long majorInfoId);


    /**
     * @author chuyunfei
     * @description 根据头像id获取头像
     * @date 21:34 2019/4/22
     **/
    UserAvatarInfo getAvatarInfo(Long userAvatarInfoId);


    /**
     * @author chuyunfei
     * @description 学生账号注册接口
     * @date 21:34 2019/4/22
     **/
    WebResponseVo<String> userStudentRegister(UserStudentInfoRegisterDto registerDto);


    /**
     * @author chuyunfei
     * @description 通过账户获取基本数据信息
     * @date 21:34 2019/4/22
     **/
    UserStudentInfoBasicVo getStudentBasicInfoByAccount(String userAccount);
}
