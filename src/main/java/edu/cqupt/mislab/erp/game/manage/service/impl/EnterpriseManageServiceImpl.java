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
import edu.cqupt.mislab.erp.game.manage.service.EnterpriseManageService;
import edu.cqupt.mislab.erp.game.manage.service.GameUserRoleService;
import edu.cqupt.mislab.erp.game.manage.service.GameUserRoleService.GameEnterpriseUserRole;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.util.*;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.*;

@Service
public class EnterpriseManageServiceImpl implements EnterpriseManageService {

    @Autowired private GameBasicInfoRepository gameBasicInfoRepository;
    @Autowired private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired private EnterpriseMemberInfoRepository enterpriseMemberInfoRepository;
    @Autowired private UserStudentRepository userStudentRepository;
    @Autowired private GameUserRoleService gameUserRoleService;
    @Autowired private CommonWebSocketMessagePublisher webSocketMessagePublisher;

    @Override
    public WebResponseVo<EnterpriseDetailInfoVo> createNewEnterprise(EnterpriseCreateDto createDto){

        //获取该用户在本场比赛里面的角色
        final GameEnterpriseUserRole role = gameUserRoleService.getUserRoleInOneGame(createDto.getGameId(),createDto.getCreatorId());

        //只有路人甲才可以创建企业
        if(role != GameEnterpriseUserRole.PASSERBY){

            return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,role.getMessage());
        }

        //获取这个比赛信息
        final GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(createDto.getGameId());

        if(gameBasicInfo == null){

            return toFailResponseVoWithMessage(ResponseStatus.NOT_FOUND,"这个比赛不存在，被删除了");
        }

        //获取里面的企业成员信息
        List<EnterpriseBasicInfo> enterpriseBasicInfos = enterpriseBasicInfoRepository.findByGameInfo_Id(gameBasicInfo.getId());

