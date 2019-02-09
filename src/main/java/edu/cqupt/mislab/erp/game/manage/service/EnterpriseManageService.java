package edu.cqupt.mislab.erp.game.manage.service;

import edu.cqupt.mislab.erp.commons.response.ResponseVo;
import edu.cqupt.mislab.erp.game.manage.model.dto.EnterpriseCreateDto;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseDetailInfoVo;

import java.util.List;

public interface EnterpriseManageService {

    ResponseVo<EnterpriseDetailInfoVo> createNewEnterprise(EnterpriseCreateDto createDto);//创建一个新的企业

    ResponseVo<String> deleteOneEnterprise(Long enterpriseId,Long userId);//删除一个企业，级联删除企业成员

    ResponseVo<String> sureOneEnterprise(Long enterpriseId,Long userId);//当前企业比赛准备开始

    EnterpriseDetailInfoVo getOneEnterpriseInfo(Long enterpriseId);//获取一个企业的详细信息

    List<EnterpriseDetailInfoVo> getEnterpriseInfos(Long gameId);
}
