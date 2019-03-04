package edu.cqupt.mislab.erp.game.compete.basic.impl;

import edu.cqupt.mislab.erp.game.compete.basic.GameModelInit;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.material.dao.MaterialBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.MaterialBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class GameModelInitService implements ApplicationContextAware {

    @Autowired private IsoBasicInfoRepository isoBasicInfoRepository;
    @Autowired private MarketBasicInfoRepository marketBasicInfoRepository;
    @Autowired private MaterialBasicInfoRepository materialBasicInfoRepository;

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

        //校验比赛模块各个模块的数据是否符合要求
        List<String> checkWarns = checkGameModelData(gameId);

        if(checkWarns != null){

            return checkWarns;
        }

        log.info("开始初始化比赛：" + gameId);

        //模块初始化信息
        appModelInitMap.put(gameId,new HashSet<>());

        //获取所有的模块的推进函数
        final Map<String,GameModelInit> modelInitMap = applicationContext.getBeansOfType(GameModelInit.class);

        if(modelInitMap != null){

            final Set<String> keySet = modelInitMap.keySet();

            List<String> warns = null;

            for(String next : keySet){

                final GameModelInit gameModelInit = modelInitMap.get(next);

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

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/3 15:53
     * @Description: 校验比赛模块的数据是否符合要求，若不符合将无法进行初始化
     **/
    private List<String> checkGameModelData(Long gameId){

        //ISO认证必须要存在
        final List<IsoBasicInfo> isoBasicInfos = isoBasicInfoRepository.findAllNewestApplicationIsoBasicInfos();

        if(isoBasicInfos == null || isoBasicInfos.size() == 0){
            return Collections.singletonList("ISO认证信息必须存在，否则无法初始化比赛!");
        }

        //市场基本数据信息必须存在
        final List<MarketBasicInfo> marketBasicInfos = marketBasicInfoRepository.findAllNewestApplicationMarketBasicInfos();

        if(marketBasicInfos == null || marketBasicInfos.size() == 0){
            return Collections.singletonList("市场信息必须存在，否则无法初始化比赛!");
        }

        //材料基本数据必须存在
        final List<MaterialBasicInfo> materialBasicInfos = materialBasicInfoRepository.findNewestMaterialBasicInfos();

        if(materialBasicInfos == null || materialBasicInfos.size() == 0){
            return Collections.singletonList("材料信息必须存在，否则无法初始化比赛!");
        }

        //todo


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
