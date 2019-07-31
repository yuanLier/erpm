package edu.cqupt.mislab.erp.game.compete.operation.market.service.impl;

import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.market.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanyiwen
 * @description
 **/

@Service
public class MarketServiceImpl implements MarketService {

    @Autowired
    private MarketDevelopInfoRepository marketDevelopInfoRepository;

    @Override
    public List<MarketTypeVo> getAllMarketTypes(Long enterpriseId) {

        List<MarketDevelopInfo> marketDevelopInfoList =
                marketDevelopInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId);

        List<MarketTypeVo> marketTypeVoList = new ArrayList<>();
        for(MarketDevelopInfo marketDevelopInfo : marketDevelopInfoList) {
            MarketBasicInfo marketBasicInfo = marketDevelopInfo.getMarketBasicInfo();

            MarketTypeVo marketTypeVo = new MarketTypeVo();
            marketTypeVo.setId(marketBasicInfo.getId());
            marketTypeVo.setMarketName(marketBasicInfo.getMarketName());

            marketTypeVoList.add(marketTypeVo);
        }

        return marketTypeVoList;
    }

    @Override
    public List<MarketDisplayVo> findByEnterpriseId(Long enterpriseId) {

        // 获取entity集
        List<MarketDevelopInfo> marketDevelopInfoList =
                marketDevelopInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId);

        // 转换为vo集
        List<MarketDisplayVo> marketDisplayVoList = new ArrayList<>();
        for (MarketDevelopInfo marketDevelopInfo : marketDevelopInfoList) {
            marketDisplayVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(marketDevelopInfo));
        }

        // 返回vo集
        return marketDisplayVoList;
    }

    @Override
    public List<MarketDisplayVo> findByEnterpriseIdAndMarketStatus(Long enterpriseId, MarketStatusEnum marketStatus) {

        // 获取entity集
        List<MarketDevelopInfo> marketDevelopInfoList =
                marketDevelopInfoRepository.findByEnterpriseBasicInfo_IdAndMarketStatus(enterpriseId, marketStatus);

        // 转换为vo集
        List<MarketDisplayVo> marketDisplayVoList = new ArrayList<>();
        for (MarketDevelopInfo marketDevelopInfo : marketDevelopInfoList) {
            marketDisplayVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(marketDevelopInfo));
        }

        // 返回vo集
        return marketDisplayVoList;
    }

    @Override
    public MarketDisplayVo startMarketDevelop(Long marketDevelopId) {

        // 根据id查询市场信息
        MarketDevelopInfo marketDevelopInfo = marketDevelopInfoRepository.findOne(marketDevelopId);

        // 修改市场状态
        marketDevelopInfo.setMarketStatus(MarketStatusEnum.DEVELOPING);
        // 设置市场开拓的开始周期与已开拓周期
        marketDevelopInfo.setDevelopBeginPeriod(marketDevelopInfo.getEnterpriseBasicInfo().getEnterpriseCurrentPeriod());
        marketDevelopInfo.setResearchedPeriod(0);

        // 保存修改
        marketDevelopInfoRepository.save(marketDevelopInfo);

        // 转换为vo实体并返回
        return EntityVoUtil.copyFieldsFromEntityToVo(marketDevelopInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MarketDisplayVo updateMarketStatus(Long marketDevelopId, MarketStatusEnum marketStatus) {

        // 根据id查询市场信息
        MarketDevelopInfo marketDevelopInfo = marketDevelopInfoRepository.findOne(marketDevelopId);

        // 修改市场状态
        marketDevelopInfo.setMarketStatus(marketStatus);

        // 保存修改
        marketDevelopInfoRepository.save(marketDevelopInfo);

        // 转换为vo实体并返回
        return EntityVoUtil.copyFieldsFromEntityToVo(marketDevelopInfo);
    }

}
