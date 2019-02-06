package edu.cqupt.mislab.erp.commons.util;

import edu.cqupt.mislab.erp.commons.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;

public abstract class CommonUtil {

    /**
     * 根据当前线程ID、当前线程名称、当前时间纳秒共同组成一个唯一标识字符串
     */
    public static String getUUString(){

        return Thread.currentThread().getId() + Thread.currentThread().getName() + System.nanoTime();
    }

    /**
     * 检查字符串是否符合命名规则：只能包含字母、数字、下划线，且只能由下划线和字母开头。
     * @param name ：需要检验的字符串
     * @return ：是否符合命名规范
     */
    public static boolean checkNameFormat(String name){

        //不能为null
        if(name == null || name.length() == 0){
            return false;
        }

        //是否以数字开头
        if(CommonConstant.DIGITS.contains(name.subSequence(0,1))){
            return false;
        }

        //是否只包含字母、数字、下划线
        return StringUtils.containsOnly(name,CommonConstant.DIGITS + CommonConstant.LOWER_CHAR + CommonConstant.UPPER_CHAR + CommonConstant.UNDER_LINE);
    }
}
