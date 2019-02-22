package edu.cqupt.mislab.erp.game.compete.operation.iso.util;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoDisplayVo;

public class VoUtil {
    public static IsoDisplayVo castEntityToVo(IsoDevelopInfo isoDevelopInfo) {
        IsoDisplayVo isoDisplayVo = new IsoDisplayVo();

        isoDisplayVo.setId(isoDevelopInfo.getId());

        // 每期维护费用
        isoDisplayVo.setIsoMaintainCost(isoDevelopInfo.getIsoBasicInfo().getIsoMaintainCost());
        // ISO认证名称
        isoDisplayVo.setIsoName(isoDevelopInfo.getIsoBasicInfo().getIsoName());
        // 每期认证费用
        isoDisplayVo.setIsoResearchCost(isoDevelopInfo.getIsoBasicInfo().getIsoResearchCost());
        // 需要认证的总期数
        isoDisplayVo.setIsoResearchPeriod(isoDevelopInfo.getIsoBasicInfo().getIsoResearchPeriod());

        // 认证状态
        isoDisplayVo.setIsoStatus(isoDevelopInfo.getIsoStatus());
        // 已经认证的期数
        isoDisplayVo.setResearchedPeriod(isoDevelopInfo.getResearchedPeriod());

        return isoDisplayVo;
    }
}
