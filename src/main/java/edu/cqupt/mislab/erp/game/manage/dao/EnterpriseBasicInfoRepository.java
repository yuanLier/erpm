package edu.cqupt.mislab.erp.game.manage.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseStatus;

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
    EnterpriseBasicInfo findByIdAndEnterpriseStatus(long enterpriseId,EnterpriseStatus status);

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
    boolean existsByGameBasicInfo_IdAndEnterpriseStatus(Long gameId,EnterpriseStatus enterpriseStatus);

    /**
     * @author chuyunfei
     * @description 获取在该轮比赛里面有意愿投广告的企业名单
     * @date 20:56 2019/4/26
     **/
    List<EnterpriseBasicInfo> findByGameBasicInfo_IdAndAdvertisingIsTrue(Long gameId);

    /**
     * @author chuyunfei
     * @description 获取在该轮比赛中有意愿投广告却还没有投广告的企业名单
     * @date 20:56 2019/4/26
     **/
    List<EnterpriseBasicInfo> findByGameBasicInfo_IdAndAdvertisingIsTrueAndAdvertisingCostIsFalse(long gameId);

    /**
     * @author chuyunfei
     * @description 获取指定比赛中没有达到指定周期的企业信息
     * @date 20:56 2019/4/26
     **/
    List<EnterpriseBasicInfo> findByGameBasicInfo_IdAndEnterpriseCurrentPeriodIsNot(long gameId,int concurrentPeriod);
}