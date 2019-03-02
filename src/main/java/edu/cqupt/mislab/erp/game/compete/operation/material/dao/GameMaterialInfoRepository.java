package edu.cqupt.mislab.erp.game.compete.operation.material.dao;

import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.GameMaterialInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameMaterialInfoRepository extends JpaRepository<GameMaterialInfo,Long> {
}
