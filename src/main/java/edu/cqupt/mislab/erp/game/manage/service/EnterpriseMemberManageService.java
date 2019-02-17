package edu.cqupt.mislab.erp.game.manage.service;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.game.manage.model.dto.EnterpriseJoinDto;
import edu.cqupt.mislab.erp.game.manage.model.dto.UserContributionRateSureDto;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseMemberDisplayVo;

import java.util.List;

public interface EnterpriseMemberManageService {

    //加入一个企业
    WebResponseVo<String> joinOneEnterprise(EnterpriseJoinDto joinDto);

    //退出一个企业
    WebResponseVo<String> outOneEnterprise(Long userId,Long enterpriseId);

    //获取一个企业的全部成员信息
    List<EnterpriseMemberDisplayVo> getOneEnterpriseMemberInfos(Long enterpriseId);

    //确认一个企业成员的贡献度
    WebResponseVo<String> sureGameContributionRate(UserContributionRateSureDto rateSureDto);
}
