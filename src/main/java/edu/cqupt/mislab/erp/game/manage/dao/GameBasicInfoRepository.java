package edu.cqupt.mislab.erp.game.manage.dao;

import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface GameBasicInfoRepository extends JpaSpecificationExecutor<GameBasicInfo>, JpaRepository<GameBasicInfo, Long> {

}