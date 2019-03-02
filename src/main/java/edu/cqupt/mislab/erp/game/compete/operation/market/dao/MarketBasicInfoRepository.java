package edu.cqupt.mislab.erp.game.compete.operation.market.dao;

import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MarketBasicInfoRepository extends JpaRepository<MarketBasicInfo, Long> {

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/2 21:00
     * @Description: 选取最新的版本的市场基本信息
     **/
    @Query(nativeQuery = true,value = "select * from market_basic_info where id in (select max(id) from market_basic_info group by market_name)")
    List<MarketBasicInfo> findAllNewestApplicationMarketBasicInfos();

}
