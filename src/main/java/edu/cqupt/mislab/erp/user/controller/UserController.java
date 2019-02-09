package edu.cqupt.mislab.erp.user.controller;

import com.google.code.kaptcha.Producer;
import edu.cqupt.mislab.erp.commons.response.ResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.NameFormat;
import edu.cqupt.mislab.erp.user.constant.UserConstant;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static edu.cqupt.mislab.erp.commons.response.ResponseUtil.*;
import static edu.cqupt.mislab.erp.commons.util.CommonUtil.checkNameFormat;

/**
 * 用于抽取用户的公共模板方法
 * @param <V>：该用户的基本VO
 */
@Validated
public abstract class UserController<V> {

    @ApiOperation(value = "通过用户账户获取用户的基本信息",notes = "该账户必须处于启用状态才行，也就是说正在等待审核和未注册的账户将不行")
    @ApiImplicitParam(name = "userAccount",value = "用户账号",paramType = "query",required = true)
    @GetMapping("/basicInfo/get")
    public ResponseVo<V> getStudentBasicInfo(
            @NameFormat @RequestParam String userAccount
            ,HttpSession httpSession){

        //试图从Session里面获取缓存数据
        V userBasicInfoVo = (V) httpSession.getAttribute(UserConstant.USER_COMMON_INFO_SESSION_ATTR_NAME);

        //缓存击中
        if(userBasicInfoVo != null){

            //判断缓存中的数据是否是需要的数据
            if(isTheAccountIsRight(userAccount,userBasicInfoVo)){

                return toSuccessResponseVo(userBasicInfoVo);
            }else {

                //清空错误的缓存
                httpSession.removeAttribute(UserConstant.USER_COMMON_INFO_SESSION_ATTR_NAME);
            }
        }

        //缓存未击中，试图去数据库里面查询数据
        userBasicInfoVo = getUserBasicInfoVoFromDatabase(userAccount);

        //数据库里面有数据
        if(userBasicInfoVo != null){

            //将数据缓存在session里面
            httpSession.setAttribute(UserConstant.USER_COMMON_INFO_SESSION_ATTR_NAME,userBasicInfoVo);

            return toSuccessResponseVo(userBasicInfoVo);
        }else {

            //账户不存在
            return toFailResponseVo(HttpStatus.NOT_FOUND,"该账户不存在或待审核");
        }
    }

    /**
     * 判断传入的账户是否和缓存的账户是一致的
     * @param userAccount ：传入的账户
     * @param userBasicInfoVo ：查出来的账户
     * @return
     */
    public abstract boolean isTheAccountIsRight(String userAccount,V userBasicInfoVo);

    /**
     * 从数据库里面查取该账户的基本视图数据
     * @param userAccount ：账户
     * @return
     */
    public abstract V getUserBasicInfoVoFromDatabase(String userAccount);


