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

@Slf4j
@Component
@WebListener
public class ApplicationInitDataListener implements ServletContextListener {

    @Value("${spring.profiles.active}")
    private String activeProfiles;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void contextInitialized(ServletContextEvent sce){

        final Map<String,ModelInit> modelInitMap = applicationContext.getBeansOfType(ModelInit.class);

        final Set<String> keySet = modelInitMap.keySet();

        final Iterator<String> iterator = keySet.iterator();

        while(iterator.hasNext()){

            final String next = iterator.next();

            modelInitMap.get(next).init();
        }
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce){ }
}
