package edu.cqupt.mislab.erp.commons.validators;

import edu.cqupt.mislab.erp.commons.validators.annotations.DoubleMax;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author yuanyiwen
 * @create 2019-08-10 18:39
 * @description
 */
@Slf4j
public class DoubleMaxValidator implements ConstraintValidator<DoubleMax,Double> {

    /**
     * 需要校验的值
     */
    private double aimValue;

    @Override
    public void initialize(DoubleMax constraintAnnotation){

        log.info("初始化：DoubleMaxValidator");

        //获取配置的信息
        this.aimValue = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Double value,ConstraintValidatorContext context){

        log.info("DoubleMaxValidator 开始校验：" + value);

        //判断当前数据是否符合要求

        // 允许包含
        return value - aimValue <= 0;
    }
}