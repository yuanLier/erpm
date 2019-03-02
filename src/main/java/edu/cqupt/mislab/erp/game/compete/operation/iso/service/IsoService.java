package edu.cqupt.mislab.erp.game.compete.operation.iso.service;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.dto.IsoBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoDisplayVo;

import java.util.List;

public interface IsoService {

    /**
     * @Author: chuyunfei
     * @Description: 获取应用里面全部的ISO认证信息
     * @Date: 2019/3/1 19:48
     **/
    List<IsoBasicInfo> findAllNewestApplicationIsoBasicInfos();

    /**
     * 获取某企业的全部iso
     * @param enterpriseId
     * @return
     */
    List<IsoDisplayVo> findByEnterpriseId(Long enterpriseId);


    /**
     * 获取某企业中处于某认证状态的iso
     * @param enterpriseId
     * @param isoStatus
     * @return
     */
    List<IsoDisplayVo> findByEnterpriseIdAndIsoStatus(Long enterpriseId, IsoStatusEnum isoStatus);


    /**
     * 修改某个iso的认证状态
     * @param isoDevelopId
     * @param isoStatus
     * @return
     */
    IsoDisplayVo updateIsoStatus(Long isoDevelopId, IsoStatusEnum isoStatus);


    /**
     * （管理员）修改iso基本信息
     * @param isoBasicDto
     * @return
     */
    IsoBasicVo updateIsoBasicInfo(IsoBasicDto isoBasicDto);
}