    @ApiOperation("用户登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userAccount",value = "用户账号",paramType = "query",required = true),
            @ApiImplicitParam(name = "userPassword",value = "用户账号密码",paramType = "query",required = true),
            @ApiImplicitParam(name = "verificationCode",value = "验证码，如果需要的话",paramType = "query")
    })
    @PostMapping("/login")
    public ResponseVo<Integer> userLogin(
            @NameFormat @RequestParam String userAccount
            ,@NameFormat @RequestParam String userPassword,
            @RequestParam(required = false) String verificationCode,HttpSession httpSession){

        //移除登录状态，也就是说如果在登录状态进行登录却失败了将被登出
        httpSession.removeAttribute(UserConstant.USER_LOGIN_SUCCESS_SESSION_ATTR_TOKEN_NAME);

        //查看当前是第几次进行登录
        Integer warnTimes = (Integer) httpSession.getAttribute(UserConstant.USER_LOGIN_WARN_TIMES_SESSION_ATTR_NAME);

        if(warnTimes == null){
            //第一次登录，初始化
            httpSession.setAttribute(UserConstant.USER_LOGIN_WARN_TIMES_SESSION_ATTR_NAME,0);
        }else {

            //判断是否需要验证码
            if(warnTimes >= UserConstant.USER_MAX_WARN_TIMES_WITHOUT_VERIFICATION_CODE){

                //进行验证码校验，获取会话里面的验证码
                final String sessionVerificationCode = (String) httpSession.getAttribute(UserConstant.USER_LOGIN_VERIFICATION_CODE_ATTR_NAME);

                if(sessionVerificationCode == null){

                    return toFailResponseVo(HttpStatus.BAD_REQUEST,"验证码没有正确被前端加载，试着刷新一下再试");
                }

                //判断验证码是否正确
                if(!sessionVerificationCode.equalsIgnoreCase(verificationCode)){

                    return toFailResponseVo(HttpStatus.BAD_REQUEST,"验证码错误");
                }
            }
        }

        //验证码验证正确或者还不需要进行验证

        boolean right = checkUserAccountAndPassword(userAccount,userPassword);

        //如果校验成功
        if(right){

            //设置登陆成功标志位
            httpSession.setAttribute(UserConstant.USER_LOGIN_SUCCESS_SESSION_ATTR_TOKEN_NAME,true);

            //清空登录失败的记录
            httpSession.removeAttribute(UserConstant.USER_LOGIN_WARN_TIMES_SESSION_ATTR_NAME);

            return toSuccessResponseVoWithNoData();
        }else {

            //重新获取记录错误登陆的标志位
            warnTimes = (Integer) httpSession.getAttribute(UserConstant.USER_LOGIN_WARN_TIMES_SESSION_ATTR_NAME);

            //错误次数增加1
            httpSession.setAttribute(UserConstant.USER_LOGIN_WARN_TIMES_SESSION_ATTR_NAME,warnTimes + 1);

            //返回已经错误的次数
            return toResponseVo(HttpStatus.UNAUTHORIZED,"账户或密码错误，或者正在等待审核",warnTimes + 1);
        }
    }

    /**
     * 检验账户和密码是否存在且正确
     * @param userAccount：账户
     * @param userPassword：密码
     * @return
     */
    public  boolean checkUserAccountAndPassword(String userAccount,String userPassword){

        if(userAccount.startsWith(UserConstant.USER_STUDENT_ACCOUNT_PREFIX)){
            return checkStudentAccountAndPassword(userAccount,userPassword);
        }

        if(userAccount.startsWith(UserConstant.USER_TEACHER_ACCOUNT_PREFIX)){
            return checkTeacherAccountAndPassword(userAccount,userPassword);
        }

        if(userAccount.startsWith(UserConstant.USER_ADMIN_ACCOUNT_PREFIX)){
            return checkAdminAccountAndPassword(userAccount,userPassword);
        }

        return false;
    }

    /**
     * 管理员用户校验登录信息的接口
     */
    public boolean checkAdminAccountAndPassword(String userAccount,String userPassword){return false;};

    /**
     * 教师用户校验登录信息的接口
     */
    public boolean checkTeacherAccountAndPassword(String userAccount,String userPassword){return false;};

    /**
     * 学生用户登陆校验的接口
     */
    public boolean checkStudentAccountAndPassword(String userAccount,String userPassword){return false;};

    @Autowired
    private Producer verificationCodeProducer;

    @ApiOperation("获取验证码")
    @GetMapping("/verificationCode/get")
    public void getVerificationCode(HttpServletResponse response,HttpSession httpSession){

        //创建一个验证码
        final String verificationCodeText = verificationCodeProducer.createText();

        //创建一个验证码图片
        final BufferedImage verificationCodeImage = verificationCodeProducer.createImage(verificationCodeText);

        //设置响应头
        response.setDateHeader("Expires",0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        ServletOutputStream responseOutputStream = null;

        try{
            responseOutputStream = response.getOutputStream();

            //返回验证码图像信息
            ImageIO.write(verificationCodeImage,"jpg",responseOutputStream);

            //刷新数据
            response.flushBuffer();

            //将验证码放在会话里面
            httpSession.setAttribute(UserConstant.USER_LOGIN_VERIFICATION_CODE_ATTR_NAME,verificationCodeText);

        }catch(IOException e){

            e.printStackTrace();
        }
    }

    @ApiOperation("更改用户密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userAccount",value = "用户账号",paramType = "query",required = true),
            @ApiImplicitParam(name = "oldPassword",value = "原来的密码",paramType = "query",required = true),
            @ApiImplicitParam(name = "newPassword",value = "新密码",paramType = "query",required = true)
    })
    @PostMapping("/password/update")
    public ResponseVo<String> updateUserPassword(
            @NameFormat @RequestParam String userAccount
            ,@NameFormat @RequestParam String oldPassword
            ,@NameFormat @RequestParam String newPassword){

        if(resetUserPassword(userAccount,oldPassword,newPassword)){

            return toSuccessResponseVoWithNoData();

        }else {

            return toFailResponseVo(HttpStatus.UNAUTHORIZED,"密码不正确或账户不存在");
        }
    }

    /**
     * 重设账户的密码
     * @param userAccount：账户
     * @param oldPassword：旧密码
     * @param newPassword：新密码
     * @return
     */
    private boolean resetUserPassword(String userAccount,String oldPassword,String newPassword){

        if(userAccount.startsWith(UserConstant.USER_STUDENT_ACCOUNT_PREFIX)){
            return resetUserStudentPassword(userAccount,oldPassword,newPassword);
        }

        if(userAccount.startsWith(UserConstant.USER_TEACHER_ACCOUNT_PREFIX)){
            return resetUserTeacherPassword(userAccount,oldPassword,newPassword);
        }

        if(userAccount.startsWith(UserConstant.USER_ADMIN_ACCOUNT_PREFIX)){
            return resetUserAdminPassword(userAccount,oldPassword,newPassword);
        }

        return false;
    }

    /**
     * 更改管理员用户的密码
     */
    private boolean resetUserAdminPassword(String userAccount,String oldPassword,String newPassword){ return false; }

    /**
     * 更改教师用户的密码
     */
    private boolean resetUserTeacherPassword(String userAccount,String oldPassword,String newPassword){ return false; }

    /**
     * 更改学生用户的密码
     */
    protected boolean resetUserStudentPassword(String userAccount,String oldPassword,String newPassword){return false;}

    @ApiOperation("判断一个账户是否存在")
    @ApiImplicitParam(name = "userAccount",value = "用户账号",paramType = "query",required = true)
    @GetMapping("/basicInfo/exist")
    public ResponseVo<Boolean> checkUserAccountExist(
            @NameFormat @RequestParam String userAccount,HttpSession httpSession){

        //从数据库里面查取数据
        final V basicInfoVo = this.getUserBasicInfoVoFromDatabase(userAccount);

        //如果该账户不存在
        if(basicInfoVo == null){

            return toFailResponseVo(HttpStatus.NOT_FOUND,"该账户不存在");
        }

        //缓存该数据
        httpSession.setAttribute(UserConstant.USER_COMMON_INFO_SESSION_ATTR_NAME,basicInfoVo);

        //返回该用户数据存在
        return toSuccessResponseVoWithNoData();
    }
}