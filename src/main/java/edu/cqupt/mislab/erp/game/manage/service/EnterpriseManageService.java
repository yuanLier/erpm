package edu.cqupt.mislab.erp.game.manage.service;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.game.manage.model.dto.EnterpriseCreateDto;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseDetailInfoVo;

import java.util.List;

public interface EnterpriseManageService {

    //创建一个新的企业
    WebResponseVo<EnterpriseDetailInfoVo> createNewEnterprise(EnterpriseCreateDto createDto);

    //删除一个企业，级联删除企业成员
    WebResponseVo<String> deleteOneEnterprise(Long enterpriseId,Long userId,String password);

    //当前企业比赛准备开始
    WebResponseVo<String> sureOneEnterprise(Long enterpriseId,Long userId);

    //获取一个企业的详细信息
    EnterpriseDetailInfoVo getOneEnterpriseInfo(Long enterpriseId);

    //获取一个比赛的全部信息
    List<EnterpriseDetailInfoVo> getEnterpriseInfos(Long gameId);
}
