package edu.cqupt.mislab.erp.commons.validators;

import edu.cqupt.mislab.erp.commons.validators.annotations.DoubleMin;
import edu.cqupt.mislab.erp.commons.validators.annotations.EnterpriseStatusValid;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * author： chuyunfei date：2019/3/6
 */
@Slf4j
public class DoubleMinValidator implements ConstraintValidator<DoubleMin,Double> {

    //校验的误差
    private double deviation = Math.pow(10,-2);

    //需要校验的值
    private double value;

    @Override
    public void initialize(DoubleMin constraintAnnotation){

        log.info("初始化：DoubleMinValidator");

        //获取配置的信息
        this.value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Double value,ConstraintValidatorContext context){

        log.info("DoubleMinValidator 开始校验：" + value);

        //判断当前数据是否符合要求

        return Math.abs(value - deviation) > 0;
    }
}
