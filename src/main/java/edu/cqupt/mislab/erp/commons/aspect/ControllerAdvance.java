package edu.cqupt.mislab.erp.commons.aspect;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toFailResponseVoWithMessage;

/**
 * @author chuyunfei
 * @description 统一异常处理
 *      1、未知异常使用统一拦截处理器
 *      2、具体异常使用具体异常的拦截器，写清楚这个异常会出现的位置和什么情况下回抛出这个异常
 * @date 22:25 2019/4/25
 **/

@ControllerAdvice
public class ControllerAdvance {

    /**
     * @author chuyunfei
     * @description 这个异常出现在JSR自定义参数校验：validation
     * @date 19:05 2019/4/29
     **/
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public WebResponseVo<Object> constraintViolationExceptionHandler(ConstraintViolationException exception){

        //打印堆栈信息，每个方法必须要打印，不然就是耍流氓
        exception.printStackTrace();

        //获取校验的错误提示信息
        final Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        StringBuilder stringBuilder = new StringBuilder();

        //拼接全部的错误提示信息
        for(ConstraintViolation<?> violation : violations){

            stringBuilder.append("校验错误提示：").append(violation.getMessage()).append(';');
        }

        return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "参数校验异常 ：" + stringBuilder.toString());
    }

    /**
     * @author chuyunfei
     * @description 这个异常出现在参数校验：@Valid @RequestBody ，如果这个对象里面的属性不是符合要求的时候就会包这个异常
     * @date 19:04 2019/4/29
     **/
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public WebResponseVo<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception){

        //打印堆栈信息，每个方法必须要打印，不然就是耍流氓
        exception.printStackTrace();

        //这是一个参数校验异常，后端会将那些参数校验失败的字段的原因返回给前端
        final BindingResult bindingResult = exception.getBindingResult();

        //如果参数绑定出现问题，将错误的信息返回前端
        if(bindingResult.hasFieldErrors()){

            StringBuilder stringBuilder = new StringBuilder();

            final List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            if(fieldErrors != null && fieldErrors.size() > 0){

                for(FieldError fieldError : fieldErrors){

                    //获取校验错误的字段
                    final String fieldName = fieldError.getField();
                    //前端传过来的哪一个值是不符合要求的
                    final Object rejectedValue = fieldError.getRejectedValue();
                    //需要的格式是什么
                    final String infoMessage = fieldError.getDefaultMessage();

                    stringBuilder.append("{").append("错误字段：").append(fieldName).append(";");
                    stringBuilder.append("前端数据：").append(rejectedValue).append(";");
                    stringBuilder.append("该字段需要的格式：").append(infoMessage).append(";").append("}");
                }
            }

            //返回参数校验错误的信息
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST,stringBuilder.toString());

        }else {

            //其他异常将无法处理
            return this.exceptionHandler(exception);
        }
    }

    /**
     * @author yuanyiwen
     * @description 捕获jpa自带的delete中可能出现的异常
     * @date 19:05 2019/4/29
     **/
    @ResponseBody
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public WebResponseVo<Object> emptyResultDataAccessExceptionHandler(EmptyResultDataAccessException exception) {

        exception.printStackTrace();

        return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.INTERNAL_SERVER_ERROR, "数据删除异常！");
    }

    
    /**
     * @author yuanyiwen
     * @description 余额不足导致的用户操作异常
     * @date 18:19 2019/7/13
     **/
    @ResponseBody
    @ExceptionHandler(InsufficientBalanceException.class)
    public WebResponseVo<Object> insufficientBalanceExceptionHandler(InsufficientBalanceException exception) {

        exception.printStackTrace();

        return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "操作失败！请检查账户余额");
    }

    
    /**
     * @author yuanyiwen
     * @description 数据修改异常：管理员试图修改已被关闭的历史数据
     * @date 13:10 2019/10/15
     **/
    @ResponseBody
    @ExceptionHandler(BadModificationException.class)
    public WebResponseVo<Object> badModificationException(BadModificationException exception) {

        exception.printStackTrace();

        return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "该信息已被关闭，无法被修改，请检查您的操作");
    }
    
    /**
     * @author yuanyiwen
     * @description 七牛云文件存储（上传或删除）异常；提示一下就好 毕竟它本质上是一个检查型异常
     * @date 12:41 2019/10/20
     **/
    @ResponseBody
    @ExceptionHandler(FileOperateException.class)
    public WebResponseVo<Object> fileUploadException(FileOperateException exception) {

        // todo 将部分堆栈错误改为打log
        exception.printStackTrace();

        String message = (exception.getMessage() == null) ? "文件存储服务不可用" : exception.getMessage();

        return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.SERVICE_UNAVAILABLE, message);
    }


    /**
     * @author yuanyiwen
     * @description Excel数据导入异常
     * @date 12:41 2019/10/20
     **/
    @ResponseBody
    @ExceptionHandler(ExcelOperationException.class)
    public WebResponseVo<Object> fileUploadException(ExcelOperationException exception) {

        // todo 将部分堆栈错误改为打log
        exception.printStackTrace();

        String message = (exception.getMessage() == null) ? "数据导入服务不可用" : exception.getMessage();

        return toFailResponseVoWithMessage(exception.getResponseStatus(), message);
    }


    /**
     * 企业状态异常
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(EnterpriseStatusException.class)
    public WebResponseVo<Object> fileUploadException(EnterpriseStatusException exception) {

        exception.printStackTrace();

        String message = (exception.getMessage() == null) ? "操作失败！企业未处于指定状态！" : exception.getMessage();

        return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, message);
    }

    /**
     * @author chuyunfei
     * @description 无可奈何的不知名异常就是使用这个处理器来进行处理，最后一道防线
     * @date 19:05 2019/4/29
     **/
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public WebResponseVo<Object> exceptionHandler(Exception exception){

        //打印堆栈信息，每个方法必须要打印，不然就是耍流氓
        exception.printStackTrace();

        //除了直接响应，我实在是没有办法啊
        return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.INTERNAL_SERVER_ERROR, "未知异常！请联系管理员");
    }
}
