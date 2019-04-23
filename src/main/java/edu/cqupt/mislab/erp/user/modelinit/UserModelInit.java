package edu.cqupt.mislab.erp.user.modelinit;

import edu.cqupt.mislab.erp.commons.basic.modelinit.ModelInit;
import edu.cqupt.mislab.erp.commons.basic.modelinit.ModelInitService;
import edu.cqupt.mislab.erp.user.dao.*;
import edu.cqupt.mislab.erp.user.model.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserModelInit implements ModelInit {

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/3 14:11
     * @Description: 初始化用户模块的原始数据，包括一个用户、几个头像和几个专业信息
     **/

    @Autowired private MajorBasicInfoRepository majorBasicInfoRepository;
    @Autowired private UserAvatarRepository userAvatarRepository;
    @Autowired private UserStudentRepository userStudentRepository;
    @Autowired private CollegeBasicInfoRepository collegeBasicInfoRepository;
    @Autowired private UserTeacherRepository userTeacherRepository;

    @Autowired private ModelInitService modelInitService;

    @Override
    public List<String> applicationModelInit(){

        if(modelInitService.addInitializedModelIfNotExist(this)){

            try{

                log.info("初始化专业信息");
                initMajorInfo();

                log.info("初始化头像信息");
                initUserAvatarInfo();

                log.info("初始化一个用户");
                initUserStudentInfo();

                return null;
            }catch(Exception e){
                e.printStackTrace();
            }

            return Collections.singletonList("用户模块初始化出现错误");
        }

        return null;
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

        final UserTeacherInfo userTeacherInfo = userTeacherRepository.save(UserTeacherInfo.builder().build());

        UserStudentInfo userStudentInfo = UserStudentInfo.builder()
                .accountEnable(true)
                .userTeacherInfo(userTeacherInfo)
                .studentAccount("S2016211050")
                .studentPassword("M123456")
                .studentName("楚云飞")
                .majorBasicInfo(majorBasicInfoRepository.findOne(1L))
                .studentClass("03011603")
                .email("755708445@qq.com")
                .phone("15025724135")
                .gender(UserGenderEnum.Man)
                .userAvatarInfo(userAvatarRepository.findOne(1L))
                .build();

        userStudentRepository.save(userStudentInfo);
    }

    /**
     * 初始化专业信息
     */
    private void initMajorInfo(){

        List<MajorBasicInfo> majorBasicInfos = new ArrayList<>();

        CollegeBasicInfo collegeBasicInfo = CollegeBasicInfo.builder().college("经济管理学院").build();
        collegeBasicInfo = collegeBasicInfoRepository.save(collegeBasicInfo);

        majorBasicInfos.add(MajorBasicInfo.builder().college(collegeBasicInfo).major("信息管理与信息系统").build());
        majorBasicInfos.add(MajorBasicInfo.builder().college(collegeBasicInfo).major("工商管理").build());
        majorBasicInfos.add(MajorBasicInfo.builder().college(collegeBasicInfo).major("经济学").build());
        majorBasicInfos.add(MajorBasicInfo.builder().college(collegeBasicInfo).major("工程管理").build());

        CollegeBasicInfo collegeBasicInfo1 = CollegeBasicInfo.builder().college("计算机学院").build();
        collegeBasicInfo1 = collegeBasicInfoRepository.save(collegeBasicInfo1);

        majorBasicInfos.add(MajorBasicInfo.builder().college(collegeBasicInfo1).major("计算机科学与技术").build());
        majorBasicInfos.add(MajorBasicInfo.builder().college(collegeBasicInfo1).major("计算机智能与科学").build());

        majorBasicInfoRepository.save(majorBasicInfos);
    }
}
