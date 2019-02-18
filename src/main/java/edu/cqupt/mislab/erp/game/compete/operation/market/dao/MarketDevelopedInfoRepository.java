package edu.cqupt.mislab.erp.game.compete.operation.market.dao;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoDevelopedInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketDevelopedInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MarketDevelopedInfoRepository extends JpaSpecificationExecutor, JpaRepository<MarketDevelopedInfo, Long> {
}
