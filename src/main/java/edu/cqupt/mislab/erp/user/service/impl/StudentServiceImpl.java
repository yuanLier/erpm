package edu.cqupt.mislab.erp.user.service.impl;

import edu.cqupt.mislab.erp.commons.util.BeanCopyUtil;
import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.user.dao.MajorInfoRepository;
import edu.cqupt.mislab.erp.user.dao.UserAvatarRepository;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import edu.cqupt.mislab.erp.user.model.dto.UserStudentInfoSearchDto;
import edu.cqupt.mislab.erp.user.model.dto.UserStudentInfoUpdateDto;
import edu.cqupt.mislab.erp.user.model.entity.*;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo.UserStudentInfoBuilder;
import edu.cqupt.mislab.erp.user.model.vo.UserStudentInfoBasicVo;
import edu.cqupt.mislab.erp.user.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private UserAvatarRepository avatarRepository;

    @Autowired
    private UserStudentRepository studentRepository;

    @Autowired
    private MajorInfoRepository majorInfoRepository;

    @Override
    public UserStudentInfoBasicVo getStudentBasicInfoByAccount(String userAccount){

        //必须要审核通过才行
        UserStudentInfoSearchDto searchDto = UserStudentInfoSearchDto.builder()
                .studentAccount(userAccount)
                .accountEnable(true).build();

        final Example<UserStudentInfo> example = getUserStudentBasicInfoExampleBySearchDto(searchDto);

        final UserStudentInfo studentInfo = studentRepository.findOne(example);

        if(studentInfo != null){

            UserStudentInfoBasicVo studentInfoBasicVo = new UserStudentInfoBasicVo();

            EntityVoUtil.copyFieldsFromEntityToVo(studentInfo,studentInfoBasicVo);

            return studentInfoBasicVo;
        }

        return null;
    }

    @Override
    public boolean checkStudentAccountAndPassword(String userAccount,String userPassword){

        UserStudentInfoSearchDto searchDto = UserStudentInfoSearchDto.builder()
                .studentAccount(userAccount)
                .studentPassword(userPassword)
                .accountEnable(true).build();

        final Example<UserStudentInfo> example = getUserStudentBasicInfoExampleBySearchDto(searchDto);

        return studentRepository.count(example) == 1;
    }

    @Override
    public boolean resetUserStudentPassword(String userAccount,String oldPassword,String newPassword){

        UserStudentInfoSearchDto searchDto = UserStudentInfoSearchDto.builder()
                .studentAccount(userAccount)
                .studentPassword(oldPassword)
                .accountEnable(true).build();

        final Example<UserStudentInfo> example = getUserStudentBasicInfoExampleBySearchDto(searchDto);

        final UserStudentInfo studentInfo = studentRepository.findOne(example);

        if(studentInfo != null){

            studentInfo.setStudentPassword(newPassword);

            //更改密码
            studentRepository.saveAndFlush(studentInfo);

            return true;
        }

        return false;
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
            studentBasicInfo = studentRepository.saveAndFlush(studentBasicInfo);

            if(studentBasicInfo != null){

                UserStudentInfoBasicVo studentBasicInfoVo = new UserStudentInfoBasicVo();

                //将更改后的数据转载入视图对象
                EntityVoUtil.copyFieldsFromEntityToVo(studentBasicInfo,studentBasicInfoVo);

                return studentBasicInfoVo;
            }
        }

        return null;
    }

    @Override
    public Example<UserStudentInfo> getUserStudentBasicInfoExampleBySearchDto(UserStudentInfoSearchDto searchDto){

        final UserStudentInfoBuilder builder = UserStudentInfo.builder();

        ExampleMatcher exampleMatcher = ExampleMatcher.matching();

        if(searchDto.getStudentAccount() != null){
            builder.studentAccount(searchDto.getStudentAccount());
        }

        if(searchDto.getStudentPassword() != null){
            builder.studentPassword(searchDto.getStudentPassword());
        }

        if(searchDto.getTeacherId() != null){
            final UserTeacherInfo teacherInfo = UserTeacherInfo.builder().id(searchDto.getTeacherId()).build();
            builder.userTeacherInfo(teacherInfo);
        }

        //是否启用的账户
        builder.accountEnable(searchDto.isAccountEnable());

        final UserStudentInfo userStudentInfo = builder.build();

        //忽略没有设置值的字段
        exampleMatcher = exampleMatcher.withIgnoreNullValues();

        return Example.of(userStudentInfo,exampleMatcher);
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
    public boolean checkAgencyExist(Long majorInfo){

        return getAgencyInfo(majorInfo) != null;
    }

    @Override
    public MajorInfo getAgencyInfo(Long majorInfo){

        return majorInfoRepository.findOne(majorInfo);
    }

    @Override
    public boolean checkAvatarExist(Long userAvatarInfo){

        return getAgencyInfo(userAvatarInfo) != null;
    }

    @Override
    public UserAvatarInfo getAvatarInfo(Long userAvatarInfo){

        return avatarRepository.findOne(userAvatarInfo);
    }

    @Override
    public UserStudentInfo getUserStudentBasicInfoById(Long studentId){

        return studentRepository.findOne(studentId);
    }

    @Override
    public List<UserStudentInfo> getStudentsByTeacherId(Long teacherId){

        UserStudentInfoSearchDto studentInfoSearchDto = UserStudentInfoSearchDto.builder()
                .accountEnable(true)
                .teacherId(teacherId)
                .build();

        final Example<UserStudentInfo> example = getUserStudentBasicInfoExampleBySearchDto(studentInfoSearchDto);

        return studentRepository.findAll(example);
    }
}