package edu.cqupt.mislab.erp.game.compete.operation.order.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.GameOrderChooseInfo;

/**
 * @author chuyunfei
 * @description
 **/

public interface GameOrderChooseInfoRepository extends BasicRepository<GameOrderChooseInfo, Long> {

    /**
     * 选取某个比赛某年的订单选择信息
     * @param gameId
     * @param year
     * @return
     */
    GameOrderChooseInfo findByGameBasicInfo_IdAndYear(long gameId,int year);

}
