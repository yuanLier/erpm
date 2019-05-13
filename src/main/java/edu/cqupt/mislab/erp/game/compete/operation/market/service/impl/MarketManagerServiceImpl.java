package edu.cqupt.mislab.erp.game.compete.operation.market.service.impl;

import edu.cqupt.mislab.erp.commons.util.BeanCopyUtil;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.dto.MarketBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.market.service.MarketManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public MarketBasicVo updateMarketBasicInfo(MarketBasicDto marketBasicDto) {
        // 将接受到的dto中的数据复制给marketBasicInfo
        MarketBasicInfo marketBasicInfo = new MarketBasicInfo();
        BeanCopyUtil.copyPropertiesSimple(marketBasicDto,marketBasicInfo);

        // 保存修改
        marketBasicInfo = marketBasicInfoRepository.save(marketBasicInfo);

        // 将获取了新id的info数据复制给marketBasicVo
        MarketBasicVo marketBasicVo = new MarketBasicVo();
        BeanCopyUtil.copyPropertiesSimple(marketBasicInfo, marketBasicVo);

        // 返回vo
        return marketBasicVo;
    }


}
