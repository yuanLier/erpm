package edu.cqupt.mislab.erp.commons.util;

/**
 * @author yuanyiwen
 * @create 2019-03-12 14:46
 * @description 数值转换处理相关util
 */
public class NumberFormatUtil {
    
    /**
     * @author yuanyiwen
     * @description 解决厂房编号问题
     * @date 14:56 2019/3/12
     * @param factoryId 接收FactoryHoldingInfo中的厂房id
     * @return 返回厂房id的后三位，不足三位的用0补齐
     **/
    public static String factoryNumberFormat (Long factoryId) {
        String str = factoryId.toString();
        String factoryNumber;
        if (str.length() > 3) {
            factoryNumber = str.substring(str.length()-3);
        } else {
            factoryNumber = String.format("%03d", factoryId);
        }
        return factoryNumber;
    }
}
