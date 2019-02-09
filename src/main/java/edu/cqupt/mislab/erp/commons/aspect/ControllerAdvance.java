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

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

        //校验错误默认处理流程
        if(exception instanceof ConstraintViolationException){

            ConstraintViolationException violationException = (ConstraintViolationException) exception;

            final Set<ConstraintViolation<?>> violations = violationException.getConstraintViolations();

            StringBuilder stringBuilder = new StringBuilder();

            final Iterator<ConstraintViolation<?>> iterator = violations.iterator();

            while(iterator.hasNext()){

                final ConstraintViolation<?> violation = iterator.next();

                stringBuilder.append("校验错误提示：").append(violation.getMessage()).append(';');
            }

            return ResponseUtil.<String>toFailResponseVo(HttpStatus.BAD_REQUEST,stringBuilder.toString());
        }

        return ResponseUtil.<String>toFailResponseVo(HttpStatus.INTERNAL_SERVER_ERROR,"服务器内部出现未知错误");
    }
}
