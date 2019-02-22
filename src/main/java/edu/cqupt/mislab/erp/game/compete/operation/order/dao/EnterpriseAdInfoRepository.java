package edu.cqupt.mislab.erp.game.compete.operation.order.dao;

import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.EnterpriseAdInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EnterpriseAdInfoRepository extends JpaSpecificationExecutor, JpaRepository<EnterpriseAdInfo, Long> {

    //选取某个阶段的排序的订单
    List<EnterpriseAdInfo> findByOrderModelInfoBasicInfo_IdAndFinishedIsFalseOrderByMoneyDescIdAsc(long modelInfoId);
}
