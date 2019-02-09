package edu.cqupt.mislab.erp.game.manage.service.impl;

import edu.cqupt.mislab.erp.commons.response.ResponseVo;
import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

import static edu.cqupt.mislab.erp.commons.response.ResponseUtil.*;

@Service
public class EnterpriseManageServiceImpl implements EnterpriseManageService {

    @Autowired
    private GameBasicInfoRepository gameBasicInfoRepository;
    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired
    private EnterpriseMemberInfoRepository enterpriseMemberInfoRepository;
    @Autowired
    private UserStudentRepository userStudentRepository;
    @Autowired
    private GameUserRoleService gameUserRoleService;

    @Override
    public ResponseVo<EnterpriseDetailInfoVo> createNewEnterprise(EnterpriseCreateDto createDto){

        final GameEnterpriseUserRole role = gameUserRoleService.getUserRoleInOneGame(createDto.getGameId(),createDto.getCreatorId());

        if(role != GameEnterpriseUserRole.PASSERBY){

            return toFailResponseVo(HttpStatus.BAD_REQUEST,role.getMessage());
        }

        final GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(createDto.getGameId());

        Set<EnterpriseBasicInfo> enterpriseBasicInfos = gameBasicInfo.getEnterpriseBasicInfos();

        if(enterpriseBasicInfos == null){

            enterpriseBasicInfos = new HashSet<>();
        }

        if(gameBasicInfo.getGameMaxEnterpriseNumber() <= enterpriseBasicInfos.size()){

            return toFailResponseVo(HttpStatus.BAD_REQUEST,"比赛企业个数已经达到上限，无法创建更多的企业");
        }

        final UserStudentInfo userStudentInfo = userStudentRepository.findByIdAndAccountEnable(createDto.getCreatorId(),true);

        //该用户存在，该比赛存在，该用户没有创建企业，该用户没有加入企业，则创建企业

        Integer maxMemberNumber = createDto.getMaxMemberNumber();

        if(maxMemberNumber > gameBasicInfo.getGameInitInfo().getMaxEnterpriseNumber() || maxMemberNumber < 1){

            maxMemberNumber = gameBasicInfo.getGameInitInfo().getMaxEnterpriseNumber();
        }

        EnterpriseBasicInfo enterpriseBasicInfo = EnterpriseBasicInfo.builder()
                .enterpriseCeo(userStudentInfo)
                .enterpriseCostAd(false)//默认没有花费广告
                .enterpriseName(createDto.getEnterpriseName())
                .enterpriseMaxMemberNumber(maxMemberNumber)
                .enterpriseCurrentPeriod(1)
                .gameInfo(gameBasicInfo)
                .enterpriseStatus(EnterpriseStatus.CREATE)
                .gameContributionRateSure(false)
                .build();

        enterpriseBasicInfo = enterpriseBasicInfoRepository.saveAndFlush(enterpriseBasicInfo);

        //存储失败
        if(enterpriseBasicInfo.getId() == null){

            return toFailResponseVo(HttpStatus.INTERNAL_SERVER_ERROR,"企业创建失败，未知错误！");
        }

        EnterpriseDetailInfoVo enterpriseDetailInfoVo = new EnterpriseDetailInfoVo();

        EntityVoUtil.copyFieldsFromEntityToVo(enterpriseBasicInfo,enterpriseDetailInfoVo);

        //响应数据
        return toSuccessResponseVo(enterpriseDetailInfoVo);
    }

    @Override
    public ResponseVo<String> deleteOneEnterprise(Long enterpriseId,Long userId){

        if(enterpriseBasicInfoRepository.existsByIdAndEnterpriseCeo_Id(enterpriseId, userId)){

            //删除一个企业
            enterpriseBasicInfoRepository.delete(enterpriseId);

            return toSuccessResponseVo("删除企业成功");
        }

        return toFailResponseVo(HttpStatus.BAD_REQUEST,"企业不存在或者用户不是企业的创建者");
    }

    @Override
    public ResponseVo<String> sureOneEnterprise(Long enterpriseId,Long userId){

        final EnterpriseBasicInfo basicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        if(basicInfo != null){

            final Long id = basicInfo.getEnterpriseCeo().getId();

            if(userId.equals(id)){

                //只有处于创建状态的企业才可以进行确认，防止企业状态的回退
                if(basicInfo.getEnterpriseStatus() == EnterpriseStatus.CREATE){

                    basicInfo.setEnterpriseStatus(EnterpriseStatus.SURE);

                    //持久化
                    enterpriseBasicInfoRepository.save(basicInfo);

                    return toSuccessResponseVo("企业准备成功！");
                }

                return toFailResponseVo(HttpStatus.BAD_REQUEST,"该企业已经不是创建状态了，无法再进行准备操作");
            }

            return toFailResponseVo(HttpStatus.BAD_REQUEST,"该用户不是该企业的创建者！");
        }

        return toFailResponseVo(HttpStatus.NOT_FOUND,"该企业不存在");
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
