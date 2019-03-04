package edu.cqupt.mislab.erp.game.compete.operation.material.dao;

import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.GameMaterialInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameMaterialInfoRepository extends JpaRepository<GameMaterialInfo,Long> {

    //选取一场比赛的全部材料信息
    List<GameMaterialInfo> findByGameBasicInfo_Id(long gameId);
}
