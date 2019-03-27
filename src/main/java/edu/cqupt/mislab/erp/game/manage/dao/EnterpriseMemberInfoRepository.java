package edu.cqupt.mislab.erp.game.manage.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseMemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EnterpriseMemberInfoRepository extends BasicRepository<EnterpriseMemberInfo, Long> {

    //判断当前用户是否已经加入了当前比赛的某个企业
    boolean existsByUserStudentInfo_IdAndEnterpriseBasicInfo_GameBasicInfo_Id(Long studentId,Long gameId);

    //查询指定的企业里面的用户数据
    EnterpriseMemberInfo findByEnterpriseBasicInfo_IdAndUserStudentInfo_Id(Long enterpriseId,Long userId);

    //查询一个企业里面的全部成员信息
    List<EnterpriseMemberInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);

    //删除某个企业里面的全部数据
    void deleteByEnterpriseBasicInfo_Id(long enterpriseId);
}