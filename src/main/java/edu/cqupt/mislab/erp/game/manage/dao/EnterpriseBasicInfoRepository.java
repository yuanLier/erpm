package edu.cqupt.mislab.erp.game.manage.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseStatusEnum;

import java.util.List;

/**
 * @author chuyunfei
 * @description 
 * @date 20:54 2019/4/26
 **/

public interface EnterpriseBasicInfoRepository extends BasicRepository<EnterpriseBasicInfo, Long> {

    /**
     * @author chuyunfei
     * @description 判断某个企业是否处于某个状态
     * @date 20:54 2019/4/26
     **/
    EnterpriseBasicInfo findByIdAndEnterpriseStatus(long enterpriseId,EnterpriseStatusEnum status);

    /**
     * @author chuyunfei
     * @description 判断一个用户是否已经创建了一个比赛
     * @date 20:54 2019/4/26
     **/
    boolean existsByUserStudentInfo_IdAndGameBasicInfo_Id(Long userStudentId,Long gameId);

    /**
     * @author chuyunfei
     * @description 获取一个比赛的全部企业信息
     * @date 20:54 2019/4/26
     **/
    List<EnterpriseBasicInfo> findByGameBasicInfo_Id(Long gameId);

    
    /**
     * @author yuanyiwen
     * @description 获取一个比赛中处于某一状态的全部企业
     * @date 21:52 2019/5/10
     **/
    List<EnterpriseBasicInfo> findByGameBasicInfo_IdAndEnterpriseStatus(Long gameId, EnterpriseStatusEnum enterpriseStatus);

    /**
     * @author yuanyiwen
     * @description 获取某场比赛处于某年时，全部完成了广告投放的企业
     *               （这里不用判断企业状态，因为只有投放了广告才能开始今年的游戏，才有破产的可能
     *               而每年推进的时候会初始化所有仍存活的企业的广告投放情况，
     *               也就是说破产了的企业是处于完成投放的状态的，不影响最终的判断
     * @date 16:15 2019/7/7
     **/
    List<EnterpriseBasicInfo> findByGameBasicInfo_IdAndFinishAdvertisingIsTrue(Long gameId);

    /**
     * @author yuanyiwen
     * @description 获取某场比赛处于某年时，全部退出订单会的企业 括弧同上
     * @date 18:31 2019/7/7
     **/
    List<EnterpriseBasicInfo> findByGameBasicInfo_IdAndFinishChoiceIsTrue(Long gameId);

    /**
     * @author yuanyiwen
     * @description 根据某场比赛中企业选取订单的顺序定位企业
     * @date 16:34 2019/7/7
     **/
    EnterpriseBasicInfo findByGameBasicInfo_IdAndSequence(Long gameId, Integer sequence);

    /**
     * @author chuyunfei
     * @description 根据创建者和企业ID来判断是否存在
     * @date 20:54 2019/4/26
     **/
    boolean existsByIdAndUserStudentInfo_Id(Long enterpriseId,Long userId);

    /**
     * @author chuyunfei
     * @description 判断某个比赛的企业中是否有指定状态的企业
     * @date 20:56 2019/4/26
     **/
    boolean existsByGameBasicInfo_IdAndEnterpriseStatus(Long gameId,EnterpriseStatusEnum enterpriseStatusEnum);

    /**
     * @author chuyunfei
     * @description 获取在该轮比赛里面有意愿投广告的企业名单
     * @date 20:56 2019/4/26
     **/
    List<EnterpriseBasicInfo> findByGameBasicInfo_IdAndAdvertisingIsTrue(Long gameId);
}