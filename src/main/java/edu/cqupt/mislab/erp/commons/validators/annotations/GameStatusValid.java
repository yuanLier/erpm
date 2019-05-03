package edu.cqupt.mislab.erp.commons.validators.annotations;

import edu.cqupt.mislab.erp.commons.validators.GameStatusValidator;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameStatusEnum;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author: chuyunfei
 * @Date: 2019/3/6 19:23
 * @Description: 用于校验比赛是否处于某种状态
 **/

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = GameStatusValidator.class)
public @interface GameStatusValid {


    /**
     * 需要该比赛处于那种状态
     * @return
     */
    GameStatusEnum requireStatus();

    String message() default "该比赛没有处于指定的状态";

    Class<?>[] groups() default {};

    Class<?extends Payload>[] payload() default{};
}
