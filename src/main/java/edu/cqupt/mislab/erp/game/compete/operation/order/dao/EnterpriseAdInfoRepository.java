package edu.cqupt.mislab.erp.game.compete.operation.order.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.EnterpriseAdInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EnterpriseAdInfoRepository extends BasicRepository<EnterpriseAdInfo, Long> {

    //选取某场比赛中某年广告的排序
    List<EnterpriseAdInfo> findByEnterpriseBasicInfo_GameBasicInfo_IdAndYearOrderByMoneyDescTimeStampAsc(long gameId,int year);

    //选取某场比赛中仍然需要进行排序的广告
    List<EnterpriseAdInfo> findByEnterpriseBasicInfo_GameBasicInfo_IdAndYearAndFinishedIsFalseOrderByMoneyDescTimeStampAsc(long gameId,int year);
}
