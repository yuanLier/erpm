package edu.cqupt.mislab.erp.game.manage.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameStatusEnum;

import java.util.List;

/**
 * @author chuyunfei
 * @description
 * @date 21:04 2019/4/26
 **/

public interface GameBasicInfoRepository extends BasicRepository<GameBasicInfo, Long> {

    /**
     * @author chuyunfei
     * @description 选取处于某种状态的比赛信息
     * @date 21:03 2019/4/26
     **/
    GameBasicInfo findByIdAndGameStatus(long gameId,GameStatusEnum gameStatusEnum);

    /**
     * @author chuyunfei
     * @description 选取处于某个状态的全部比赛信息
     * @date 21:03 2019/4/26
     **/
    List<GameBasicInfo> findByGameStatus(GameStatusEnum gameStatusEnum);


    /**
     * @author yuanyiwen
     * @description 获取一个用户创建的全部比赛
     * @date 17:33 2019/7/30
     **/
    List<GameBasicInfo> findByUserStudentInfo_Id(Long userId);
}