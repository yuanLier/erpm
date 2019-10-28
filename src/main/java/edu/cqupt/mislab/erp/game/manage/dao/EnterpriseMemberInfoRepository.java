package edu.cqupt.mislab.erp.game.manage.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseMemberInfo;

import java.util.List;

/**
 * @author chuyunfei
 * @description 
 * @date 21:28 2019/4/26
 **/

public interface EnterpriseMemberInfoRepository extends BasicRepository<EnterpriseMemberInfo, Long> {

    /**
     * @author chuyunfei
     * @description 判断当前用户是否已经加入了当前比赛的某个企业
     * @date 21:03 2019/4/26
     **/
    boolean existsByUserStudentInfo_IdAndEnterpriseBasicInfo_GameBasicInfo_Id(Long studentId,Long gameId);

    /**
     * @author chuyunfei
     * @description 查询指定的企业里面的用户数据
     * @date 21:03 2019/4/26
     **/
    EnterpriseMemberInfo findByEnterpriseBasicInfo_IdAndUserStudentInfo_Id(Long enterpriseId,Long userId);

    /**
     * @author chuyunfei
     * @description 查询一个企业里面的全部成员信息
     * @date 21:03 2019/4/26
     **/
    List<EnterpriseMemberInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);

    /**
     * @author chuyunfei
     * @description 删除某个企业里面的全部数据
     * @date 21:03 2019/4/26
     **/
    void deleteByEnterpriseBasicInfo_Id(long enterpriseId);

    /**
     * @author yuanyiwen
     * @description 获取用户在比赛中的哪个企业
     * @date 9:23 2019/7/26
     **/
    EnterpriseMemberInfo findByUserStudentInfo_IdAndEnterpriseBasicInfo_GameBasicInfo_Id(Long studentId, Long gameId);

    /**
     * @author yuanyiwen
     * @description 获取某个用户参与过的全部比赛间企业信息
     * @date 13:41 2019/7/28
     **/
    List<EnterpriseMemberInfo> findByUserStudentInfo_Id(Long userId);
    
    /**
     * @author yuanyiwen
     * @description 获取某一比赛中的全部企业成员信息
     * @date 18:40 2019/10/20
     **/
    List<EnterpriseMemberInfo> findByEnterpriseBasicInfo_GameBasicInfo_Id(Long gameId);

}