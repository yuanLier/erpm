package edu.cqupt.mislab.erp.commons.aspect;

import edu.cqupt.mislab.erp.commons.response.ResponseUtil;
import edu.cqupt.mislab.erp.commons.response.ResponseVo;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
public class ControllerAdvance {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseVo<String> totalExceptionHandler(Exception exception){

        exception.printStackTrace();

        //参数格式错误异常默认处理方法
        if(exception instanceof MethodArgumentNotValidException){

            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) exception;

            final BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();

            final List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            StringBuilder stringBuilder = new StringBuilder();

            for(FieldError fieldError : fieldErrors){

                final String field = fieldError.getField();
                final Object rejectedValue = fieldError.getRejectedValue();
                final String defaultMessage = fieldError.getDefaultMessage();

                stringBuilder.append("格式错误字段：").append(field).append('\n');
                stringBuilder.append("你传输的字段值：").append(rejectedValue).append('\n');
                stringBuilder.append("需要的格式规则为：").append(defaultMessage).append('\n');
            }

            return ResponseUtil.<String>toFailResponseVo(HttpStatus.BAD_REQUEST,stringBuilder.toString());
        }

        return ResponseUtil.<String>toFailResponseVo(HttpStatus.INTERNAL_SERVER_ERROR,"服务器内部出现未知错误");
    }
}