        //每一个比赛设置有一个企业个数上限
        if(gameBasicInfo.getGameMaxEnterpriseNumber() <= enterpriseBasicInfos.size()){

            return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"比赛企业个数已经达到上限，无法创建更多的企业");
        }

        //查询这个用户的信息，该信息已经被校验存在，所以绝对非空
        final UserStudentInfo userStudentInfo = userStudentRepository.findByIdAndAccountEnable(createDto.getCreatorId(),true);

        //该用户存在，该比赛存在，该用户没有创建企业，该用户没有加入企业，则创建企业

        //矫正企业最大成员数量
        Integer maxMemberNumber = createDto.getMaxMemberNumber();

        //最大的成员个数在 1-应用初始化的企业成员个数之间
        if(maxMemberNumber > gameBasicInfo.getGameInitInfo().getMaxEnterpriseNumber()){

            maxMemberNumber = gameBasicInfo.getGameInitInfo().getMaxEnterpriseNumber();
        }

        //构建这个企业
        EnterpriseBasicInfo enterpriseBasicInfo = EnterpriseBasicInfo.builder()
                .enterpriseCeo(userStudentInfo)
                .advertising(true)//默认会投
                .advertisingCost(false)//默认未投广告费
                .enterpriseName(createDto.getEnterpriseName())
                .enterpriseMaxMemberNumber(maxMemberNumber)
                .enterpriseCurrentPeriod(1)//当前周期为第一期
                .gameInfo(gameBasicInfo)
                .enterpriseStatus(EnterpriseStatus.CREATE)//企业状态为创建状态
                .gameContributionRateSure(false)//默认企业成员贡献度没有确认
                .build();

        //存储并立即刷新到数据库
        enterpriseBasicInfo = enterpriseBasicInfoRepository.saveAndFlush(enterpriseBasicInfo);

        //存储失败
        if(enterpriseBasicInfo.getId() == null){

            return toFailResponseVoWithMessage(ResponseStatus.INTERNAL_SERVER_ERROR,"企业创建失败，未知错误！");
        }

        //创建企业的同时需要将企业创建者作为企业成员添加到数据信息里面去
        EnterpriseMemberInfo enterpriseMemberInfo = EnterpriseMemberInfo.builder()
                .gameEnterpriseRole("创建者")
                .studentInfo(userStudentInfo)
                .enterprise(enterpriseBasicInfo)
                .build();

        //企业成员增加一个
        enterpriseMemberInfoRepository.save(enterpriseMemberInfo);

        //通知前端某一个企业已经被创建出来了
        webSocketMessagePublisher.publish(createDto.getGameId(),new TextMessage(ManageConstant.ENTERPRISE_CREATE_KEY_NAME + enterpriseBasicInfo.getId()));

        EnterpriseDetailInfoVo enterpriseDetailInfoVo = new EnterpriseDetailInfoVo();

        EntityVoUtil.copyFieldsFromEntityToVo(enterpriseBasicInfo,enterpriseDetailInfoVo);

        //响应数据
        return toSuccessResponseVoWithData(enterpriseDetailInfoVo);
    }

    @Override
    public WebResponseVo<String> deleteOneEnterprise(Long enterpriseId,Long userId,String password){

        final EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        //如果该企业本来就不存在也就是删除成功咯
        if(enterpriseBasicInfo == null){

            return toSuccessResponseVoWithNoData();
        }

        //获取企业的创建者
        final UserStudentInfo userStudentInfo = enterpriseBasicInfo.getEnterpriseCeo();

        //判断是否有进行删除的权利
        if(!userStudentInfo.getId().equals(userId) || !userStudentInfo.getStudentPassword().equals(password)){

            return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"认证信息错误，无法删除该企业");
        }

        //判断企业状态是否能够被删除
        if(enterpriseBasicInfo.getEnterpriseStatus() != EnterpriseStatus.CREATE){

            return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"只能删除处于创建状态的企业");
        }

        //获取比赛的ID
        Long gameId = enterpriseBasicInfo.getGameInfo().getId();
        //删除这个企业
        try{
            //删除一个企业
            enterpriseBasicInfoRepository.delete(enterpriseId);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            //通知前端该企业被删除了
            webSocketMessagePublisher.publish(gameId,new TextMessage(ManageConstant.ENTERPRISE_DELETE_KEY_NAME + enterpriseId));
        }

        //删除成功
        return toSuccessResponseVoWithNoData();
    }

    @Override
    public WebResponseVo<String> sureOneEnterprise(Long enterpriseId,Long userId){

        final EnterpriseBasicInfo basicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        if(basicInfo != null){

            //对比是否是创建者，只有创建者才可以进行企业的准备完成操作
            final Long id = basicInfo.getEnterpriseCeo().getId();

            if(userId.equals(id)){

                //只有处于创建状态的企业才可以进行确认，防止企业状态的回退
                if(basicInfo.getEnterpriseStatus() == EnterpriseStatus.CREATE){

                    basicInfo.setEnterpriseStatus(EnterpriseStatus.SURE);

                    //持久化
                    enterpriseBasicInfoRepository.save(basicInfo);

                    //获取该企业的比赛信息
                    final Long gameId = basicInfo.getGameInfo().getId();

                    //通知前端该企业已经准备完毕
                    webSocketMessagePublisher.publish(gameId,new TextMessage(ManageConstant.ENTERPRISE_SURE_KEY_NAME + enterpriseId));

                    return toSuccessResponseVoWithNoData();
                }

                return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"该企业已经不是创建状态了，无法再进行准备操作");
            }

            return toFailResponseVoWithMessage(ResponseStatus.FORBIDDEN,"该用户不是该企业的创建者！");
        }

        return toFailResponseVoWithMessage(ResponseStatus.NOT_FOUND,"该企业不存在");
    }

    @Override
    public EnterpriseDetailInfoVo getOneEnterpriseInfo(Long enterpriseId){

        final EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        if(enterpriseBasicInfo != null){

            EnterpriseDetailInfoVo enterpriseDetailInfoVo = new EnterpriseDetailInfoVo();

            EntityVoUtil.copyFieldsFromEntityToVo(enterpriseBasicInfo,enterpriseDetailInfoVo);

            return enterpriseDetailInfoVo;
        }

        return null;
    }

    @Override
    public List<EnterpriseDetailInfoVo> getEnterpriseInfos(Long gameId){

        final List<EnterpriseBasicInfo> basicInfos = enterpriseBasicInfoRepository.findByGameInfo_Id(gameId);

        if(basicInfos != null){

            List<EnterpriseDetailInfoVo> detailInfoVos = new ArrayList<>();

            basicInfos.forEach(basicInfo ->{

                EnterpriseDetailInfoVo detailInfoVo = new EnterpriseDetailInfoVo();

                EntityVoUtil.copyFieldsFromEntityToVo(basicInfo,detailInfoVo);

                detailInfoVos.add(detailInfoVo);
            });

            return detailInfoVos;
        }

        return null;
    }
}
