package edu.cqupt.mislab.erp.commons.validators.annotations;

import edu.cqupt.mislab.erp.commons.validators.DoubleMaxValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author yuanyiwen
 * @create 2019-08-10 18:38
 * @description
 */
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DoubleMaxValidator.class)
public @interface DoubleMax {

    /**
     * 用于支持对Double数据类型的最大值校验
     * @return
     */
    double value();

    String message() default "数值范围不正确";

    Class<?>[] groups() default {};

    Class<?extends Payload>[] payload() default{};
}
