package edu.cqupt.mislab.erp.commons.basic;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * author： chuyunfei date：2019/3/2
 */
@Component
public class ModelInitService implements ApplicationContextAware {

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/2 21:35
     * @Description: 用于协调模块的初始化工作
     **/

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
        this.applicationContext = applicationContext;
    }
}
