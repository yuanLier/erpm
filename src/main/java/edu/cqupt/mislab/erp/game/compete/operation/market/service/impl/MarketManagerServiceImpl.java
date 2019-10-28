package edu.cqupt.mislab.erp.game.compete.operation.market.service.impl;

import edu.cqupt.mislab.erp.commons.aspect.BadModificationException;
import edu.cqupt.mislab.erp.commons.util.BeanCopyUtil;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.dto.MarketBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.market.service.MarketManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * @author yuanyiwen
 * @create 2019-05-13 19:55
 * @description
 */

@Service
public class MarketManagerServiceImpl implements MarketManagerService {


    @Autowired
    private MarketBasicInfoRepository marketBasicInfoRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MarketBasicVo addMarketBasicInfo(MarketBasicDto marketBasicDto) {

        // 将接受到的dto中的数据复制给marketBasicInfo
        MarketBasicInfo marketBasicInfo = new MarketBasicInfo();
        BeanCopyUtil.copyPropertiesSimple(marketBasicDto, marketBasicInfo);

        // 启用该条设置
        marketBasicInfo.setEnable(true);

        // 保存修改并刷新
        marketBasicInfo = marketBasicInfoRepository.saveAndFlush(marketBasicInfo);

        // 将获取了新id的info数据复制给marketBasicVo
        MarketBasicVo marketBasicVo = new MarketBasicVo();
        BeanCopyUtil.copyPropertiesSimple(marketBasicInfo, marketBasicVo);

        // 返回vo
        return marketBasicVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MarketBasicVo updateMarketBasicInfo(Long marketBasicId, MarketBasicDto marketBasicDto) {

        // 获取之前的市场信息并设置为不启用
        MarketBasicInfo marketBasicInfo = marketBasicInfoRepository.findOne(marketBasicId);
        if(!marketBasicInfo.isEnable()) {
            throw new BadModificationException();
        }
        marketBasicInfo.setEnable(false);

        marketBasicInfoRepository.save(marketBasicInfo);

        // 重新生成一条数据
        MarketBasicInfo newMarketBasicInfo = new MarketBasicInfo();
        BeanCopyUtil.copyPropertiesSimple(marketBasicDto, newMarketBasicInfo);
        // 设置可用
        newMarketBasicInfo.setEnable(true);

        newMarketBasicInfo = marketBasicInfoRepository.saveAndFlush(newMarketBasicInfo);

        MarketBasicVo marketBasicVo = new MarketBasicVo();
        BeanCopyUtil.copyPropertiesSimple(newMarketBasicInfo, marketBasicVo);

        return marketBasicVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MarketBasicVo closeMarketBasicInfo(Long marketBasicId) {

        // 获取这个市场信息
        MarketBasicInfo marketBasicInfo = marketBasicInfoRepository.findOne(marketBasicId);

        // 设置为不启用
        marketBasicInfo.setEnable(false);

        // 保存修改
        marketBasicInfoRepository.save(marketBasicInfo);

        MarketBasicVo marketBasicVo = new MarketBasicVo();
        BeanCopyUtil.copyPropertiesSimple(marketBasicInfo, marketBasicVo);

        return marketBasicVo;
    }

    @Override
    public List<MarketBasicVo> getAllMarketBasicVoOfStatus(boolean enable) {

        List<MarketBasicInfo> marketBasicInfoList = marketBasicInfoRepository.findByEnable(enable);

        List<MarketBasicVo> marketBasicVoList = new ArrayList<>();
        for (MarketBasicInfo marketBasicInfo : marketBasicInfoList) {
            MarketBasicVo marketBasicVo = new MarketBasicVo();
            BeanCopyUtil.copyPropertiesSimple(marketBasicInfo, marketBasicVo);

            marketBasicVoList.add(marketBasicVo);
        }

        return marketBasicVoList;
    }
}
