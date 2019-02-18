package edu.cqupt.mislab.erp.game.compete.basic.impl;

import edu.cqupt.mislab.erp.game.compete.basic.ModelAdvance;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.Map;

/**
 * 这个服务用于集合各个模块的周期推进功能，将分散在各个模块的周期推进函数集合起来一起执行
 */
@Slf4j
@Service
public class ModelAdvanceService implements ModelAdvance , ApplicationContextAware {

    //用于发现Bean模块
    private ApplicationContext applicationContext;

    @Autowired GameBasicInfoRepository gameBasicInfoRepository;

    @Override
    public boolean advance(Long gameId){

        final GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);

        //只有被正确初始化的比赛才可以进行比赛推进操作
        if(gameBasicInfo == null || gameBasicInfo.getGameStatus() != GameStatus.PLAYING){
            return false;
        }

        log.info("开始推进比赛：" + gameId + " 进入下一个比赛周期");

        //获取所有的模块的推进函数
        final Map<String,ModelAdvance> advanceMap = applicationContext.getBeansOfType(ModelAdvance.class);

        if(advanceMap != null && advanceMap.size() > 0){

            final Iterator<String> iterator = advanceMap.keySet().iterator();

            while(iterator.hasNext()){

                final ModelAdvance modelAdvance = advanceMap.get(iterator.next());

                //只要有一个模块没有被推进完成将无法完成周期的推进
                if(!modelAdvance.advance(gameId)){

                    log.info("推进比赛：" + gameId + " 进入下一个比赛周期失败");

                    return false;
                }
            }
        }

        log.info("推进比赛：" + gameId + " 进入下一个比赛周期成功");

        //默认如果还没有模块开启的话将是推进成功
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
        this.applicationContext = applicationContext;
    }
}
