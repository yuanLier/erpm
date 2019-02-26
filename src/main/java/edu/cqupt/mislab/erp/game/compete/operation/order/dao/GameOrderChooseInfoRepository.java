package edu.cqupt.mislab.erp.game.compete.operation.order.dao;

import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.GameOrderChooseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GameOrderChooseInfoRepository extends JpaSpecificationExecutor, JpaRepository<GameOrderChooseInfo, Long> {

}
