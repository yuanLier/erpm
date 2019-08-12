package edu.cqupt.mislab.erp.game.manage.service.impl;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.response.WebResponseVo.ResponseStatus;
import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.commons.websocket.CommonWebSocketMessagePublisher;
import edu.cqupt.mislab.erp.game.manage.constant.ManageConstant;
import edu.cqupt.mislab.erp.game.manage.dao.*;
import edu.cqupt.mislab.erp.game.manage.model.dto.EnterpriseCreateDto;
import edu.cqupt.mislab.erp.game.manage.model.entity.*;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseDetailInfoVo;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterprisePeriodVo;
import edu.cqupt.mislab.erp.game.manage.service.EnterpriseManageService;
import edu.cqupt.mislab.erp.game.manage.service.GameUserRoleService;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import java.util.*;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.*;

/**
 * @author chuyunfei
 * @description
 * @date 22:45 2019/4/26
 **/

@Service
public class EnterpriseManageServiceImpl implements EnterpriseManageService {

    @Autowired private GameBasicInfoRepository gameBasicInfoRepository;
    @Autowired private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired private EnterpriseMemberInfoRepository enterpriseMemberInfoRepository;
    @Autowired private UserStudentRepository userStudentRepository;
    @Autowired private GameUserRoleService gameUserRoleService;
    @Autowired private CommonWebSocketMessagePublisher webSocketMessagePublisher;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WebResponseVo<EnterpriseDetailInfoVo> createNewEnterprise(EnterpriseCreateDto createDto){

        //获取该用户在本场比赛里面的角色
        final GameEnterpriseUserRoleEnum role = gameUserRoleService.getUserRoleInOneGame(createDto.getGameId(),createDto.getCreatorId());

        //只有路人甲才可以创建企业
        if(role != GameEnterpriseUserRoleEnum.PASSERBY){

            return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,role.getMessage());
        }

        //获取这个比赛信息
        final GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(createDto.getGameId());

        //这个是一个可能的现象
        if(gameBasicInfo == null){

            return toFailResponseVoWithMessage(ResponseStatus.NOT_FOUND,"这个比赛不存在，被删除了");
        }

        //获取里面的企业信息
        List<EnterpriseBasicInfo> enterpriseBasicInfos = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(createDto.getGameId());

