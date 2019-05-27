package edu.cqupt.mislab.erp.game.compete.operation.market.advance;

import edu.cqupt.mislab.erp.commons.constant.FinanceOperationConstant;
import edu.cqupt.mislab.erp.game.compete.basic.ModelAdvance;
import edu.cqupt.mislab.erp.game.compete.operation.finance.service.FinanceService;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketHistoryRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketHistoryInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketStatusEnum;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-13 20:14
 * @description
 */


@Slf4j
@Component
public class MarketAdvance implements ModelAdvance {

    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired
    private MarketDevelopInfoRepository marketDevelopInfoRepository;
    @Autowired
    private MarketHistoryRepository marketHistoryRepository;

    @Autowired
    private FinanceService financeService;


    /**
     * 市场模块比赛期间历史数据 ：
     *      查一下企业拥有的市场开拓信息就好
     *      Iso认证、产品研发同理（产品生产隶属于生产线 所以不在这里考虑）
     * @param gameId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean modelHistory(Long gameId) {

        log.info("开始记录市场模块比赛期间历史数据");

        // 获取该比赛中全部进行中企业
        List<EnterpriseBasicInfo> enterpriseBasicInfoList = enterpriseBasicInfoRepository.findByGameBasicInfo_IdAndEnterpriseStatus(gameId, EnterpriseStatusEnum.PLAYING);

        for(EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfoList) {

            // 获取全部开拓完成的市场信息
            List<MarketDevelopInfo> marketDevelopInfoList = marketDevelopInfoRepository.findByEnterpriseBasicInfo_IdAndMarketStatus(enterpriseBasicInfo.getId(), MarketStatusEnum.DEVELOPED);

            for(MarketDevelopInfo marketDevelopInfo : marketDevelopInfoList) {

                MarketHistoryInfo marketHistoryInfo = new MarketHistoryInfo();

                marketHistoryInfo.setEnterpriseBasicInfo(enterpriseBasicInfo);
                marketHistoryInfo.setMarketBasicInfo(marketDevelopInfo.getMarketBasicInfo());
                marketHistoryInfo.setPeriod(enterpriseBasicInfo.getEnterpriseCurrentPeriod());

                // 记录截止到当前周期，各个企业开拓完成的市场信息
                marketHistoryRepository.save(marketHistoryInfo);
            }
        }

        log.info("市场模块-比赛期间历史数据记录成功");

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean modelAdvance(Long gameId) {

        log.info("开始进行市场模块比赛期间周期推进");

        // 获取该比赛中全部进行中企业
        List<EnterpriseBasicInfo> enterpriseBasicInfoList = enterpriseBasicInfoRepository.findByGameBasicInfo_IdAndEnterpriseStatus(gameId, EnterpriseStatusEnum.PLAYING);

        for (EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfoList) {

            // 获取该企业中全部开拓完成的市场信息
            List<MarketDevelopInfo> marketDevelopInfoList = marketDevelopInfoRepository.findByEnterpriseBasicInfo_IdAndMarketStatus(enterpriseBasicInfo.getId(), MarketStatusEnum.DEVELOPED);

            for (MarketDevelopInfo marketDevelopInfo : marketDevelopInfoList) {

                // 扣除开拓完成后需要支付的维护费用
                Long enterpriseId = enterpriseBasicInfo.getId();
                String changeOperating = FinanceOperationConstant.MARKET_MAINTAIN;
                Double changeAmount = marketDevelopInfo.getMarketBasicInfo().getMarketMaintainCost();
                financeService.updateFinanceInfo(enterpriseId, changeOperating, changeAmount, true);

            }

            // 获取该企业中全部开拓中的市场
            marketDevelopInfoList = marketDevelopInfoRepository.findByEnterpriseBasicInfo_IdAndMarketStatus(enterpriseBasicInfo.getId(), MarketStatusEnum.DEVELOPING);

            for (MarketDevelopInfo marketDevelopInfo : marketDevelopInfoList) {

                // 已经开拓的周期数+1
                marketDevelopInfo.setResearchedPeriod(marketDevelopInfo.getResearchedPeriod()+1);

                // 如果已经开拓的周期数达到了该市场开拓所需的周期数
                if(marketDevelopInfo.getResearchedPeriod() == marketDevelopInfo.getMarketBasicInfo().getMarketResearchPeriod()) {
                    // 设置为已开拓
                    marketDevelopInfo.setMarketStatus(MarketStatusEnum.DEVELOPED);
                }

                // 保存修改
                marketDevelopInfoRepository.save(marketDevelopInfo);

                // 扣除开拓过程中需要支付的费用
                Long enterpriseId = enterpriseBasicInfo.getId();
                String changeOperating = FinanceOperationConstant.MARKET_DEVELOP;
                Double changeAmount = marketDevelopInfo.getMarketBasicInfo().getMarketResearchCost();
                financeService.updateFinanceInfo(enterpriseId, changeOperating, changeAmount, true);

            }
        }

        log.info("市场模块-比赛期间周期推进正常");

        // 运行到这里说明周期推进正常
        return true;
    }
}
