package edu.cqupt.mislab.erp.commons.validators;

import edu.cqupt.mislab.erp.commons.validators.annotations.EnterpriseStatusValid;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author： chuyunfei
 * @date：2019/3/6
 */
@Slf4j
@Component
public class EnterpriseStatusValidator implements ConstraintValidator<EnterpriseStatusValid,Long> {

    @Autowired private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;

    private EnterpriseStatusEnum enterpriseStatusEnum;

    @Override
    public void initialize(EnterpriseStatusValid constraintAnnotation){

        log.info("初始化：EnterpriseStatusValid");

        //获取配置的信息
        enterpriseStatusEnum = constraintAnnotation.enterpriseStatus();
    }

    @Override
    public boolean isValid(Long value,ConstraintValidatorContext context){

        log.info("EnterpriseStatusValidator 开始校验：" + value);

        //判断当前数据是否符合要求

        return enterpriseBasicInfoRepository.findByIdAndEnterpriseStatus(value, enterpriseStatusEnum) != null;
    }
}
