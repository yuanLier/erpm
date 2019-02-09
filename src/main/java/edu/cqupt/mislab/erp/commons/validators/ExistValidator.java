package edu.cqupt.mislab.erp.commons.validators;

import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
@Component
public class ExistValidator implements ConstraintValidator<Exist,Long> , ApplicationContextAware {

    private ApplicationContext applicationContext;

    private CrudRepository crudRepository;

    @Override
    public void initialize(Exist constraintAnnotation){

        final Class<? extends CrudRepository> repositoryClass = constraintAnnotation.repository();

        this.crudRepository = applicationContext.getBean(repositoryClass);
    }

    @Override
    public boolean isValid(Long value,ConstraintValidatorContext context){

        log.info("验证：" + value + " " + crudRepository.getClass().getSimpleName());

        //如果这个字段是null，标识前端根本就没有传输，这个校验器不去校验null字段
        if(value == null){

            return true;
        }

        boolean exists = crudRepository.exists(value);

        if(exists){

            log.info("验证：" + value + " " + crudRepository.getClass().getSimpleName() + " 通过！");

            return true;
        }

        log.warn("验证：" + value + " " + crudRepository.getClass().getSimpleName() + " 不通过！");

        return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{

        log.info("初始化：ApplicationContextAware");

        this.applicationContext = applicationContext;
    }
}
