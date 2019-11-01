package edu.cqupt.mislab.erp.game.compete.operation.material.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.GameMaterialBasicInfo;

import java.util.List;

/**
 * @author chuyunfei
 * @description
 **/

public interface GameMaterialInfoRepository extends BasicRepository<GameMaterialBasicInfo,Long> {

    /**
     * 选取一场比赛的全部材料信息
     * @param gameId
     * @return
     */
    List<GameMaterialBasicInfo> findByGameBasicInfo_Id(long gameId);
}
