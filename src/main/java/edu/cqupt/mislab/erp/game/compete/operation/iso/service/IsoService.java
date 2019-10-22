package edu.cqupt.mislab.erp.game.compete.operation.iso.service;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoDisplayVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @description
 **/

public interface IsoService {

    /**
     * 获取某企业的全部iso
     * @param gameId
     * @return
     */
    List<IsoDisplayVo> findByEnterpriseId(Long gameId);


    /**
     * 获取某企业中处于某认证状态的iso
     * @param enterpriseId
     * @param isoStatus
     * @return
     */
    List<IsoDisplayVo> findByEnterpriseIdAndIsoStatus(Long enterpriseId, IsoStatusEnum isoStatus);


    /**
     * 开始认证
     * @param isoDevelopId
     * @return
     */
    IsoDisplayVo startDevelopIso(Long isoDevelopId);


    /**
     * 修改某个iso的认证状态
     * @param isoDevelopId
     * @param isoStatus
     * @return
     */
    IsoDisplayVo updateIsoStatus(Long isoDevelopId, IsoStatusEnum isoStatus);

}
