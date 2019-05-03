package edu.cqupt.mislab.erp.commons.basic.modelinit;

import java.util.List;

/**
 * @author chuyunfei
 * @description 
 * @date 11:42 2019/5/3
 **/

public interface ModelInit {

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/3 13:17
     * @Description: 初始化应用的第一个原始数据，只在应用第一次启用的时候才会被调用
     *                              返回值为出现的错误信息，为null表示初始化是成功的
     **/
    List<String> applicationModelInit();
}
