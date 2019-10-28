package edu.cqupt.mislab.erp.game.manage.service;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.game.manage.model.dto.EnterpriseJoinDto;
import edu.cqupt.mislab.erp.game.manage.model.dto.UserContributionRateSureDto;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseDetailInfoVo;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseMemberDisplayVo;

import java.util.List;

/**
 * @author chuyunfei
 * @description 企业中成员变动与查询
 * @date 21:31 2019/4/26
 **/
public interface EnterpriseMemberManageService {

    /**
     * @author chuyunfei
     * @description 加入一个企业
     * @date 21:31 2019/4/26
     **/
    WebResponseVo<Long> joinOneEnterprise(EnterpriseJoinDto joinDto);

    /**
     * @author chuyunfei
     * @description 退出一个企业
     * @date 21:31 2019/4/26
     **/
    WebResponseVo<String> outOneEnterprise(Long userId,Long enterpriseId);

    /**
     * @author chuyunfei
     * @description 获取一个企业的全部成员信息
     * @date 21:31 2019/4/26
     **/
    List<EnterpriseMemberDisplayVo> getOneEnterpriseMemberInfos(Long enterpriseId);

    /**
     * @author chuyunfei
     * @description 确认一个企业成员的贡献度
     * @date 21:31 2019/4/26
     **/
    WebResponseVo<String> sureGameContributionRate(UserContributionRateSureDto rateSureDto);

    /**
     * @author yuanyiwen
     * @description 获取一个用户是在比赛中的哪个企业
     * @date 9:27 2019/7/26
     **/
    EnterpriseDetailInfoVo getEnterpriseOfMember(Long userId, Long gameId);

}
