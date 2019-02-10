package edu.cqupt.mislab.erp.game.manage.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cqupt.mislab.erp.commons.response.ResponseVo;
import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.game.manage.constant.ManageConstant;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseMemberInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.dto.EnterpriseJoinDto;
import edu.cqupt.mislab.erp.game.manage.model.dto.UserContributionRateSureDto;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseMemberInfo;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseMemberDisplayVo;
import edu.cqupt.mislab.erp.game.manage.service.EnterpriseMemberManageService;
import edu.cqupt.mislab.erp.game.manage.service.GameUserRoleService;
import edu.cqupt.mislab.erp.game.manage.service.GameUserRoleService.GameEnterpriseUserRole;
import edu.cqupt.mislab.erp.game.manage.websocket.WebSocketMessagePublisher;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static edu.cqupt.mislab.erp.commons.response.ResponseUtil.*;

@Service
public class EnterpriseMemberManageServiceImpl implements EnterpriseMemberManageService {

    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired
    private EnterpriseMemberInfoRepository enterpriseMemberInfoRepository;
    @Autowired
    private UserStudentRepository userStudentRepository;
    @Autowired
    private GameUserRoleService gameUserRoleService;
    @Autowired
    private WebSocketMessagePublisher webSocketMessagePublisher;

    @Override
    public ResponseVo<String> joinOneEnterprise(EnterpriseJoinDto joinDto){

        final GameEnterpriseUserRole role = gameUserRoleService.getUserRoleInOneGame(joinDto.getGameId(),joinDto.getUserId());

        //只有路人甲可以加入企业
        if(role != GameEnterpriseUserRole.PASSERBY){

            return toFailResponseVo(HttpStatus.BAD_REQUEST,role.getMessage());
        }

        final EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(joinDto.getEnterpriseId());

        if(enterpriseBasicInfo == null){

            return toFailResponseVo(HttpStatus.NOT_FOUND,"该企业不存在");
        }

        final Set<EnterpriseMemberInfo> memberInfos = enterpriseBasicInfo.getMemberInfos();

        int memberNumber = 0;

        if(memberInfos != null){

            memberNumber = memberInfos.size();
        }

        //必须要小于企业最大成员数
        if(memberNumber >= enterpriseBasicInfo.getGameInfo().getGameInitInfo().getMaxMemberNumber()){

            return toFailResponseVo(HttpStatus.BAD_REQUEST,"企业成员个数达到最大，不能够再加入");
        }

        //这个用户绝对会存在，非null
        final UserStudentInfo userStudentInfo = userStudentRepository.findOne(joinDto.getUserId());

        EnterpriseMemberInfo enterpriseMemberInfo = EnterpriseMemberInfo.builder()
                .enterprise(enterpriseBasicInfo)
                .studentInfo(userStudentInfo)
                .gameEnterpriseRole("成员")
                .build();

        final EnterpriseMemberInfo info = enterpriseMemberInfoRepository.saveAndFlush(enterpriseMemberInfo);

        if(info.getId() != null){

            return toSuccessResponseVo("加入企业成功");
        }

        return toFailResponseVo(HttpStatus.INTERNAL_SERVER_ERROR,"内部出现未知错误");
    }

    @Override
    public ResponseVo<String> outOneEnterprise(Long userId,Long enterpriseId){

        final EnterpriseMemberInfo memberInfo = enterpriseMemberInfoRepository.findByEnterprise_IdAndStudentInfo_Id(enterpriseId,userId);

        if(memberInfo == null){

            return toFailResponseVo(HttpStatus.NOT_FOUND,"该企业没有该成员");
        }

        final EnterpriseBasicInfo basicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        if(basicInfo != null){

            if(basicInfo.getEnterpriseCeo().getId().equals(userId)){

                final Long gameInfoId = basicInfo.getGameInfo().getId();

                //通知前端
                webSocketMessagePublisher.publish(gameInfoId,new TextMessage(ManageConstant.ENTERPRISE_DELETE_KEY_NAME + enterpriseId));

                //当创始人退出企业时将删除这个企业，级联删除了这个企业的全部成员
                enterpriseBasicInfoRepository.delete(enterpriseId);

                return toSuccessResponseVo("创始人退出企业，删除企业");
            }
        }

        enterpriseMemberInfoRepository.delete(memberInfo.getId());

        return toSuccessResponseVo("退出企业成功");
    }

    @Override
    public List<EnterpriseMemberDisplayVo> getOneEnterpriseMemberInfos(Long enterpriseId){

        final List<EnterpriseMemberInfo> memberInfos = enterpriseMemberInfoRepository.findByEnterprise_Id(enterpriseId);

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
    public ResponseVo<String> sureGameContributionRate(UserContributionRateSureDto rateSureDto){

        final Long enterpriseId = rateSureDto.getEnterpriseId();

        final EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        //这个企业必须要存在
        if(enterpriseBasicInfo == null){

            return toFailResponseVo(HttpStatus.BAD_REQUEST,"该企业不存在");
        }

        final Boolean rateSure = enterpriseBasicInfo.getGameContributionRateSure();

        //贡献度只能够确认一次
        if(rateSure != null && rateSure){

            return toFailResponseVo(HttpStatus.BAD_REQUEST,"该企业贡献度已经被确认，不能再被修改");
        }

        final Long creatorId = rateSureDto.getCreatorId();

        //只有创建者才能够确认贡献度
        if(!creatorId.equals(enterpriseBasicInfo.getEnterpriseCeo().getId())){

            return toFailResponseVo(HttpStatus.BAD_REQUEST,"只有企业创建者才可以进行贡献度确认操作");
        }

        Set<Long> memberIds = new HashSet<>();

        enterpriseMemberInfoRepository.findByEnterprise_Id(enterpriseId)
                .forEach(enterpriseMemberInfo -> {
                    memberIds.add(enterpriseMemberInfo.getId());
                });

        final Set<Long> keySet = rateSureDto.getRates().keySet();

        //企业成员贡献度只能够一次确认全部
        if(!keySet.containsAll(memberIds)){

            return toFailResponseVo(HttpStatus.BAD_REQUEST,"企业贡献度必须确认全部成员的贡献度");
        }

        AtomicInteger rateTotal = new AtomicInteger(0);

        rateSureDto.getRates().values().forEach(rateTotal::addAndGet);

        //企业成员贡献度的总和必须等于100
        if(rateTotal.get() != 100){

            return toFailResponseVo(HttpStatus.BAD_REQUEST,"成员贡献度的总和必须为100");
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

        return toSuccessResponseVo("更新成功");
    }
}