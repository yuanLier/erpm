package edu.cqupt.mislab.erp.user.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.user.constant.UserConstant;
import edu.cqupt.mislab.erp.user.model.dto.UserStudentInfoRegisterDto;
import edu.cqupt.mislab.erp.user.model.dto.UserStudentInfoUpdateDto;
import edu.cqupt.mislab.erp.user.model.entity.MajorBasicInfo;
import edu.cqupt.mislab.erp.user.model.vo.UserStudentInfoBasicVo;
import edu.cqupt.mislab.erp.user.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toFailResponseVoWithMessage;
import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithData;

/**
 * @author chuyunfei
 * @description
 * @date 14:39 2019/4/23
 **/

@Api
@Validated
@CrossOrigin
@RestController
@RequestMapping("/user/student")
public class StudentController extends BaseUserController<UserStudentInfoBasicVo> {

    @Autowired
    private StudentService studentService;

    @ApiOperation(value = "学生账户注册",notes = "如果该账号既没有被审核通过使用也没有正在等待审核——>注册成功并等待审核")
    @PostMapping("/register")
    public WebResponseVo<String> userStudentRegister(@Valid @RequestBody UserStudentInfoRegisterDto registerDto){

        //校验重复密码是否正确
        if(!registerDto.getStudentPassword().equals(registerDto.getRePassword())){

            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST,"两次输入不一致！");
        }

        //进行注册
        return studentService.userStudentRegister(registerDto);
    }

    @ApiOperation(value = "修改学生账户的基本信息",notes = "需要修改那个信息就传那个信息，如果要覆盖就传空字符串而不是null，只有处于启用状态的账号才可以被修改")
    @PostMapping("/basicInfo/update")
    public WebResponseVo<UserStudentInfoBasicVo> updateStudentBasicInfo(@Valid @RequestBody UserStudentInfoUpdateDto updateDto,HttpSession httpSession){

        // todo 异常处理
        UserStudentInfoBasicVo studentBasicInfoVo = studentService.updateStudentBasicInfo(updateDto);

        if(studentBasicInfoVo == null){

            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND,"该账户不存在或正在等待审核");
        }

        //缓存数据
        httpSession.setAttribute(UserConstant.USER_COMMON_INFO_SESSION_ATTR_NAME,studentBasicInfoVo);

        return toSuccessResponseVoWithData(studentBasicInfoVo);
    }

    @ApiOperation(value = "获取全部的头像位置信息",notes = "注意这个接口前端显示有问题，请直接 try it out!")
    @GetMapping("/avatar/get")
    public WebResponseVo<Object> getUserAvatarInfos(){

        return toSuccessResponseVoWithData(studentService.getUserAvatarInfos());
    }

    @ApiOperation("获取学院专业信息")
    @GetMapping("/agency/get")
    public WebResponseVo<List<MajorBasicInfo>> getAgencyInfos(){

        return toSuccessResponseVoWithData(studentService.getAgencyInfos());
    }

    @Override
    public boolean isTheIdIsRight(Long userId,UserStudentInfoBasicVo userBasicInfoVo){

        //userId绝对非null
        return userId.equals(userBasicInfoVo.getId());
    }

    @Override
    public UserStudentInfoBasicVo getUserBasicInfoVoFromDatabaseById(Long userId){

        return studentService.getStudentBasicInfoByUserId(userId);
    }

    @Override
    public UserStudentInfoBasicVo getUserBasicInfoVoFromDatabaseByAccount(String userAccount){

        return studentService.getStudentBasicInfoByAccount(userAccount);
    }

    @Override
    public Long checkUserAccountAndPassword(String userAccount,String userPassword){
        return studentService.checkStudentAccountAndPassword(userAccount,userPassword);
    }

    @Override
    public Boolean resetUserPassword(Long userId,String oldPassword,String newPassword){
        return studentService.resetUserStudentPassword(userId,oldPassword,newPassword);
    }
}
