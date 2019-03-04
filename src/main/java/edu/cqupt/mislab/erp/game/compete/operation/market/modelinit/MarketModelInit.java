package edu.cqupt.mislab.erp.game.compete.operation.market.modelinit;

import edu.cqupt.mislab.erp.commons.basic.ModelInit;
import edu.cqupt.mislab.erp.commons.basic.ModelInitService;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class MarketModelInit implements ModelInit {

    @Autowired private MarketBasicInfoRepository marketBasicInfoRepository;
    @Autowired private ModelInitService modelInitService;

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/2 20:43
     * @Description: 初始化市场模块的基本数据信息
     **/

    @Override
    public List<String> applicationModelInit(){

        //判断当前模块是否已经被初始化过一次了
        if(modelInitService.addInitializedModelIfNotExist(this)){

            try{
                log.info("开始初始化Market模块的基本数据信息");

                //初始化市场信息
                initMarketBasicInfo();

                log.info("初始化Market模块的基本数据信息完成");

                return null;
            }catch(Exception e){
                e.printStackTrace();
            }

            return Collections.singletonList("市场模块应用初始化基本数据类型出错");
        }

        return null;
    }

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/2 20:46
     * @Description: 初始化市场模块的应用基本数据信息
     **/
    private void initMarketBasicInfo(){

        marketBasicInfoRepository.save(MarketBasicInfo.builder()
                .marketName("本地市场")
                .marketResearchPeriod(2)
                .marketResearchCost(1.5)
                .marketMaintainCost(0.5)
                .marketStatus(MarketStatusEnum.DEVELOPED)
                .enable(true)
                .build());
        marketBasicInfoRepository.save(MarketBasicInfo.builder()
                .marketName("区域市场")
                .marketResearchPeriod(3)
                .marketResearchCost(2)
                .marketMaintainCost(1)
                .marketStatus(MarketStatusEnum.TODEVELOP)
                .enable(true)
                .build());
        marketBasicInfoRepository.save(MarketBasicInfo.builder()
                .marketName("国内市场")
                .marketResearchPeriod(4)
                .marketResearchCost(2.5)
                .marketMaintainCost(1.5)
                .marketStatus(MarketStatusEnum.TODEVELOP)
                .enable(true)
                .build());
        marketBasicInfoRepository.save(MarketBasicInfo.builder()
                .marketName("国际市场")
                .marketResearchPeriod(5)
                .marketResearchCost(3)
                .marketMaintainCost(2)
                .marketStatus(MarketStatusEnum.TODEVELOP)
                .enable(true)
                .build());
    }
}
