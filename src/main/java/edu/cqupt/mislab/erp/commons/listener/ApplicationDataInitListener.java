package edu.cqupt.mislab.erp.commons.listener;

import edu.cqupt.mislab.erp.commons.basic.ModelInit;
import edu.cqupt.mislab.erp.commons.basic.ModelInitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 初始化应用的数据，注意JPA的DDL模式必须为create才可以配合使用，目前是开发阶段，运行阶段将使用另外的方式
 * 1、依靠ModeInit接口实现对模块的基本元数据进行初始化
 */
@Slf4j
@Component
@WebListener
public class ApplicationDataInitListener implements ServletContextListener, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired private ModelInitService modelInitService;

    @Override
    public void contextInitialized(ServletContextEvent sce){

        if(!modelInitService.applicationModelInit()){

            log.error("应用的原始数据初始化出现错误，请联系开发人员");

            //直接退出，不进行下面的初始化了
            System.exit(0);
        }
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce){ }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
        this.applicationContext = applicationContext;
    }
}
