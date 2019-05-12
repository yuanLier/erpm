package edu.cqupt.mislab.erp.game.compete.basic.impl;

import edu.cqupt.mislab.erp.game.compete.basic.ModelAdvance;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseStatusEnum;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author chuyunfei
 * @description 这个服务用于集合各个模块的周期推进功能，将分散在各个模块的周期推进函数集合起来一起执行
 * @date 15:43 2019/5/2
 **/
@Slf4j
@Service
public class ModelAdvanceService implements ApplicationContextAware {

    /**
     * 用于发现Bean模块
     */
    private ApplicationContext applicationContext;

    @Autowired GameBasicInfoRepository gameBasicInfoRepository;

    @Autowired
    EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;

    public boolean advance(Long gameId){

        final GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);

        //只有被正确初始化的比赛才可以进行比赛推进操作
        if(gameBasicInfo == null || gameBasicInfo.getGameStatus() != GameStatusEnum.PLAYING){
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

        /********************* yyw 2019/5/12 begin ************************/

        log.info("比赛各模块推进成功，企业将进入下一周期...");

        // 获取全部正在比赛中的企业
        List<EnterpriseBasicInfo> enterpriseBasicInfoList = enterpriseBasicInfoRepository.findByGameBasicInfo_IdAndEnterpriseStatus(gameId, EnterpriseStatusEnum.PLAYING);

        // 企业周期+1
        for (EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfoList) {
            enterpriseBasicInfo.setEnterpriseCurrentPeriod(enterpriseBasicInfo.getEnterpriseCurrentPeriod()+1);
        }

        /********************* yyw 2019/5/12 end **************************/

        log.info("推进比赛：" + gameId + " 进入下一个比赛周期成功");

        //默认如果还没有模块开启的话将是推进成功
        return true;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
        this.applicationContext = applicationContext;
    }
}
