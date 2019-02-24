package edu.cqupt.mislab.erp.game.compete.operation.material.dao;

import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.MaterialBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MaterialBasicRepository extends JpaSpecificationExecutor, JpaRepository<MaterialBasicInfo, Long> {
}
