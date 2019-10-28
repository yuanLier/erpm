package edu.cqupt.mislab.erp.game.compete.basic;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Comment {

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/4 20:10
     * @Description: 用于注解Entity的字段解释
     **/
    String comment();
}
