package edu.cqupt.mislab.erp.user.controller;

import com.google.code.kaptcha.Producer;
import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.response.WebResponseVo.ResponseStatus;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.user.constant.UserConstant;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.*;

/**
 * @author chuyunfei
 * @description 用于抽取用户的公共模板方法
 * @param <V>：该用户的基本VO
 * @date 22:43 2019/4/22
 **/

@Validated
public abstract class BaseUserController<V> {

//    @Autowired
//    PlatformTransactionManager manager;

    @ApiOperation(value = "通过用户Id获取用户的基本信息",notes = "1、该账户必须审核通过才可以请求到数据；2、如果这个userId没有注册过，将返回400")
    @GetMapping("/basicInfo/get")
    public WebResponseVo<V> getStudentBasicInfo(@Exist(repository = UserStudentRepository.class)
                                                    @RequestParam Long userId,
                                                HttpSession httpSession){

        //试图从Session里面获取缓存数据，由于有缓存这一步，所以在对用户数据进行修改后需要把这个缓存给去除掉
        V userBasicInfoVo = (V) httpSession.getAttribute(UserConstant.USER_COMMON_INFO_SESSION_ATTR_NAME);

        //缓存击中
        if(userBasicInfoVo != null){

            //判断缓存中的数据是否是需要的数据，看看两个Id是否一致
            if(isTheIdIsRight(userId,userBasicInfoVo)){

                //响应前端数据
                return toSuccessResponseVoWithData(userBasicInfoVo);
            }else {

                //缓存里面的数据不一致，清空错误的缓存
                httpSession.removeAttribute(UserConstant.USER_COMMON_INFO_SESSION_ATTR_NAME);
            }
        }

        //缓存未击中，试图去数据库里面查询数据
        userBasicInfoVo = getUserBasicInfoVoFromDatabaseById(userId);

        //数据库里面有数据
        if(userBasicInfoVo != null){

            //将数据缓存在session里面
            httpSession.setAttribute(UserConstant.USER_COMMON_INFO_SESSION_ATTR_NAME, userBasicInfoVo);

            //响应前端数据
            return toSuccessResponseVoWithData(userBasicInfoVo);
        }else {

            //账户不存在
            return toFailResponseVoWithMessage(ResponseStatus.NOT_FOUND,"该账户不存在或待审核");
        }
    }

    /**
     * 判断传入的账户是否和缓存的账户是一致的
     * @param userId ：传入的账户
     * @param userBasicInfoVo ：查出来的账户
     * @return
     */
    public abstract boolean isTheIdIsRight(Long userId ,V userBasicInfoVo);

    /**
     * 从数据库里面查取该账户的基本视图数据
     * @param userId ：账户
     * @return
     */
    public abstract V getUserBasicInfoVoFromDatabaseById(Long userId);

    /**
     * 从数据库里面查取该账户的基本视图数据
     * @param userAccount ：账户
     * @return
     */
    public abstract V getUserBasicInfoVoFromDatabaseByAccount(String userAccount);


