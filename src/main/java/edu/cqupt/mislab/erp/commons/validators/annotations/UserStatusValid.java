package edu.cqupt.mislab.erp.commons.validators.annotations;

import edu.cqupt.mislab.erp.commons.validators.UserStatusValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author: chuyunfei
 * @Date: 2019/3/6 18:52
 * @Description: 用于校验用户的状态信息
 **/

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UserStatusValidator.class)
public @interface UserStatusValid {

    /**
     * 判断用户是否启用
     * @return
     */
    boolean isEnable();

    String message() default "该用户没有处于指定的状态";

    Class<?>[] groups() default {};

    Class<?extends Payload>[] payload() default{};
}
