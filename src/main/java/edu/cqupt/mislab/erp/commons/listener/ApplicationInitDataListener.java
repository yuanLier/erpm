package edu.cqupt.mislab.erp.commons.listener;

import edu.cqupt.mislab.erp.game.manage.dao.GameInitInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameInitInfo;
import edu.cqupt.mislab.erp.user.dao.*;
import edu.cqupt.mislab.erp.user.model.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@WebListener
public class ApplicationInitDataListener implements ServletContextListener {

    @Value("${spring.profiles.active}")
    private String activeProfiles;

    @Autowired private MajorInfoRepository majorInfoRepository;
    @Autowired private UserAvatarRepository userAvatarRepository;
    @Autowired private UserStudentRepository userStudentRepository;
    @Autowired private CollegeInfoRepository collegeInfoRepository;
    @Autowired private GameInitInfoRepository gameInitInfoRepository;

    @Override
    public void contextInitialized(ServletContextEvent sce){

        if(activeProfiles.equals("dev")){

            log.info("初始化专业信息");
            initMajorInfo();

            log.info("初始化头像信息");
            initUserAvatarInfo();

            log.info("初始化一个用户");
            initUserStudentInfo();

            log.info("初始化比赛的基本初始化信息");
            initGameInitInfo();
        }
    }

    /**
     * 初始化比赛的初始化信息
     */
    private void initGameInitInfo(){

        GameInitInfo gameInitInfo = GameInitInfo.builder()
                .maxEnterpriseNumber(20)
                .maxMemberNumber(6)
                .period(4)
                .totalYear(5)
                .timeStamp(new Date())
                .build();

        gameInitInfoRepository.save(gameInitInfo);
    }

    /**
     * 初始化用户头像
     */
    private void initUserAvatarInfo(){

        userAvatarRepository.save(UserAvatarInfo.builder().avatarLocation("A001.jpg").build());
        userAvatarRepository.save(UserAvatarInfo.builder().avatarLocation("A002.jpg").build());
        userAvatarRepository.save(UserAvatarInfo.builder().avatarLocation("A003.jpg").build());
        userAvatarRepository.save(UserAvatarInfo.builder().avatarLocation("A004.jpg").build());
    }

    /**
     * 初始化一个用户
     */
    private void initUserStudentInfo(){

        UserStudentInfo userStudentInfo = UserStudentInfo.builder()
                .accountEnable(true)
                .userTeacherInfo(null)
                .studentAccount("S2016211050")
                .studentPassword("M123456")
                .studentName("楚云飞")
                .majorInfo(majorInfoRepository.findOne(1L))
                .studentClass("03011603")
                .email("755708445@qq.com")
                .phone("15025724135")
                .gender(UserGender.Man)
                .userAvatarInfo(userAvatarRepository.findOne(1L))
                .build();

        userStudentRepository.save(userStudentInfo);
    }

    /**
     * 初始化专业信息
     */
    private void initMajorInfo(){

        List<MajorInfo> majorInfos = new ArrayList<>();

        CollegeInfo collegeInfo = CollegeInfo.builder().college("经济管理学院").build();
        collegeInfo = collegeInfoRepository.save(collegeInfo);

        majorInfos.add(MajorInfo.builder().college(collegeInfo).major("信息管理与信息系统").build());
        majorInfos.add(MajorInfo.builder().college(collegeInfo).major("工商管理").build());
        majorInfos.add(MajorInfo.builder().college(collegeInfo).major("经济学").build());
        majorInfos.add(MajorInfo.builder().college(collegeInfo).major("工程管理").build());

        CollegeInfo collegeInfo1 = CollegeInfo.builder().college("计算机学院").build();
        collegeInfo1 = collegeInfoRepository.save(collegeInfo1);

        majorInfos.add(MajorInfo.builder().college(collegeInfo1).major("计算机科学与技术").build());
        majorInfos.add(MajorInfo.builder().college(collegeInfo1).major("计算机智能与科学").build());

        majorInfoRepository.save(majorInfos);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce){ }
}
