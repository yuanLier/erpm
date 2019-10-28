package edu.cqupt.mislab.erp.commons.basic.modelinit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author： chuyunfei
 * @date：2019/3/2
 */

@Slf4j
@Component
public class ModelInitService implements ApplicationContextAware {

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/2 21:35
     * @Description: 用于协调模块的初始化工作
     **/

    private ApplicationContext applicationContext;

    /**
     * 用于记录已经被初始化过的模块信息，该服务只有在容器初始化时才会被调用，所以是多线程安全的，不用进行并发控制
     */
    private Set<ModelInit> models = new HashSet<>();

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/3 13:53
     * @Description: 初始化应用，该方法供外界调用
     */
    public boolean applicationModelInit(){

        //获取全部需要初始化的模块
        final Map<String,ModelInit> beans = applicationContext.getBeansOfType(ModelInit.class);

        if(beans != null){

            //迭代初始化所有的模块
            final Set<String> keySet = beans.keySet();

            for(String next : keySet){

                final ModelInit modelInit = beans.get(next);

                //初始化每个模块的原始数据
                final List<String> strings = modelInit.applicationModelInit();

                if(strings != null){

                    //出现错误
                    log.error(strings.toString());

                    return false;
                }
            }
        }

        // 走到这里说明初始化正常完成
        //去除中间信息，GC掉
        models = null;

        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
        this.applicationContext = applicationContext;
    }

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/3 13:50
     * @Description: 判断某个模块是否已经被初始化完全了，如果没有被初始化就添加进去
     **/
    public boolean addInitializedModelIfNotExist(ModelInit modelInit){

        if(!models.contains(modelInit)){

            models.add(modelInit);

            return true;
        }

        return false;
    }
}
