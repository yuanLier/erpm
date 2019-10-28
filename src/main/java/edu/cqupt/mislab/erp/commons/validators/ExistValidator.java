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

/**
 * @author chuyunfei
 * @description 
 * @date 20:44 2019/5/3
 **/

@Slf4j
@Component
public class ExistValidator implements ConstraintValidator<Exist,Long> , ApplicationContextAware {

    private ApplicationContext applicationContext;

    private CrudRepository crudRepository;

    @Override
    public void initialize(Exist constraintAnnotation){

        final Class<? extends CrudRepository> repositoryClass = constraintAnnotation.repository();

        final CrudRepository bean = applicationContext.getBean(repositoryClass);

        //校验配置是否正确
        if(bean == null){

            throw new RuntimeException("ExistValidator 校验器所配置的Repository不正确");
        }

        this.crudRepository = bean;
    }

    @Override
    public boolean isValid(Long value,ConstraintValidatorContext context){

        log.info("验证：" + value + " " + crudRepository.getClass().getSimpleName());

        //如果这个字段是null，标识前端根本就没有传输，这个校验器不去校验null字段，需要额外的注解去校验
        if(value == null){

            return true;
        }

        //查询该主键数据是否存在
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

        log.info("初始化ExistValidator校验器：ApplicationContextAware");

        this.applicationContext = applicationContext;
    }
}
