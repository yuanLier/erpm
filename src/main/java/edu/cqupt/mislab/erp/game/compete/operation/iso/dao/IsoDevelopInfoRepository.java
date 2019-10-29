package edu.cqupt.mislab.erp.game.compete.operation.iso.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoStatusEnum;

import java.util.List;

/**
 * @author chuyunfei
 * @description
 **/

public interface IsoDevelopInfoRepository extends BasicRepository<IsoDevelopInfo, Long> {

    /**
     * 获取某企业的全部iso认证信息
     * @param enterpriseId
     * @return
     */
    List<IsoDevelopInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);

    /**
     * 获取某企业中处于某认证状态的iso认证信息
     * @param enterpriseId
     * @param isoStatus
     * @return
     */
    List<IsoDevelopInfo> findByEnterpriseBasicInfo_IdAndIsoStatus(Long enterpriseId, IsoStatusEnum isoStatus);
}
