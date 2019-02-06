package edu.cqupt.mislab.erp.user.controller;

import edu.cqupt.mislab.erp.commons.response.ResponseVo;
import edu.cqupt.mislab.erp.user.constant.UserConstant;
import edu.cqupt.mislab.erp.user.model.dto.UserStudentInfoRegisterDto;
import edu.cqupt.mislab.erp.user.model.dto.UserStudentInfoUpdateDto;
import edu.cqupt.mislab.erp.user.model.vo.UserStudentInfoBasicVo;
import edu.cqupt.mislab.erp.user.service.StudentService;
import edu.cqupt.mislab.erp.user.task.StudentTask;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.List;
import java.util.Map;

import static edu.cqupt.mislab.erp.commons.response.ResponseUtil.toFailResponseVo;
import static edu.cqupt.mislab.erp.commons.response.ResponseUtil.toSuccessResponseVo;

@Api
@RestController
@RequestMapping("/user/student")
public class StudentController extends UserController<UserStudentInfoBasicVo>{

    @Autowired
    private StudentTask studentTask;
    @Autowired
    private StudentService studentService;

    @ApiOperation(value = "学生账户注册",notes = "如果该账号既没有被审核通过使用也没有正在等待审核——》注册成功并等待审核")
    @PostMapping("/register")
    public ResponseVo<String> userStudentRegister(@Valid @RequestBody UserStudentInfoRegisterDto registerDto){

        //校验重复密码是否正确
        if(!registerDto.getStudentPassword().equals(registerDto.getRePassword())){

            return toFailResponseVo(HttpStatus.BAD_REQUEST,"重复密码错误");
        }

        return studentTask.userStudentRegister(registerDto);
    }

    @ApiOperation(value = "修改学生账户的基本信息",notes = "需要修改那个信息就传那个信息，如果要覆盖就传空字符串而不是null，只有处于启用状态的账号才可以被修改")
    @PostMapping("/basicInfo/update")
    public ResponseVo<UserStudentInfoBasicVo> updateStudentBasicInfo(@Valid @RequestBody UserStudentInfoUpdateDto updateDto,HttpSession httpSession){

        UserStudentInfoBasicVo studentBasicInfoVo = studentService.updateStudentBasicInfo(updateDto);

        if(studentBasicInfoVo == null){

            return toFailResponseVo(HttpStatus.NOT_FOUND,"该账户不存在或正在等待审核");
        }

        //将数据缓存
        httpSession.setAttribute(UserConstant.USER_COMMON_INFO_SESSION_ATTR_NAME,studentBasicInfoVo);

        return toSuccessResponseVo(studentBasicInfoVo);
    }

    @ApiOperation("获取全部的头像位置信息，注意这个接口前端显示有问题，请直接 try it out!")
    @GetMapping("/avatar/get")
    public ResponseVo<Object> getUserAvatarInfos(HttpSession httpSession){

        return toSuccessResponseVo(studentService.getUserAvatarInfos());
    }

    @ApiOperation("获取学院专业信息")
    @GetMapping("/agency/get")
    public ResponseVo<Map<String,List<String>>> getAgencyInfos(){

        return toSuccessResponseVo(studentTask.getAgencyInfos());
    }

    @Override
    public boolean isTheAccountIsRight(String userAccount,UserStudentInfoBasicVo userBasicInfoVo){

        return userAccount.equals(userBasicInfoVo.getStudentCount());
    }

    @Override
    public UserStudentInfoBasicVo getUserBasicInfoVoFromDatabase(String userAccount){

        return studentService.getStudentBasicInfoByAccount(userAccount);
    }

    @Override
    public boolean checkStudentAccountAndPassword(String userAccount,String userPassword){

        return studentService.checkStudentAccountAndPassword(userAccount,userPassword);
    }

    @Override
    protected boolean resetUserStudentPassword(String userAccount,String oldPassword,String newPassword){

        return studentService.resetUserStudentPassword(userAccount,oldPassword,newPassword);
    }
}
