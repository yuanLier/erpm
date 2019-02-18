package edu.cqupt.mislab.erp.game.compete.operation.iso.service;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.EnterpriseIsoDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoBasicInfoDisplayVo;

import java.util.List;

public interface IsoService {

    //获取全部的ISO基本信息
    List<IsoBasicInfoDisplayVo> getAllIsoInfos(Boolean newest);

    //获取某个企业的全部认证信息
    EnterpriseIsoDisplayVo getAllIsoInfosOfOneEnterprise(Long enterpriseId);
}
