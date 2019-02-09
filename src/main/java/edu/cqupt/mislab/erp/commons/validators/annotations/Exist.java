package edu.cqupt.mislab.erp.commons.validators.annotations;

import edu.cqupt.mislab.erp.commons.validators.ExistValidator;
import org.springframework.data.repository.CrudRepository;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 通过配置Repository来校验该主键所对应的数据是否存在，注意仅仅是主键校验
 */
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ExistValidator.class)
public @interface Exist {

    Class<? extends CrudRepository> repository();

    String message() default "该ID所对应的数据不存在";

    Class<?>[] groups() default {};

    Class<?extends Payload>[] payload() default{};

}
