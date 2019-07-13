package edu.cqupt.mislab.erp.game.compete.operation.finance.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.entity.GameLoanBasicInfo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-07-11 16:44
 * @description
 */
public interface GameLoanBasicInfoRepository extends BasicRepository<GameLoanBasicInfo, Long> {


    /**
     * 获取某场比赛中使用的贷款基本信息
     * @param gameId
     * @return
     */
    List<GameLoanBasicInfo> findByGameBasicInfo_Id(Long gameId);

}
