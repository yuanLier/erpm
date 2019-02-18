package edu.cqupt.mislab.erp.game.compete.operation.market.dao;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoToDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketToDevelopInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MarketToDevelopInfoRepository extends JpaSpecificationExecutor, JpaRepository<MarketToDevelopInfo, Long> {
}
