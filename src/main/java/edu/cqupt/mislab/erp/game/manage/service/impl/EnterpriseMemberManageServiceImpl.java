package edu.cqupt.mislab.erp.game.manage.service.impl;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.response.WebResponseVo.ResponseStatus;
import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.commons.websocket.CommonWebSocketMessagePublisher;
import edu.cqupt.mislab.erp.game.manage.constant.ManageConstant;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseMemberInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.dto.EnterpriseJoinDto;
import edu.cqupt.mislab.erp.game.manage.model.dto.UserContributionRateSureDto;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseMemberInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseStatus;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseMemberDisplayVo;
import edu.cqupt.mislab.erp.game.manage.service.EnterpriseMemberManageService;
import edu.cqupt.mislab.erp.game.manage.service.GameUserRoleService;
import edu.cqupt.mislab.erp.game.manage.service.GameUserRoleService.GameEnterpriseUserRole;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.*;

@Service
public class EnterpriseMemberManageServiceImpl implements EnterpriseMemberManageService {

    @Autowired private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired private EnterpriseMemberInfoRepository enterpriseMemberInfoRepository;
    @Autowired private UserStudentRepository userStudentRepository;
    @Autowired private GameUserRoleService gameUserRoleService;
    @Autowired @Qualifier("commonWebSocketService") private CommonWebSocketMessagePublisher webSocketMessagePublisher;

    @Override
    @Transactional
    public WebResponseVo<Long> joinOneEnterprise(EnterpriseJoinDto joinDto){

        final GameEnterpriseUserRole role = gameUserRoleService.getUserRoleInOneGame(joinDto.getGameId(),joinDto.getUserId());

        //只有路人甲可以加入企业
        if(role != GameEnterpriseUserRole.PASSERBY){

            return toFailResponseVoWithMessage(ResponseStatus.BAD_REQUEST,role.getMessage());
        }

        //获取企业信息
        final EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(joinDto.getEnterpriseId());

        if(enterpriseBasicInfo == null){

            return toFailResponseVoWithMessage(ResponseStatus.NOT_FOUND,"该企业不存在");
        }

        //获取已有的成员个数
        int memberNumber = enterpriseMemberInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseBasicInfo.getId()).size();

        //正常加入企业
        if(enterpriseBasicInfo.getEnterpriseStatus() == EnterpriseStatus.PLAYING ||
                enterpriseBasicInfo.getEnterpriseStatus() == EnterpriseStatus.CREATE){

            if(enterpriseBasicInfo.getEnterpriseStatus() == EnterpriseStatus.PLAYING){

                //需要密码通过才行
                if(!enterpriseBasicInfo.getUserStudentInfo().getStudentPassword().equals(joinDto.getPassword())){

                    return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"密码错误，添加成员失败");
                }
            }

            //这里的人数限制是比赛初始化信息的最大人数
            //必须要小于企业最大成员数
            if(memberNumber >= enterpriseBasicInfo.getGameBasicInfo().getGameInitBasicInfo().getMaxEnterpriseMemberNumber()){

                return toFailResponseVoWithMessage(ResponseStatus.BAD_REQUEST,"企业成员个数达到最大，不能够再添加成员了");
            }

            //这个用户绝对会存在，非null
            final UserStudentInfo userStudentInfo = userStudentRepository.findOne(joinDto.getUserId());

            EnterpriseMemberInfo enterpriseMemberInfo = EnterpriseMemberInfo.builder()
                    .enterpriseBasicInfo(enterpriseBasicInfo)
                    .userStudentInfo(userStudentInfo)
                    .gameEnterpriseRole("成员")
                    .build();

            //存储数据
            enterpriseMemberInfoRepository.save(enterpriseMemberInfo);

