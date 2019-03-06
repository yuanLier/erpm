package edu.cqupt.mislab.erp.commons.validators;

import edu.cqupt.mislab.erp.commons.validators.annotations.UserStatusValid;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * author： chuyunfei date：2019/3/6
 */
@Slf4j
@Component
public class UserStatusValidator implements ConstraintValidator<UserStatusValid,Long> {

    @Autowired private UserStudentRepository userStudentRepository;

    //是否启用
    private boolean enable = false;

    @Override
    public void initialize(UserStatusValid constraintAnnotation){

        log.info("初始化：UserStatusValidator");

        //获取配置的信息
        enable = constraintAnnotation.isEnable();
    }

    @Override
    public boolean isValid(Long value,ConstraintValidatorContext context){

        log.info("UserStatusValidator 开始校验：" + value);

        //判断当前数据是否符合要求
        return userStudentRepository.findByIdAndAccountEnable(value,enable) != null;
    }
}
