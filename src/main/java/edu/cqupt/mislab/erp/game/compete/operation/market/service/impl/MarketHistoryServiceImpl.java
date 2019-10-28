package edu.cqupt.mislab.erp.game.compete.operation.market.service.impl;

import edu.cqupt.mislab.erp.commons.util.BeanCopyUtil;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketHistoryRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketHistoryInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketHistoryVo;
import edu.cqupt.mislab.erp.game.compete.operation.market.service.MarketHistoryService;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseBasicInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-15 20:10
 * @description
 */

@Service
public class MarketHistoryServiceImpl implements MarketHistoryService {
    
    @Autowired
    private MarketHistoryRepository marketHistoryRepository;
    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;

    @Override
    public List<MarketHistoryVo> findMarketHistoryVoOfGameAndPeriod(Long gameId, Integer period) {

        List<MarketHistoryVo> marketHistoryVoList = new ArrayList<>();

        // 获取当前比赛中的全部企业
        List<EnterpriseBasicInfo> enterpriseBasicInfoList = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId);
        for (EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfoList) {

            MarketHistoryVo marketHistoryVo = new MarketHistoryVo();
            marketHistoryVo.setEnterpriseBasicInfoVo(new EnterpriseBasicInfoVo(enterpriseBasicInfo.getId(), enterpriseBasicInfo.getEnterpriseName()));
            marketHistoryVo.setPeriod(period);

            // 若该周期该企业已结束经营，则返回结束前最后一周期的开拓情况
            Integer bankruptPeriod = enterpriseBasicInfo.getEnterpriseCurrentPeriod();
            period = (period < bankruptPeriod) ? period : bankruptPeriod;

            // 获取全部市场开拓信息
            List<MarketHistoryInfo> marketHistoryInfoList = marketHistoryRepository.findByEnterpriseBasicInfo_IdAndPeriod(enterpriseBasicInfo.getId(), period);
            // 获取该企业在当前周期的全部的市场开拓历史数据并转化为vo实体集
            List<MarketBasicVo> marketBasicVoList = new ArrayList<>();
            for (MarketHistoryInfo marketHistoryInfo : marketHistoryInfoList) {

                MarketBasicVo marketBasicVo = new MarketBasicVo();
                BeanCopyUtil.copyPropertiesSimple(marketHistoryInfo.getMarketBasicInfo(), marketBasicVo);

                marketBasicVoList.add(marketBasicVo);
            }

            marketHistoryVo.setMarketBasicVoList(marketBasicVoList);

            marketHistoryVoList.add(marketHistoryVo);
        }

        return marketHistoryVoList;
    }

    @Override
    public Integer getTotalPeriod(Long gameId) {

        Integer totalPeriod = 0;

        // 获取当前比赛中的全部企业
        List<EnterpriseBasicInfo> enterpriseBasicInfoList = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId);
        for (EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfoList) {

            Integer currentPeriod = enterpriseBasicInfo.getEnterpriseCurrentPeriod();

            // 其实就是获取企业的最大存活周期
            totalPeriod = (totalPeriod > currentPeriod) ? totalPeriod : currentPeriod;
        }

        return totalPeriod;
    }
}
