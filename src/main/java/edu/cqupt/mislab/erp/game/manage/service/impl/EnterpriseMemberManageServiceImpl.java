package edu.cqupt.mislab.erp.game.manage.service.impl;

import edu.cqupt.mislab.erp.commons.response.ResponseVo;
import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseMemberInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.dto.EnterpriseJoinDto;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseMemberInfo;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseMemberDisplayVo;
import edu.cqupt.mislab.erp.game.manage.service.EnterpriseMemberManageService;
import edu.cqupt.mislab.erp.game.manage.service.GameUserRoleService;
import edu.cqupt.mislab.erp.game.manage.service.GameUserRoleService.GameEnterpriseUserRole;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.ResponseUtil.*;

@Service
public class EnterpriseMemberManageServiceImpl implements EnterpriseMemberManageService {

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
    public ResponseVo<String> joinOneEnterprise(EnterpriseJoinDto joinDto){

        final GameEnterpriseUserRole role = gameUserRoleService.getUserRoleInOneGame(joinDto.getGameId(),joinDto.getUserId());

        if(role != GameEnterpriseUserRole.PASSERBY){

            return toFailResponseVo(HttpStatus.BAD_REQUEST,role.getMessage());
        }

        //只有路人甲可以加入企业
        final EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(joinDto.getEnterpriseId());

        if(enterpriseBasicInfo == null){

            return toFailResponseVo(HttpStatus.NOT_FOUND,"该企业不存在");
        }

        //这个用户绝对会存在，非null
        final UserStudentInfo userStudentInfo = userStudentRepository.findOne(joinDto.getUserId());

        EnterpriseMemberInfo enterpriseMemberInfo = EnterpriseMemberInfo.builder()
                .enterprise(enterpriseBasicInfo)
                .studentInfo(userStudentInfo)
                .gameEnterpriseRole("企业成员")
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

        enterpriseMemberInfoRepository.delete(memberInfo.getId());

        return toSuccessResponseVo("推出企业成功");
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
}
