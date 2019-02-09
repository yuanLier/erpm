package edu.cqupt.mislab.erp.commons.validators.annotations;

import edu.cqupt.mislab.erp.commons.validators.NameFormatValidator;
import org.springframework.data.repository.CrudRepository;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NameFormatValidator.class)
public @interface NameFormat {

    String message() default "数据不符合命名规范";

    Class<?>[] groups() default {};

    Class<?extends Payload>[] payload() default{};
}
