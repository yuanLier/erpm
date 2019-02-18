package edu.cqupt.mislab.erp.game.compete.basic.impl;

import edu.cqupt.mislab.erp.game.compete.basic.GameModelInit;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;

@Slf4j
@Service
public class GameModelInitService implements GameModelInit , ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired private GameBasicInfoRepository gameBasicInfoRepository;

    @Override
    public boolean initGameModel(Long gameId){

        final GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);

        //只有被正确初始化的比赛才可以进行比赛推进操作
        if(gameBasicInfo == null || gameBasicInfo.getGameStatus() != GameStatus.CREATE){
            return false;
        }

        log.info("开始初始化比赛：" + gameId);

        //获取所有的模块的推进函数
        final Map<String,GameModelInit> modelInitMap = applicationContext.getBeansOfType(GameModelInit.class);

        if(modelInitMap != null && modelInitMap.size() > 0){

            final Iterator<String> iterator = modelInitMap.keySet().iterator();

            while(iterator.hasNext()){

                final GameModelInit gameModelInit = modelInitMap.get(iterator.next());

                //只要有一个模块没有被推进完成将无法完成周期的推进
                if(!gameModelInit.initGameModel(gameId)){

                    log.error("初始化比赛：" + gameId + " 失败");

                    return false;
                }
            }
        }

        log.info("初始化比赛：" + gameId + " 完成");

        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
        this.applicationContext = applicationContext;
    }
}
