package edu.cqupt.mislab.erp.commons.listener;

import edu.cqupt.mislab.erp.commons.basic.ModelInit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
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
 * //todo
 */
@Slf4j
@Component
@WebListener
public class ApplicationDataInitListener implements ServletContextListener {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void contextInitialized(ServletContextEvent sce){

        //获取容器里面所有实现了模块初始化接口的对象，使用这些对象来进行模块的元数据初始化
        final Map<String,ModelInit> modelInitMap = applicationContext.getBeansOfType(ModelInit.class);

        final Set<String> keySet = modelInitMap.keySet();

        final Iterator<String> iterator = keySet.iterator();

        while(iterator.hasNext()){

            final String next = iterator.next();

            //初始化该模块数据
            if(!modelInitMap.get(next).init()){
                throw new RuntimeException("应用初始化数据异常！！！");
            }
        }
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce){ }
}
