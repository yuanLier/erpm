package edu.cqupt.mislab.erp.commons.validators.annotations;

import edu.cqupt.mislab.erp.commons.validators.DoubleMinValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author: chuyunfei
 * @Date: 2019/3/8 12:20
 * @Description: 用于校验Double数据类型
 **/

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DoubleMinValidator.class)
public @interface DoubleMin {


    /**
     * 用于支持对Double数据类型的最小值校验
     * @return
     */
    double value();

    String message() default "数值范围不正确";

    Class<?>[] groups() default {};

    Class<?extends Payload>[] payload() default{};
}
