package edu.cqupt.mislab.erp.commons.validators.annotations;

import edu.cqupt.mislab.erp.commons.validators.GameStatusValidator;
import edu.cqupt.mislab.erp.commons.validators.UserStatusValidator;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameStatus;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = GameStatusValidator.class)
public @interface GameStatusValid {

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/6 19:23
     * @Description: 用于校验比赛是否处于某种状态
     **/

    GameStatus requireStatus();//需要改比赛处于那种状态

    String message() default "该比赛没有处于指定的状态";

    Class<?>[] groups() default {};

    Class<?extends Payload>[] payload() default{};
}
