package edu.cqupt.mislab.erp.commons.listener;

import edu.cqupt.mislab.erp.commons.appinit.dao.AppInitTokenRepository;
import edu.cqupt.mislab.erp.commons.appinit.model.AppInitToken;
import edu.cqupt.mislab.erp.commons.basic.modelinit.ModelInitService;
import edu.cqupt.mislab.erp.game.compete.operation.order.service.OrderChooseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.UUID;

/**
 * 初始化应用的数据，注意JPA的DDL模式必须为create才可以配合使用，目前是开发阶段，运行阶段将使用另外的方式
 * 1、依靠ModeInit接口实现对模块的基本元数据进行初始化
 */
@Slf4j
@Component
@WebListener
public class ApplicationInitListener implements ServletContextListener, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired private ModelInitService modelInitService;
    @Autowired private AppInitTokenRepository appInitTokenRepository;
    @Autowired private OrderChooseService orderChooseService;

    @Override
    public void contextInitialized(ServletContextEvent sce){

        //初始化应用信息
        if(appInitTokenRepository.findAll().size() == 0){

            if(!modelInitService.applicationModelInit()){

                log.error("应用的原始数据初始化出现错误，请联系开发人员");

                //直接退出，不进行下面的初始化了
                System.exit(0);
            }

            AppInitToken appInitToken = new AppInitToken();

            appInitToken.setToken(UUID.randomUUID().toString());

            appInitTokenRepository.save(appInitToken);

            return;
        }

        //提取订单半成品信息，防止半成品信息造成数据混乱
        orderChooseService.loadOrderChooseInfoFromDatabase();

    }
    @Override
    public void contextDestroyed(ServletContextEvent sce){ }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
        this.applicationContext = applicationContext;
    }
}