package edu.cqupt.mislab.erp.commons.validators;

import edu.cqupt.mislab.erp.commons.validators.annotations.DoubleMin;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * author： chuyunfei date：2019/3/6
 */
@Slf4j
public class DoubleMinValidator implements ConstraintValidator<DoubleMin,Double> {

    //需要校验的值
    private double aimValue;

    @Override
    public void initialize(DoubleMin constraintAnnotation){

        log.info("初始化：DoubleMinValidator");

        //获取配置的信息
        this.aimValue = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Double value,ConstraintValidatorContext context){

        log.info("DoubleMinValidator 开始校验：" + value);

        //判断当前数据是否符合要求

        return value - aimValue > 0;
    }
}
