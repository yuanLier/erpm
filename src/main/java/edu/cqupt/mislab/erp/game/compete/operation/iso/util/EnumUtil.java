package edu.cqupt.mislab.erp.game.compete.operation.iso.util;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoStatusEnum;

/**
 * 检查前端传来的某个值是否属于枚举类IsoStatusEnum
 */
public class EnumUtil {

    public static boolean isInclude(String key) {
        boolean include = false;
        for(IsoStatusEnum isoStatus : IsoStatusEnum.values()) {
            if(isoStatus.toString().equals(key)) {
                include = true;
                break;
            }
        }
        return include;
    }
}
