package edu.cqupt.mislab.erp.commons.util;

/**
 * @author yuanyiwen
 * @create 2019-03-12 14:46
 * @description 数值转换处理相关util
 */
public abstract class NumberFormatUtil {
    
    /**
     * @author yuanyiwen
     * @description 解决各实体的编号问题
     * @date 14:56 2019/3/12
     * @param id 接收一个id
     * @param n 接收一个位数n
     * @return 返回id的后n位作为编号，不足n位的用0补齐
     **/
    public static String numberFormat(Long id, int n) {
        String str = id.toString();
        String number;
        if (str.length() > n) {
            number = str.substring(str.length()-n);
        } else {
            number = String.format("%03d", id);
        }
        return number;
    }
}
