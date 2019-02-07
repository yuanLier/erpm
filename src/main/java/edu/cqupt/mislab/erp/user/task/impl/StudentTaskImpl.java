package edu.cqupt.mislab.erp.user.task.impl;

import edu.cqupt.mislab.erp.commons.response.ResponseVo;
import edu.cqupt.mislab.erp.user.dao.MajorInfoRepository;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import edu.cqupt.mislab.erp.user.model.dto.UserStudentInfoRegisterDto;
import edu.cqupt.mislab.erp.user.model.dto.UserStudentInfoSearchDto;
import edu.cqupt.mislab.erp.user.model.entity.*;
import edu.cqupt.mislab.erp.user.model.vo.UserStudentInfoBasicVo;
import edu.cqupt.mislab.erp.user.service.StudentService;
import edu.cqupt.mislab.erp.user.task.StudentTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static edu.cqupt.mislab.erp.commons.response.ResponseUtil.*;
import static edu.cqupt.mislab.erp.commons.util.BeanCopyUtil.copyPropertiesSimple;

@Service
public class StudentTaskImpl implements StudentTask {

    @Autowired
    private UserStudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private MajorInfoRepository majorInfoRepository;

    @Override
    public ResponseVo<String> userStudentRegister(UserStudentInfoRegisterDto registerDto){

        //是否该账号已经被启用
        final UserStudentInfoBasicVo basicInfoVo = studentService.getStudentBasicInfoByAccount(registerDto.getStudentAccount());

        if(basicInfoVo != null){

            return toFailResponseVo(HttpStatus.BAD_REQUEST,"该账户已经被注册且正在被使用，请不要重复注册！");
        }

        //判断账号是否正在等待审核
        final UserStudentInfoSearchDto searchDto = UserStudentInfoSearchDto
                .builder()
                .studentAccount(registerDto.getStudentAccount())
                .accountEnable(false)
                .build();

        final Example<UserStudentInfo> example = studentService.getUserStudentBasicInfoExampleBySearchDto(searchDto);

        final UserStudentInfo basicInfo = studentRepository.findOne(example);

        if(basicInfo != null){

            return toFailResponseVo(HttpStatus.BAD_REQUEST,"该账户正在等待审核，请耐心等待老师审核！");
        }

        //进行账号注册
        UserStudentInfo studentInfo = new UserStudentInfo();

        copyPropertiesSimple(registerDto,studentInfo);

        //默认不启用该账户
        studentInfo.setAccountEnable(false);
        //默认性别为男
        studentInfo.setGender(UserGender.Man);
        //设置专业信息
        studentInfo.setMajorInfo(studentService.getAgencyInfo(registerDto.getMajorInfoId()));

        //存储并立即持久化到数据库
        studentInfo = studentRepository.saveAndFlush(studentInfo);

        //存储成功
        if(studentInfo.getId() != null){

            return toSuccessResponseVo("注册成功，请耐心等待老师审核！");
        }

        return toFailResponseVo(HttpStatus.INTERNAL_SERVER_ERROR,"注册失败");
    }

}