        //每一个比赛设置有一个企业个数上限
        if(gameBasicInfo.getGameMaxEnterpriseNumber() <= enterpriseBasicInfos.size()){

            return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"比赛企业个数已经达到上限，无法创建更多的企业");
        }

        //查询这个用户的信息，该信息已经被校验存在，所以绝对非空
        final UserStudentInfo userStudentInfo = userStudentRepository.findOne(createDto.getCreatorId());

        //该用户存在，该比赛存在，该用户没有创建企业，该用户没有加入企业，则创建企业

        //矫正企业最大成员数量
        Integer maxMemberNumber = createDto.getMaxMemberNumber();

        //最大的成员个数在 1-应用初始化的企业成员个数之间
        if(maxMemberNumber > gameBasicInfo.getGameMaxEnterpriseNumber()){

            maxMemberNumber = gameBasicInfo.getGameMaxEnterpriseNumber();
        }

        //构建这个企业
        EnterpriseBasicInfo enterpriseBasicInfo = EnterpriseBasicInfo.builder()
                .userStudentInfo(userStudentInfo)
                //默认会投广告费
                .advertising(true)
                //默认未投广告费
                .finishAdvertising(false)
                // 默认顺序为0
                .sequence(0)
                // 默认不轮到这个企业选单
                .myTurn(false)
                // 默认未结束订单会
                .finishChoice(false)
                .enterpriseName(createDto.getEnterpriseName())
                .enterpriseMaxMemberNumber(maxMemberNumber)
                //当前周期为第一期
                .enterpriseCurrentPeriod(1)
                .gameBasicInfo(gameBasicInfo)
                //企业状态为创建状态
                .enterpriseStatus(EnterpriseStatusEnum.CREATE)
                //默认企业成员贡献度没有确认
                .gameContributionRateSure(false)
                .build();

        //存储并立即刷新到数据库
        enterpriseBasicInfo = enterpriseBasicInfoRepository.saveAndFlush(enterpriseBasicInfo);

        //创建企业的同时需要将企业创建者作为企业成员添加到数据信息里面去
        EnterpriseMemberInfo enterpriseMemberInfo = EnterpriseMemberInfo.builder()
                .gameEnterpriseRole("创建者")
                .userStudentInfo(userStudentInfo)
                .enterpriseBasicInfo(enterpriseBasicInfo)
                .gameExperience("请填写实验报告...")
                .build();

        //企业成员增加一个
        enterpriseMemberInfoRepository.saveAndFlush(enterpriseMemberInfo);

        //通知前端某一个企业已经被创建出来了
        webSocketMessagePublisher.publish(createDto.getGameId(),new TextMessage(ManageConstant.ENTERPRISE_CREATE_KEY_NAME + enterpriseBasicInfo.getId()));

        EnterpriseDetailInfoVo enterpriseDetailInfoVo = new EnterpriseDetailInfoVo();

        EntityVoUtil.copyFieldsFromEntityToVo(enterpriseBasicInfo,enterpriseDetailInfoVo);

        //响应数据
        return toSuccessResponseVoWithData(enterpriseDetailInfoVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WebResponseVo<String> deleteOneEnterprise(Long enterpriseId,Long userId,String password){

        final EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        //有必要判断
        if(enterpriseBasicInfo == null){

            return toFailResponseVoWithMessage(ResponseStatus.NOT_FOUND, "该企业不存在");
        }

        //获取企业的创建者
        final UserStudentInfo userStudentInfo = enterpriseBasicInfo.getUserStudentInfo();

        //判断是否有进行删除的权利
        if(!userStudentInfo.getId().equals(userId) || !userStudentInfo.getStudentPassword().equals(password)){

            return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"认证信息错误，无法删除该企业");
        }

        //获取比赛的ID
        Long gameId = enterpriseBasicInfo.getGameBasicInfo().getId();

        //删除一个企业
        enterpriseBasicInfoRepository.delete(enterpriseId);

        //通知前端该企业被删除了
        webSocketMessagePublisher.publish(gameId,new TextMessage(ManageConstant.ENTERPRISE_DELETE_KEY_NAME + enterpriseId));

        //删除成功
        return toSuccessResponseVoWithNoData();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WebResponseVo<String> sureOneEnterprise(Long enterpriseId,Long userId){

        final EnterpriseBasicInfo basicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        if(basicInfo != null){

            //对比是否是创建者，只有创建者才可以进行企业的准备完成操作
            final Long id = basicInfo.getUserStudentInfo().getId();

            if(!userId.equals(id)){

                return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"该用户不是该企业的创建者！");
            }

            basicInfo.setEnterpriseStatus(EnterpriseStatusEnum.SURE);

            //持久化
            enterpriseBasicInfoRepository.save(basicInfo);

            //获取该企业的比赛信息
            final Long gameId = basicInfo.getGameBasicInfo().getId();

            //通知前端该企业已经准备完毕
            webSocketMessagePublisher.publish(gameId,new TextMessage(ManageConstant.ENTERPRISE_SURE_KEY_NAME + enterpriseId));

            return toSuccessResponseVoWithNoData();
        }

        return toFailResponseVoWithMessage(ResponseStatus.NOT_FOUND,"该企业不存在");
    }

    @Override
    public EnterpriseDetailInfoVo getOneEnterpriseInfo(Long enterpriseId){

        final EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        if(enterpriseBasicInfo == null){

            return null;
        }

        EnterpriseDetailInfoVo enterpriseDetailInfoVo = new EnterpriseDetailInfoVo();

        EntityVoUtil.copyFieldsFromEntityToVo(enterpriseBasicInfo,enterpriseDetailInfoVo);

        return enterpriseDetailInfoVo;
    }

    @Override
    public List<EnterpriseDetailInfoVo> getEnterpriseInfos(Long gameId){

        final List<EnterpriseBasicInfo> basicInfos = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId);

        if(basicInfos.size() == 0){

            return null;
        }

        List<EnterpriseDetailInfoVo> detailInfoVos = new ArrayList<>();

        basicInfos.forEach(basicInfo ->{

            EnterpriseDetailInfoVo detailInfoVo = new EnterpriseDetailInfoVo();

            EntityVoUtil.copyFieldsFromEntityToVo(basicInfo,detailInfoVo);

            detailInfoVos.add(detailInfoVo);
        });

        return detailInfoVos;
    }

    @Override
    public EnterprisePeriodVo getCurrentPeriod(Long enterpriseId) {

        EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        // 获取企业当前所处的年与周期
        Integer currentPeriod = enterpriseBasicInfo.getEnterpriseCurrentPeriod();
        Integer currentYear = enterpriseBasicInfo.getGameBasicInfo().getGameCurrentYear();
        Integer totalPeriod = enterpriseBasicInfo.getGameBasicInfo().getGameInitBasicInfo().getPeriodOfOneYear();
        Integer periodOfYear = (currentPeriod % totalPeriod == 0) ? totalPeriod : currentPeriod % totalPeriod;

        EnterprisePeriodVo enterprisePeriodVo = new EnterprisePeriodVo();
        enterprisePeriodVo.setYear(currentYear);
        enterprisePeriodVo.setPeriod(periodOfYear);

        return enterprisePeriodVo;
    }
}
