package edu.cqupt.mislab.erp.game.manage.modelinit;

import edu.cqupt.mislab.erp.commons.basic.modelinit.ModelInit;
import edu.cqupt.mislab.erp.commons.basic.modelinit.ModelInitService;
import edu.cqupt.mislab.erp.game.manage.dao.*;
import edu.cqupt.mislab.erp.game.manage.model.entity.*;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import edu.cqupt.mislab.erp.user.modelinit.UserModelInit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author chuyunfei
 * @description 
 * @date 22:39 2019/4/26
 **/

@Slf4j
@Service
public class GameManageModelInit implements ModelInit {

    @Autowired private GameInitInfoRepository gameInitInfoRepository;
    @Autowired private GameBasicInfoRepository gameBasicInfoRepository;
    @Autowired private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired private EnterpriseMemberInfoRepository enterpriseMemberInfoRepository;
    @Autowired private UserStudentRepository userStudentRepository;

    @Autowired private UserModelInit userModelInit;

    @Autowired private ModelInitService modelInitService;

    @Override
    public List<String> applicationModelInit(){

        //判断当前模块是否已经被初始化过
        if(modelInitService.addInitializedModelIfNotExist(this)){

            //先决条件
            final List<String> strings = preGameManageModelInit();

            //先决条件初始化错误
            if(strings != null){

                return strings;
            }

            try{
                log.info("初始化比赛的基本初始化信息");

                //初始化比赛初始化信息
                initGameInitInfo();

                log.info("初始化比赛的基本初始化信息完成");

                return null;
            }catch(Exception e){
                e.printStackTrace();
            }

            return Collections.singletonList("比赛管理模块原始数据初始化失败");
        }

        return null;
    }

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/3 14:34
     * @Description: 比赛管理的初始化先决条件是用户模块初始化
     */
    private List<String> preGameManageModelInit(){

        return userModelInit.applicationModelInit();
    }

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/3 14:41
     * @Description: 初始化比赛信息，里面有一个等待初始化的比赛
     */
    private void initGameInitInfo(){

        //默认初始化比赛信息
        GameInitBasicInfo gameInitBasicInfo = GameInitBasicInfo.builder()
                .maxEnterpriseNumber(20)
                .maxEnterpriseMemberNumber(6)
                .periodOfOneYear(4)
                .totalYear(5)
                .settingEnable(true)
                .settingCreateTimeStamp(new Date())
                .build();

        gameInitBasicInfo = gameInitInfoRepository.save(gameInitBasicInfo);

        //初始化一个比赛信息
        final UserStudentInfo userStudentInfo = userStudentRepository.findByStudentAccountAndAccountEnable("S2016211050",true);

        GameBasicInfo gameBasicInfo = GameBasicInfo.builder()
                .gameInitBasicInfo(gameInitBasicInfo)
                .gameName("究极比赛")
                .gameMaxEnterpriseNumber(gameInitBasicInfo.getMaxEnterpriseNumber())
                .gameCurrentYear(1)
                .gameStatus(GameStatusEnum.CREATE)
                .gameCreateTime(new Date())
                .userStudentInfo(userStudentInfo)
                .build();

        gameBasicInfo = gameBasicInfoRepository.save(gameBasicInfo);

        //初始化一个企业
        EnterpriseBasicInfo enterpriseBasicInfo = EnterpriseBasicInfo.builder()
                .advertising(true)
                .advertisingCost(false)
                .gameContributionRateSure(false)
                .enterpriseStatus(EnterpriseStatusEnum.CREATE)
                .userStudentInfo(userStudentInfo)
                .enterpriseName("究极企业")
                .enterpriseCurrentPeriod(1)
                .gameBasicInfo(gameBasicInfo)
                .enterpriseMaxMemberNumber(gameInitBasicInfo.getMaxEnterpriseMemberNumber())
                .build();

        enterpriseBasicInfo = enterpriseBasicInfoRepository.save(enterpriseBasicInfo);

        //初始化一个成员信息
        EnterpriseMemberInfo enterpriseMemberInfo = EnterpriseMemberInfo.builder()
                .userStudentInfo(userStudentInfo)
                .enterpriseBasicInfo(enterpriseBasicInfo)
                .gameEnterpriseRole("创建者")
                .gameContributionRate(null)
                .gameExperience("请填写实验报告...")
                .build();

        enterpriseMemberInfo = enterpriseMemberInfoRepository.save(enterpriseMemberInfo);
    }
}
