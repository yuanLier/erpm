package edu.cqupt.mislab.erp.game.compete.operation.produce.gamemodelinit;

import edu.cqupt.mislab.erp.game.compete.basic.GameModelInit;
import edu.cqupt.mislab.erp.game.compete.basic.impl.GameModelInitService;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryHoldingInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.GameProdlineBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineHoldingInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineProduceInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.*;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.gamemodelinit.ProductGameModelInit;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo;
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
 * @create 2019-03-26 21:18
 * @description
 */
@Slf4j
@Component
public class ProdlineGameModelInit implements GameModelInit {

    @Autowired
    private GameModelInitService gameModelInitService;

    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired
    private GameBasicInfoRepository gameBasicInfoRepository;

    @Autowired
    private ProdlineBasicInfoRepository prodlineBasicInfoRepository;
    @Autowired
    private GameProdlineBasicInfoRepository gameProdlineBasicInfoRepository;
    @Autowired
    private ProdlineHoldingInfoRepository prodlineHoldingInfoRepository;
    @Autowired
    private ProdlineProduceInfoRepository prodlineProduceInfoRepository;
    @Autowired
    private ProductDevelopInfoRepository productDevelopInfoRepository;
    @Autowired
    private FactoryHoldingInfoRepository factoryHoldingInfoRepository;

    @Autowired
    private FactoryGameModelInit factoryGameModelInit;
    @Autowired
    private ProductGameModelInit productGameModelInit;


    @Override
    public List<String> initGameModel(Long gameId) {
        // 判断是否已经被初始化过了
        if(gameModelInitService.addInitializedModelIfNotExist(gameId,this)){

            // 企业中生产线初始化的先决条件
            List<String> preCheckWarns = preProdlineGameModel(gameId);

            if(preCheckWarns != null){

                return preCheckWarns;
            }

            try{
                log.info("开始初始化产品模块的比赛数据");

                // 在比赛开始时初始化本场比赛中使用的生产线基本信息
                GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);

                // 初始化当前设定下的生产线基本信息
                List<ProdlineBasicInfo> prodlineBasicInfoList = prodlineBasicInfoRepository.findNewestProdlineBasicInfos();
                for(ProdlineBasicInfo prodlineBasicInfo : prodlineBasicInfoList) {
                    gameProdlineBasicInfoRepository.save(
                            GameProdlineBasicInfo.builder()
                                    .gameBasicInfo(gameBasicInfo)
                                    .prodlineBasicInfo(prodlineBasicInfo)
                                    .build()
                    );
                }

                // 随机选取一条生产线基本数据信息
                List<GameProdlineBasicInfo> prodlineBasicInfos = gameProdlineBasicInfoRepository.findByGameBasicInfo_Id(gameId);
                final GameProdlineBasicInfo prodlineBasicInfo = prodlineBasicInfos.get(new Random().nextInt(prodlineBasicInfos.size()));

                // 选取全部的企业
                final List<EnterpriseBasicInfo> enterpriseBasicInfos = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId);

                // 为所有企业自带的那条厂房初始化一条生产线
                for(EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfos){

                    // 获取这个企业初始时候默认研发完成的产品和生产线
                    List<ProductDevelopInfo> productDevelopInfoList = productDevelopInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseBasicInfo.getId());
                    List<FactoryHoldingInfo> factoryHoldingInfoList = factoryHoldingInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseBasicInfo.getId());

                    // 若初始时候没有默认研发完成的产品或厂房
                    if(productDevelopInfoList.size() == 0 || factoryHoldingInfoList.size() == 0) {
                        // 就不分配默认的生产线了
                        return null;
                    }

                    // 否则，随机选一个厂房和产品进行研发（如果初始时默认研发完成的产品or默认修建完成的厂房有多个的话，就可能每个企业不一样 是不是很刺激
                    ProductDevelopInfo productDevelopInfo = productDevelopInfoList.get(new Random().nextInt(productDevelopInfoList.size()));
                    FactoryHoldingInfo factoryHoldingInfo = factoryHoldingInfoList.get(new Random().nextInt(factoryHoldingInfoList.size()));

                    ProdlineHoldingInfo prodlineHoldingInfo =
                            prodlineHoldingInfoRepository.saveAndFlush(
                                ProdlineHoldingInfo.builder()
                                        .enterpriseBasicInfo(enterpriseBasicInfo)
                                        .prodlineBasicInfo(prodlineBasicInfo)
                                        .factoryHoldingInfo(factoryHoldingInfo)
                                        .prodlineHoldingStatus(ProdlineHoldingStatus.PRODUCING)
                                        .beginPeriod(1)
                                        .endPeriod(null)
                                        .build()
                            );

                    prodlineProduceInfoRepository.save(
                            ProdlineProduceInfo.builder()
                                    .prodlineHoldingInfo(prodlineHoldingInfo)
                                    .productDevelopInfo(productDevelopInfo)
                                    .produceDuration(0)
                                    .beginPeriod(null)
                                    .endPeriod(null)
                                    .producedPeriod(0)
                                    .prodlineProduceStatus(ProdlineProduceStatus.TOPRODUCE)
                                    .build()
                    );
                }

                log.info("初始化产品模块的比赛数据完成");

                return null;
            }catch(Exception e){
                e.printStackTrace();
            }
            return Collections.singletonList("产品比赛模块数据初始化出错，无法初始化比赛");
        }

        return null;
    }

    
    /**
     * @author yuanyiwen
     * @description 企业中的生产线初始化的先决条件是企业中的厂房与产品都已经初始化过了
     * @date 21:34 2019/3/26
     **/
    private List<String> preProdlineGameModel(Long gameId){

        List<String> factoryInitMessage = factoryGameModelInit.initGameModel(gameId);
        List<String> productInitMessage = productGameModelInit.initGameModel(gameId);

        if(factoryInitMessage == null && productInitMessage == null) {
            return null;
        }

        return (factoryInitMessage != null) ? factoryInitMessage : productInitMessage;
    }
}
