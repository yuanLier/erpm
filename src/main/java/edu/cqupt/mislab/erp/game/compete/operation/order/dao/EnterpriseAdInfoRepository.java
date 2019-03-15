package edu.cqupt.mislab.erp.game.compete.operation.order.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.EnterpriseAdInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EnterpriseAdInfoRepository extends BasicRepository<EnterpriseAdInfo, Long> {

    //获取需要选单的广告
    List<EnterpriseAdInfo> findByEnterpriseBasicInfo_GameBasicInfo_IdAndYearAndProductBasicInfo_IdAndMarketBasicInfo_IdAndFinishedIsFalseOrderByMoneyDescTimeStampAsc(long gameId,int year,long productId,long marketId);
}
