package edu.cqupt.mislab.erp.game.compete.operation.market.gamemodelinit;

import edu.cqupt.mislab.erp.game.compete.basic.GameModelInit;
import edu.cqupt.mislab.erp.game.compete.basic.impl.GameModelInitService;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoDevelopInfo.IsoDevelopInfoBuilder;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo.MarketBasicInfoBuilder;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketDevelopInfo.MarketDevelopInfoBuilder;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class MarketGameModelInit implements GameModelInit {

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/2 20:56
     * @Description: 比赛市场模块初始化
     **/

    @Autowired private GameBasicInfoRepository gameBasicInfoRepository;
    @Autowired private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired private IsoBasicInfoRepository isoBasicInfoRepository;
    @Autowired private IsoDevelopInfoRepository isoDevelopInfoRepository;
    @Autowired private ProductBasicInfoRepository productBasicInfoRepository;
    @Autowired private ProductDevelopInfoRepository productDevelopInfoRepository;
    @Autowired private MarketBasicInfoRepository marketBasicInfoRepository;
    @Autowired private MarketDevelopInfoRepository marketDevelopInfoRepository;

    @Autowired private GameModelInitService gameModelInitService;

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/2 17:37
     * @Description: 初始化Market模块的比赛信息
     **/
    @Override
    public List<String> initGameModel(Long gameId){

        //判断是否已经被初始化过了
        if(gameModelInitService.addInitializedModelIfNotExist(gameId,this)){

            try{
                log.info("开始初始化市场模块的比赛数据");

                //选取所有的基本市场信息
                final List<MarketBasicInfo> marketBasicInfos = marketBasicInfoRepository.findAllNewestApplicationMarketBasicInfos();

                //选取所有的比赛企业，这些企业是绝对存在的
                final List<EnterpriseBasicInfo> enterpriseBasicInfos = enterpriseBasicInfoRepository.findByGameInfo_Id(gameId);

                //为所有的企业生产市场基本信息
                for(EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfos){

                    for(MarketBasicInfo marketBasicInfo : marketBasicInfos){

                        final MarketDevelopInfoBuilder builder = MarketDevelopInfo.builder()
                                .marketBasicInfo(marketBasicInfo)
                                .enterpriseBasicInfo(enterpriseBasicInfo)
                                .marketStatus(marketBasicInfo.getMarketStatus());

                        //特殊状态认证信息处理
                        if(marketBasicInfo.getMarketStatus() == MarketStatusEnum.DEVELOPED){

                            builder.developBeginPeriod(1)
                                    .researchedPeriod(0)
                                    .developEndPeriod(1);
                        }

                        final MarketDevelopInfo marketDevelopInfo = builder.build();

                        //存储该信息
                        marketDevelopInfoRepository.save(marketDevelopInfo);
                    }
                }

                return null;
            }catch(Exception e){
                e.printStackTrace();
            }

            return Collections.singletonList("市场模块比赛数据初始化出错，无法初始化比赛");
        }

        return null;
    }
}
