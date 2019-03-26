package edu.cqupt.mislab.erp.game.compete.operation.produce.gamemodelinit;

import edu.cqupt.mislab.erp.game.compete.basic.GameModelInit;
import edu.cqupt.mislab.erp.game.compete.basic.impl.GameModelInitService;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryHoldingInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingStatus;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author yuanyiwen
 * @create 2019-03-26 20:24
 * @description
 */
@Slf4j
@Component
public class FactoryGameModelInit implements GameModelInit {

    @Autowired
    private GameModelInitService gameModelInitService;

    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired
    private FactoryBasicInfoRepository factoryBasicInfoRepository;
    @Autowired
    private FactoryHoldingInfoRepository factoryHoldingInfoRepository;


    @Override
    public List<String> initGameModel(Long gameId) {
        //判断是否已经被初始化过了
        if(gameModelInitService.addInitializedModelIfNotExist(gameId,this)){

            try{

                log.info("开始初始化厂房模块的比赛数据");

                // 随机获取一条基本厂房信息
                List<FactoryBasicInfo> factoryBasicInfos = factoryBasicInfoRepository.findNewestFactoryBasicInfos();
                final FactoryBasicInfo factoryBasicInfo = factoryBasicInfos.get(new Random().nextInt(factoryBasicInfos.size()));

                // 选取所有的比赛企业，这些企业是绝对存在的
                final List<EnterpriseBasicInfo> enterpriseBasicInfos = enterpriseBasicInfoRepository.findByGameInfo_Id(gameId);

                // 为所有的企业生成一条厂房
                for(EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfos){

                    factoryHoldingInfoRepository.save(
                            FactoryHoldingInfo.builder()
                                    .enterpriseBasicInfo(enterpriseBasicInfo)
                                    .factoryBasicInfo(factoryBasicInfo)
                                    .factoryHoldingStatus(FactoryHoldingStatus.HOLDING)
                                    .beginPeriod(1)
                                    .endPeriod(1)
                                    .enable(true)
                                    .build()
                    );
                }

                return null;
            }catch(Exception e){
                e.printStackTrace();
            }

            return Collections.singletonList("厂房模块比赛数据初始化出错");
        }

        return null;
    }


}
