package edu.cqupt.mislab.erp.game.compete.operation.material.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.GameMaterialInfo;

import java.util.List;

/**
 * @author chuyunfei
 * @description
 **/

public interface GameMaterialInfoRepository extends BasicRepository<GameMaterialInfo,Long> {

    /**
     * 选取一场比赛的全部材料信息
     * @param gameId
     * @return
     */
    List<GameMaterialInfo> findByGameBasicInfo_Id(long gameId);
}
