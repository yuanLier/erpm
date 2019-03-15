package edu.cqupt.mislab.erp.game.manage.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface GameBasicInfoRepository extends BasicRepository<GameBasicInfo, Long> {

    //选取处于某种状态的比赛信息
    GameBasicInfo findByIdAndGameStatus(long gameId,GameStatus gameStatus);

    //选取处于某个状态的全部比赛信息
    List<GameBasicInfo> findByGameStatus(GameStatus gameStatus);
}