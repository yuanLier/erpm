package edu.cqupt.mislab.erp.game.compete.operation.market.service.impl;

import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.market.service.MarketService;
import edu.cqupt.mislab.erp.game.compete.operation.market.util.VoUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MarketServiceImpl implements MarketService {

    @Autowired
    private MarketBasicInfoRepository marketBasicInfoRepository;

    @Autowired
    private MarketDevelopInfoRepository marketDevelopInfoRepository;

    @Override
    public List<MarketDisplayVo> findByEnterpriseId(Long enterpriseId) {

        // 获取entity集
        List<MarketDevelopInfo> marketDevelopInfoList =
                marketDevelopInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId);

        // 非空判断
        if(marketDevelopInfoList == null || marketDevelopInfoList.size() == 0) {
            return null;
        }

        // 转换为vo集
        List<MarketDisplayVo> marketDisplayVoList = new ArrayList<>();
        for (MarketDevelopInfo marketDevelopInfo : marketDevelopInfoList) {
            marketDisplayVoList.add(VoUtil.castEntityToVo(marketDevelopInfo));
        }

        // 返回vo集
        return marketDisplayVoList;
    }

    @Override
    public List<MarketDisplayVo> findByEnterpriseIdAndMarketStatus(Long enterpriseId, MarketStatusEnum marketStatus) {

        // 获取entity集
        List<MarketDevelopInfo> marketDevelopInfoList =
                marketDevelopInfoRepository.findByEnterpriseBasicInfo_IdAndMarketStatus(enterpriseId, marketStatus);

        // 非空判断
        if(marketDevelopInfoList == null || marketDevelopInfoList.size() == 0) {
            return null;
        }

        // 转换为vo集
        List<MarketDisplayVo> marketDisplayVoList = new ArrayList<>();
        for (MarketDevelopInfo marketDevelopInfo : marketDevelopInfoList) {
            marketDisplayVoList.add(VoUtil.castEntityToVo(marketDevelopInfo));
        }

        // 返回vo集
        return marketDisplayVoList;
    }

    @Override
    public MarketDisplayVo updateMarketStatus(Long marketDevelopId, MarketStatusEnum marketStatus) {

        // 根据id查询市场信息
        MarketDevelopInfo marketDevelopInfo = marketDevelopInfoRepository.findOne(marketDevelopId);

        // 非空判断
        if(marketDevelopInfo == null) {
            return null;
        }

        // 修改市场状态
        marketDevelopInfo.setMarketStatus(marketStatus);

        // 保存修改
        marketDevelopInfoRepository.save(marketDevelopInfo);

        // 转换为vo实体并返回
        return VoUtil.castEntityToVo(marketDevelopInfo);
    }

    @Override
    public MarketBasicInfo updateMarketBasicInfo(MarketBasicInfo marketBasicInfo) {
        // 保存修改并返回
        return marketBasicInfoRepository.save(marketBasicInfo);
    }
}
