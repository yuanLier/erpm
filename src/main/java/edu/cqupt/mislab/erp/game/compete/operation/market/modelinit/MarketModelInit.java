package edu.cqupt.mislab.erp.game.compete.operation.market.modelinit;

import edu.cqupt.mislab.erp.commons.basic.ModelInit;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MarketModelInit implements ModelInit {

    @Autowired private MarketBasicInfoRepository marketBasicInfoRepository;

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/2 20:43
     * @Description: 初始化市场模块的基本数据信息
     **/

    @Override
    public boolean init(){

        log.info("开始初始化Market模块的基本数据信息");

        initMarketBasicInfo();

        log.info("初始化Market模块的基本数据信息完成");

        return true;
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
                .marketResearchCost(0.5)
                .marketMaintainCost(0.2)
                .marketStatus(MarketStatusEnum.DEVELOPED)
                .build());
        marketBasicInfoRepository.save(MarketBasicInfo.builder()
                .marketName("区域市场")
                .marketResearchPeriod(2)
                .marketResearchCost(0.5)
                .marketMaintainCost(0.2)
                .marketStatus(MarketStatusEnum.TODEVELOP)
                .build());
        marketBasicInfoRepository.save(MarketBasicInfo.builder()
                .marketName("国内市场")
                .marketResearchPeriod(2)
                .marketResearchCost(0.5)
                .marketMaintainCost(0.2)
                .marketStatus(MarketStatusEnum.TODEVELOP)
                .build());
        marketBasicInfoRepository.save(MarketBasicInfo.builder()
                .marketName("国际市场")
                .marketResearchPeriod(2)
                .marketResearchCost(0.5)
                .marketMaintainCost(0.2)
                .marketStatus(MarketStatusEnum.TODEVELOP)
                .build());
    }
}
