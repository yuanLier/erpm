package edu.cqupt.mislab.erp.commons.validators.annotations;

import edu.cqupt.mislab.erp.commons.validators.EnterpriseStatusValidator;
import edu.cqupt.mislab.erp.commons.validators.UserStatusValidator;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseStatus;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EnterpriseStatusValidator.class)
public @interface EnterpriseStatusValid {

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/6 18:52
     * @Description: 用于校验企业的状态信息
     **/
    EnterpriseStatus enterpriseStatus();//判断企业是否处于某种状态

    String message() default "该企业没有处于指定的状态";

    Class<?>[] groups() default {};

    Class<?extends Payload>[] payload() default{};
}