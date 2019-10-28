package edu.cqupt.mislab.erp.commons.validators;

import edu.cqupt.mislab.erp.commons.constant.CommonConstant;
import edu.cqupt.mislab.erp.commons.validators.annotations.NameFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author chuyunfei
 * @description 
 * @date 20:44 2019/5/3
 **/

@Slf4j
@Component
public class NameFormatValidator implements ConstraintValidator<NameFormat,String> {

    @Override
    public void initialize(NameFormat constraintAnnotation){

        log.info("初始化：NameFormatValidator");
    }

    @Override
    public boolean isValid(String value,ConstraintValidatorContext context){

        //不校验null值，这些值交由其他校验器去校验
        if(value == null){

            return true;
        }

        log.info("验证：" + value + " NameFormatValidator");

        //不能为null
        if(value.length() == 0){
            return false;
        }

        //是否以数字开头
        if(CommonConstant.DIGITS.contains(value.subSequence(0,1))){
            return false;
        }

        //是否只包含字母、数字、下划线
        return StringUtils.containsOnly(value,CommonConstant.DIGITS + CommonConstant.LOWER_CHAR + CommonConstant.UPPER_CHAR + CommonConstant.UNDER_LINE);
    }
}
