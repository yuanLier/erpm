package edu.cqupt.mislab.erp.game.compete.operation.market.dao;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoDevelopingInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketDevelopingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MarketDevelopingInfoRepository extends JpaSpecificationExecutor, JpaRepository<MarketDevelopingInfo, Long> {
}
