package edu.cqupt.mislab.erp.user.service.impl;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.response.WebResponseVo.ResponseStatus;
import edu.cqupt.mislab.erp.commons.util.BeanCopyUtil;
import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.user.dao.MajorInfoRepository;
import edu.cqupt.mislab.erp.user.dao.UserAvatarRepository;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import edu.cqupt.mislab.erp.user.model.dto.UserStudentInfoRegisterDto;
import edu.cqupt.mislab.erp.user.model.dto.UserStudentInfoUpdateDto;
import edu.cqupt.mislab.erp.user.model.entity.*;
import edu.cqupt.mislab.erp.user.model.vo.UserStudentInfoBasicVo;
import edu.cqupt.mislab.erp.user.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toFailResponseVoWithMessage;
import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithData;
import static edu.cqupt.mislab.erp.commons.util.BeanCopyUtil.copyPropertiesSimple;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private UserAvatarRepository avatarRepository;

    @Autowired
    private UserStudentRepository studentRepository;

    @Autowired
    private MajorInfoRepository majorInfoRepository;

    @Override
    public WebResponseVo<String> userStudentRegister(UserStudentInfoRegisterDto registerDto){

        //是否该账号已经被启用
        final UserStudentInfoBasicVo basicInfoVo = getStudentBasicInfoByAccount(registerDto.getStudentAccount());

        if(basicInfoVo != null){

            return toFailResponseVoWithMessage(ResponseStatus.BAD_REQUEST,"该账户已经被注册且正在被使用，请不要重复注册！");
        }

        //判断账号是否正在等待审核
        final UserStudentInfo userStudentInfo = studentRepository.findByStudentAccountAndAccountEnable(registerDto.getStudentAccount(),false);

        if(userStudentInfo != null){

            return toFailResponseVoWithMessage(ResponseStatus.BAD_REQUEST,"该账户正在等待审核，请耐心等待老师审核！");
        }

        //进行账号注册
        UserStudentInfo studentInfo = new UserStudentInfo();

        copyPropertiesSimple(registerDto,studentInfo);

        //默认不启用该账户 todo 这个地方需要更改
        studentInfo.setAccountEnable(true);
        //默认性别为男
        studentInfo.setGender(UserGender.Man);
        //设置专业信息
        studentInfo.setMajorInfo(getAgencyInfo(registerDto.getMajorInfoId()));

        //存储并立即持久化到数据库
        studentInfo = studentRepository.saveAndFlush(studentInfo);

        //存储成功
        if(studentInfo.getId() != null){

            return toSuccessResponseVoWithData("注册成功，请耐心等待老师审核！");
        }

        return toFailResponseVoWithMessage(ResponseStatus.INTERNAL_SERVER_ERROR,"注册失败，服务器内部错误");
    }

    @Override
    public UserStudentInfoBasicVo getStudentBasicInfoByAccount(String userAccount){

        //能够获取详细信息的账户必须是要审核通过的账户
        final UserStudentInfo studentInfo = studentRepository.findByStudentAccountAndAccountEnable(userAccount,true);

        if(studentInfo != null){

            UserStudentInfoBasicVo studentInfoBasicVo = new UserStudentInfoBasicVo();

            //转换Entity和VO
            EntityVoUtil.copyFieldsFromEntityToVo(studentInfo,studentInfoBasicVo);

            return studentInfoBasicVo;
        }

        return null;
    }

    @Override
    public UserStudentInfoBasicVo getStudentBasicInfoByUserId(Long userId){

        //能够获取详细信息的账户必须是要审核通过的账户
        final UserStudentInfo studentInfo = studentRepository.findByIdAndAccountEnable(userId,true);

        if(studentInfo != null){

            UserStudentInfoBasicVo studentInfoBasicVo = new UserStudentInfoBasicVo();

            //转换Entity和VO
            EntityVoUtil.copyFieldsFromEntityToVo(studentInfo,studentInfoBasicVo);

            return studentInfoBasicVo;
        }

        return null;
    }

    @Override
    public Long checkStudentAccountAndPassword(String userAccount,String userPassword){

        final UserStudentInfo userStudentInfo = studentRepository.findByStudentAccountAndStudentPasswordAndAccountEnable(userAccount,userPassword,true);

        if(userStudentInfo != null){

            return userStudentInfo.getId();
        }

        return null;
    }

    @Override
    public boolean resetUserStudentPassword(Long userId,String oldPassword,String newPassword){

        final UserStudentInfo studentInfo = studentRepository.findByIdAndAccountEnable(userId,true);

        //账户不存在
        if(studentInfo == null){

            return false;
        }

        //密码不正确
        if(!oldPassword.equals(studentInfo.getStudentPassword())){

            return false;
        }

        //设置新的密码
        studentInfo.setStudentPassword(newPassword);

        //更改密码
        studentRepository.save(studentInfo);

        return true;
    }

    @Override
    public UserStudentInfoBasicVo updateStudentBasicInfo(UserStudentInfoUpdateDto updateDto){

        UserStudentInfo studentBasicInfo = studentRepository.findOne(updateDto.getId());

        if(studentBasicInfo != null){

            //将需要更改的数据复制过去
            BeanCopyUtil.copyPropertiesWithNonNullSourceFields(updateDto,studentBasicInfo);

            //专业信息特殊处理
            if(updateDto.getMajorInfoId() != null){

                studentBasicInfo.setMajorInfo(getAgencyInfo(updateDto.getMajorInfoId()));
            }

            //头像信息特殊处理
            if(updateDto.getUserAvatarInfoId() != null){

                studentBasicInfo.setUserAvatarInfo(getAvatarInfo(updateDto.getUserAvatarInfoId()));
            }

            //将数据同步到数据库
            studentBasicInfo = studentRepository.save(studentBasicInfo);

            UserStudentInfoBasicVo studentBasicInfoVo = new UserStudentInfoBasicVo();

            //将更改后的数据转载入视图对象
            EntityVoUtil.copyFieldsFromEntityToVo(studentBasicInfo,studentBasicInfoVo);

            return studentBasicInfoVo;
        }

        return null;
    }

    @Override
    public List<UserAvatarInfo> getUserAvatarInfos(){

        return avatarRepository.findAll();
    }


    @Override
    public List<MajorInfo> getAgencyInfos(){

        return majorInfoRepository.findAll();
    }

    @Override
    public MajorInfo getAgencyInfo(Long majorInfo){

        return majorInfoRepository.findOne(majorInfo);
    }

    @Override
    public UserAvatarInfo getAvatarInfo(Long userAvatarInfo){

        return avatarRepository.findOne(userAvatarInfo);
    }
}