    @ApiOperation(value = "用户登录接口",notes = "1、只有审核通过的账户可以登录成功；2、如果登录成功，返回的data值为该账户的userId，如果不成功这个值是登录出错的次数")
    @PostMapping("/login")
    public WebResponseVo<Long> userLogin(@RequestParam String userAccount,
                                         @RequestParam String userPassword,
                                         @RequestParam(required = false) String verificationCode,
                                         HttpSession httpSession){

        //移除登录状态，也就是说如果在登录状态进行登录却失败了将被登出登录状态
        httpSession.removeAttribute(UserConstant.USER_LOGIN_SUCCESS_SESSION_ATTR_TOKEN_NAME);

        //查看当前是第几次进行登录
        Long warnTimes = (Long) httpSession.getAttribute(UserConstant.USER_LOGIN_WARN_TIMES_SESSION_ATTR_NAME);

        if(warnTimes == null){
            //第一次登录，初始化
            httpSession.setAttribute(UserConstant.USER_LOGIN_WARN_TIMES_SESSION_ATTR_NAME,0L);
        }else {

            //判断是否需要验证码
            if(warnTimes >= UserConstant.USER_MAX_WARN_TIMES_WITHOUT_VERIFICATION_CODE){

                //进行验证码校验，获取会话里面的验证码
                final String sessionVerificationCode = (String) httpSession.getAttribute(UserConstant.USER_LOGIN_VERIFICATION_CODE_ATTR_NAME);

                if(sessionVerificationCode == null){

                    return toFailResponseVoWithMessage(ResponseStatus.BAD_REQUEST,"验证码没有正确被前端加载，试着刷新一下验证码再试");
                }

                //判断验证码是否正确，忽略大小写
                if(!sessionVerificationCode.equalsIgnoreCase(verificationCode)){

                    return toFailResponseVoWithMessage(ResponseStatus.BAD_REQUEST,"验证码错误");
                }
            }
        }

        //验证码验证正确或者还不需要进行验证

        Long userId = checkUserAccountAndPassword(userAccount,userPassword);

        //如果校验成功
        if(userId != null){

            //设置登陆成功标志位
            httpSession.setAttribute(UserConstant.USER_LOGIN_SUCCESS_SESSION_ATTR_TOKEN_NAME,true);

            //清空登录失败的记录
            httpSession.removeAttribute(UserConstant.USER_LOGIN_WARN_TIMES_SESSION_ATTR_NAME);

            //响应前端数据
            return toSuccessResponseVoWithData(userId);
        }else {

            //重新获取记录错误登陆的标志位
            warnTimes = (Long) httpSession.getAttribute(UserConstant.USER_LOGIN_WARN_TIMES_SESSION_ATTR_NAME);

            //错误次数增加1
            httpSession.setAttribute(UserConstant.USER_LOGIN_WARN_TIMES_SESSION_ATTR_NAME,warnTimes + 1);

            //返回已经错误的次数
            return toResponseVo(ResponseStatus.BAD_REQUEST,"账户或密码错误，或者正在等待审核",warnTimes + 1);
        }
    }

    /**
     * 检验账户和密码是否存在且正确
     * @param userAccount ：账户
     * @param userPassword ：密码
     * @return
     */
    public abstract Long checkUserAccountAndPassword(String userAccount,String userPassword);


    @Autowired
    private Producer verificationCodeProducer;

    @ApiOperation(value = "获取验证码",notes = "这个接口返回值是二进制数据，需要复制这个链接到浏览器其它窗口才可以进行测试")
    @GetMapping("/verificationCode/get")
    public void getVerificationCode(HttpServletResponse response,HttpSession httpSession){

        //创建一个验证码
        final String verificationCodeText = verificationCodeProducer.createText();

        //创建一个验证码图片 todo 换个验证码？
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
    @PostMapping("/password/update")
    public WebResponseVo<String> updateUserPassword(@RequestParam Long userId,@RequestParam String oldPassword,@RequestParam String newPassword){

        Boolean isSuccess = resetUserPassword(userId,oldPassword,newPassword);

        if(isSuccess == null) {
            return toFailResponseVoWithMessage(ResponseStatus.INTERNAL_SERVER_ERROR, "密码更新失败！请联系开发人员");
        }

        if(isSuccess){

            return toSuccessResponseVoWithNoData();

        }else {

            return toFailResponseVoWithMessage(ResponseStatus.BAD_REQUEST,"密码不正确或账户不存在");
        }
    }

    /**
     * 重设账户的密码
     * @param userId ：账户
     * @param oldPassword ：旧密码
     * @param newPassword ：新密码
     * @return
     */
    public abstract Boolean resetUserPassword(Long userId,String oldPassword,String newPassword);


    @ApiOperation(value = "判断一个账户是否存在",notes = "1、204标识存在，404标识不存在")
    @GetMapping("/basicInfo/exist")
    public WebResponseVo<Object> checkUserAccountExist(@RequestParam String userAccount,HttpSession httpSession){

        //从数据库里面查取数据
        final V basicInfoVo = this.getUserBasicInfoVoFromDatabaseByAccount(userAccount);

        //如果该账户不存在
        if(basicInfoVo == null){

            return toFailResponseVoWithMessage(ResponseStatus.NOT_FOUND,"该账户不存在");
        }

        //缓存该数据
        httpSession.setAttribute(UserConstant.USER_COMMON_INFO_SESSION_ATTR_NAME,basicInfoVo);

        //返回该用户数据存在
        return toSuccessResponseVoWithNoData();
    }
}