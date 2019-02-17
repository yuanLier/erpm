package edu.cqupt.mislab.erp.commons.aspect;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.*;

/**
 * ControllerAdvance：用于对Controller层的非业务异常进行处理
 * 1、未知异常使用统一拦截处理器
 * 2、具体异常使用具体异常的拦截器，写清楚这个异常会出现的位置和什么情况下回抛出这个异常
 */
@ControllerAdvice
public class ControllerAdvance {

    /**
     * 这个异常出现在JSR自定义参数校验：validation
     */
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

        return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST,stringBuilder.toString());
    }

    /**
     * 这个异常出现在参数校验：@Valid @RequestBody ，如果这个对象里面的属性不是符合要求的时候就会包这个异常
     */
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
     * 无可奈何的不知名异常就是使用这个处理器来进行处理，最后一道防线
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public WebResponseVo<Object> exceptionHandler(Exception exception){

        //打印堆栈信息，每个方法必须要打印，不然就是耍流氓
        exception.printStackTrace();

        //除了直接响应，我实在是没有办法啊
        return toFailResponseVoWithNoMessage(WebResponseVo.ResponseStatus.INTERNAL_SERVER_ERROR);
    }
}