            return toSuccessResponseVoWithData(enterpriseBasicInfo.getId());

        }else {

            return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"只有运行中或者创建状态的企业才可以添加成员");
        }
    }

    @Override
    @Transactional
    public WebResponseVo<String> outOneEnterprise(Long userId,Long enterpriseId){

        final EnterpriseMemberInfo memberInfo = enterpriseMemberInfoRepository.findByEnterpriseBasicInfo_IdAndUserStudentInfo_Id(enterpriseId,userId);

        if(memberInfo == null){

            //没有该成员就是成功
            return toSuccessResponseVoWithNoData();
        }

        final EnterpriseBasicInfo basicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        if(basicInfo != null){

            //如果是创始人退出企业将删除这个企业
            if(basicInfo.getUserStudentInfo().getId().equals(userId)){

                final Long gameInfoId = basicInfo.getGameBasicInfo().getId();

                //通知前端这个企业已经被删除了
                webSocketMessagePublisher.publish(gameInfoId,new TextMessage(ManageConstant.ENTERPRISE_DELETE_KEY_NAME + enterpriseId));

                //当创始人退出企业时将删除这个企业
                enterpriseBasicInfoRepository.delete(enterpriseId);

                return toSuccessResponseVoWithData("创始人退出企业，删除企业");
            }

            //删除这个成员
            enterpriseMemberInfoRepository.delete(memberInfo.getId());

            //删除成功响应
            return toSuccessResponseVoWithNoData();
        }

        return toFailResponseVoWithMessage(ResponseStatus.NOT_FOUND,"该企业不存在");
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnterpriseMemberDisplayVo> getOneEnterpriseMemberInfos(Long enterpriseId){

        final List<EnterpriseMemberInfo> memberInfos = enterpriseMemberInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId);

        if(memberInfos != null){

            List<EnterpriseMemberDisplayVo> displayVos = new ArrayList<>();

            memberInfos.forEach(enterpriseMemberInfo -> {

                EnterpriseMemberDisplayVo displayVo = new EnterpriseMemberDisplayVo();

                EntityVoUtil.copyFieldsFromEntityToVo(enterpriseMemberInfo,displayVo);

                displayVos.add(displayVo);
            });

            return displayVos;
        }

        return null;
    }

    @Override
    @Transactional
    public WebResponseVo<String> sureGameContributionRate(UserContributionRateSureDto rateSureDto){

        final Long enterpriseId = rateSureDto.getEnterpriseId();

        final EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        //这个企业必须要存在
        if(enterpriseBasicInfo == null){

            return toFailResponseVoWithMessage(ResponseStatus.NOT_FOUND,"该企业不存在");
        }

        //企业状态需要准确
        if(!(enterpriseBasicInfo.getEnterpriseStatus() == EnterpriseStatus.BANKRUPT || enterpriseBasicInfo.getEnterpriseStatus() == EnterpriseStatus.OVER)){

            return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"只有完结的企业才可以确定贡献率");
        }

        Boolean rateSure = enterpriseBasicInfo.getGameContributionRateSure();

        //贡献度只能够确认一次
        if(rateSure != null && rateSure){

            return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"该企业贡献度已经被确认，不能再被修改");
        }

        final Long creatorId = rateSureDto.getCreatorId();

        //只有创建者才能够确认贡献度
        if(!creatorId.equals(enterpriseBasicInfo.getUserStudentInfo().getId())){

            return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"只有企业创建者才可以进行贡献度确认操作");
        }

        Set<Long> memberIds = new HashSet<>();

        enterpriseMemberInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId)
                .forEach(enterpriseMemberInfo -> {
                    memberIds.add(enterpriseMemberInfo.getId());
                });

        final Set<Long> keySet = rateSureDto.getRates().keySet();

        //企业成员贡献度只能够一次确认全部
        if(!keySet.containsAll(memberIds)){

            return toFailResponseVoWithMessage(ResponseStatus.BAD_REQUEST,"企业贡献度必须确认全部成员的贡献度");
        }

        AtomicInteger rateTotal = new AtomicInteger(0);

        rateSureDto.getRates().values().forEach(rateTotal::addAndGet);

        //企业成员贡献度的总和必须等于100
        if(rateTotal.get() != 100){

            return toFailResponseVoWithMessage(ResponseStatus.BAD_REQUEST,"成员贡献度的总和必须为100");
        }

        //更新贡献度
        keySet.forEach(memberId -> {

            final EnterpriseMemberInfo memberInfo = enterpriseMemberInfoRepository.findOne(memberId);

            memberInfo.setGameContributionRate(rateSureDto.getRates().get(memberId));

            enterpriseMemberInfoRepository.save(memberInfo);
        });

        //更新企业状态
        enterpriseBasicInfo.setGameContributionRateSure(true);

        enterpriseBasicInfoRepository.save(enterpriseBasicInfo);

        //响应前端
        return toSuccessResponseVoWithNoData();
    }
}