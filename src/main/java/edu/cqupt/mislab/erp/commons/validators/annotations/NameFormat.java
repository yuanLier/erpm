package edu.cqupt.mislab.erp.commons.validators.annotations;

import edu.cqupt.mislab.erp.commons.validators.NameFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author chuyunfei
 * @description 
 * @date 20:44 2019/5/3
 **/

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NameFormatValidator.class)
public @interface NameFormat {

    String message() default "数据不符合命名规范：变量命名规范";

    Class<?>[] groups() default {};

    Class<?extends Payload>[] payload() default{};
}
