package edu.cqupt.mislab.erp.commons.validators;

import edu.cqupt.mislab.erp.commons.validators.annotations.GameStatusValid;
import edu.cqupt.mislab.erp.commons.validators.annotations.UserStatusValid;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * author： chuyunfei date：2019/3/6
 */
@Slf4j
@Component
public class GameStatusValidator implements ConstraintValidator<GameStatusValid,Long> {

    @Autowired private GameBasicInfoRepository gameBasicInfoRepository;

    //需要比赛处于该状态才可以校验通过
    private GameStatus requireGameStatus;

    @Override
    public void initialize(GameStatusValid constraintAnnotation){

        log.info("初始化：GameStatusValidator");

        //获取配置的信息
        requireGameStatus = constraintAnnotation.requireStatus();
    }

    @Override
    public boolean isValid(Long value,ConstraintValidatorContext context){

        log.info("GameStatusValidator 校验 " + value);

        //判断是否该比赛处于该状态
        return gameBasicInfoRepository.findByIdAndGameStatus(value,requireGameStatus) != null;
    }
}
