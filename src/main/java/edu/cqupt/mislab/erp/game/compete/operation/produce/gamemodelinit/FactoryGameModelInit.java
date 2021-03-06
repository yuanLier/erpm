package edu.cqupt.mislab.erp.game.compete.operation.produce.gamemodelinit;

import edu.cqupt.mislab.erp.game.compete.basic.GameModelInit;
import edu.cqupt.mislab.erp.game.compete.basic.impl.GameModelInitService;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryHoldingInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.GameFactoryBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingStatus;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.GameFactoryBasicInfo;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
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
    private GameBasicInfoRepository gameBasicInfoRepository;
    @Autowired
    private GameFactoryBasicInfoRepository gameFactoryBasicInfoRepository;
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

                // 在比赛开始时初始化本场比赛中使用的厂房基本信息
                GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);

                // 初始化当前设定下的厂房基本信息
                List<FactoryBasicInfo> factoryBasicInfoList = factoryBasicInfoRepository.findNewestFactoryBasicInfos();
                for(FactoryBasicInfo factoryBasicInfo : factoryBasicInfoList) {
                    gameFactoryBasicInfoRepository.save(
                            GameFactoryBasicInfo.builder()
                                    .gameBasicInfo(gameBasicInfo)
                                    .factoryBasicInfo(factoryBasicInfo)
                                    .build()
                    );
                }

                // 随机获取一条基本厂房信息 TODO 拓展为管理员可控的，让管理员选择默认建造的是哪种厂房，以及默认建造的数量
                List<GameFactoryBasicInfo> factoryBasicInfos = gameFactoryBasicInfoRepository.findByGameBasicInfo_Id(gameId);
                final GameFactoryBasicInfo factoryBasicInfo = factoryBasicInfos.get(new Random().nextInt(factoryBasicInfos.size()));

                // 选取所有的比赛企业，这些企业是绝对存在的
                final List<EnterpriseBasicInfo> enterpriseBasicInfos = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId);

                // 为所有的企业生成一条厂房
                for(EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfos){

                    factoryHoldingInfoRepository.save(
                            FactoryHoldingInfo.builder()
                                    .enterpriseBasicInfo(enterpriseBasicInfo)
                                    .factoryBasicInfo(factoryBasicInfo)
                                    .factoryHoldingStatus(FactoryHoldingStatus.HOLDING)
                                    .beginPeriod(1)
                                    .endPeriod(null)
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
