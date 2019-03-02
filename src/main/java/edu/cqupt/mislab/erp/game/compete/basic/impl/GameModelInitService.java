package edu.cqupt.mislab.erp.game.compete.basic.impl;

import edu.cqupt.mislab.erp.game.compete.basic.GameModelInit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class GameModelInitService implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * 用于控制比赛模块的初始化，防止某个模块未初始化或者重复初始化
     * Long：用于区分比赛
     * Set：用于记录已经初始化完成的比赛模块
     **/
    private Map<Long,Set<GameModelInit>> appModelInitMap = new ConcurrentHashMap<>();

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/1 20:24
     * @Description: 用于外界服务调用的接口，所有的比赛初始化通过这个服务进行初始化，被初始化的比赛必须满足必要条件
     * 返回值为null时标识初始化成功，否则每一条信息为一个错误原因
     **/
    public List<String> initGameModel(Long gameId){

        log.info("开始初始化比赛：" + gameId);

        //模块初始化信息
        appModelInitMap.put(gameId,new HashSet<>());

        //获取所有的模块的推进函数
        final Map<String,GameModelInit> modelInitMap = applicationContext.getBeansOfType(GameModelInit.class);

        if(modelInitMap != null && modelInitMap.size() > 0){

            final Iterator<String> iterator = modelInitMap.keySet().iterator();

            List<String> warns = null;

            while(iterator.hasNext()){

                final GameModelInit gameModelInit = modelInitMap.get(iterator.next());

                final List<String> oneWarns = gameModelInit.initGameModel(gameId);
                //只要有一个模块没有被推进完成将无法完成周期的推进
                if(oneWarns != null){

                    log.error("初始化比赛：" + gameId + " 失败");

                    //记录错误
                    warns = oneWarns;

                    //没必要继续初始化了
                    break;
                }
            }

            //是否初始化完成
            if(warns != null){

                //移除初始化的信息
                appModelInitMap.remove(gameId);

                return warns;
            }
        }

        log.info("初始化比赛：" + gameId + " 完成");

        //移除初始化的信息
        appModelInitMap.remove(gameId);

        return null;
    }

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/2 19:47
     * @Description: 判断某个比赛的某个模块是否已经被初始化完成，这个gameId必须存在才行，一定要保证；该方法只能在单线程使用
     * 也就是说比赛初始化是单线程任务，不允许多线程初始化
     **/
    public boolean addInitializedModelIfNotExist(Long gameId,GameModelInit model){

        final Set<GameModelInit> modelInits = appModelInitMap.get(gameId);

        if(modelInits != null){

            if(modelInits.contains(model)){
                return false;
            }

            modelInits.add(model);

            return true;
        }

        //执行到这里来了说明代码逻辑错误了
        log.error("代码出错了！");

        return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
        this.applicationContext = applicationContext;
    }
}
