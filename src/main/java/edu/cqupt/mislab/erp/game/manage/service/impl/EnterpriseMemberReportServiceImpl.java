package edu.cqupt.mislab.erp.game.manage.service.impl;

import edu.cqupt.mislab.erp.commons.aspect.EnterpriseStatusException;
import edu.cqupt.mislab.erp.commons.config.QiniuProperties;
import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.commons.util.FileUtil;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseMemberInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.*;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseMemberReportVo;
import edu.cqupt.mislab.erp.game.manage.service.EnterpriseMemberReportService;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-10-20 14:46
 * @description
 */
@Service
public class EnterpriseMemberReportServiceImpl implements EnterpriseMemberReportService {

    @Autowired
    private EnterpriseMemberInfoRepository enterpriseMemberInfoRepository;
    @Autowired
    private UserStudentRepository userStudentRepository;
    @Autowired
    private GameBasicInfoRepository gameBasicInfoRepository;

    @Autowired
    private QiniuProperties qiniuProperties;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean submitExperimentalReport(Long userId, Long gameId, MultipartFile multipartFile) {

        EnterpriseMemberInfo enterpriseMemberInfo = enterpriseStatusValid(userId, gameId);

        FileInputStream inputStream = null;
        try {
            inputStream = (FileInputStream) multipartFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 使用用户id+当前时间戳作为文件名称   todo 后续可能会完善命名规则，比如加个姓名学号之类的？
        String key = userId + Long.toString(System.currentTimeMillis());
        FileUtil.fileUpload(qiniuProperties, inputStream, key);

        // 走到这里说明文件存储未抛出异常

        // 更新企业成员报告信息
        enterpriseMemberInfo.setReportKey(key);
        enterpriseMemberInfoRepository.save(enterpriseMemberInfo);

        return true;
    }

    @Override
    public EnterpriseMemberReportVo checkExperimentalReport(Long userId, Long gameId) {

        EnterpriseMemberInfo enterpriseMemberInfo = enterpriseStatusValid(userId, gameId);

        return EntityVoUtil.copyFieldsFromEntityToVo(qiniuProperties, enterpriseMemberInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteExperimentalReport(Long userId, String password, Long gameId) {

        // 删除之前需要进行用户身份认证   todo 如果后面给密码加密了，记得改一下这里
        UserStudentInfo userStudentInfo = userStudentRepository.findOne(userId);
        if(!password.equals(userStudentInfo.getStudentPassword())) {
            return null;
        }

        EnterpriseMemberInfo enterpriseMemberInfo = enterpriseStatusValid(userId, gameId);

        // 删除报告
        FileUtil.fileDelete(qiniuProperties, enterpriseMemberInfo.getReportKey());
        // 同步修改成员信息
        enterpriseMemberInfo.setReportKey(null);
        enterpriseMemberInfoRepository.save(enterpriseMemberInfo);

        return true;
    }

    @Override
    public List<EnterpriseMemberReportVo> getAllReportsOfGame(Long gameId) {

        GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);

        // 若比赛未进行完成，拒绝查询
        if(!GameStatusEnum.OVER.equals(gameBasicInfo.getGameStatus())) {
            return null;
        }

        List<EnterpriseMemberReportVo> enterpriseMemberReportVoList = new ArrayList<>();

        // 获取比赛中的全部企业成员信息
        List<EnterpriseMemberInfo> enterpriseMemberInfoList = enterpriseMemberInfoRepository.findByEnterpriseBasicInfo_GameBasicInfo_Id(gameId);
        for(EnterpriseMemberInfo enterpriseMemberInfo : enterpriseMemberInfoList) {
            enterpriseMemberReportVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(qiniuProperties, enterpriseMemberInfo));
        }

        return enterpriseMemberReportVoList;
    }


    /**
     * 校验用户所在的企业是否已经完成经营
     * @param userId
     * @param gameId
     * @return 若企业状态符合要求，返回企业成员信息实体
     */
    private EnterpriseMemberInfo enterpriseStatusValid(Long userId, Long gameId) {

        EnterpriseMemberInfo enterpriseMemberInfo = enterpriseMemberInfoRepository.findByUserStudentInfo_IdAndEnterpriseBasicInfo_GameBasicInfo_Id(userId, gameId);
        EnterpriseBasicInfo enterpriseBasicInfo = enterpriseMemberInfo.getEnterpriseBasicInfo();

        // 若该用户所在的企业未完成经营，不允许提交/查看报告
        boolean isOver = EnterpriseStatusEnum.BANKRUPT.equals(enterpriseBasicInfo.getEnterpriseStatus()) || EnterpriseStatusEnum.OVER.equals(enterpriseBasicInfo.getEnterpriseStatus());
        if(!isOver) {
            throw new EnterpriseStatusException("您所在的企业尚未完成经营！");
        }

        return enterpriseMemberInfo;
    }
}
