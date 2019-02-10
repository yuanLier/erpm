package edu.cqupt.mislab.erp.game.manage.service;

import edu.cqupt.mislab.erp.commons.response.ResponseVo;
import edu.cqupt.mislab.erp.game.manage.model.dto.EnterpriseJoinDto;
import edu.cqupt.mislab.erp.game.manage.model.dto.UserContributionRateSureDto;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseMemberDisplayVo;

import java.util.List;

public interface EnterpriseMemberManageService {

    ResponseVo<String> joinOneEnterprise(EnterpriseJoinDto joinDto);//加入一个企业

    ResponseVo<String> outOneEnterprise(Long userId,Long enterpriseId);//退出一个企业

    List<EnterpriseMemberDisplayVo> getOneEnterpriseMemberInfos(Long enterpriseId);//获取一个企业的全部成员信息

    ResponseVo<String> sureGameContributionRate(UserContributionRateSureDto rateSureDto);//确认一个企业成员的贡献度
}
