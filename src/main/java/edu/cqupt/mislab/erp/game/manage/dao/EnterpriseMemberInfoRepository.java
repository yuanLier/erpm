package edu.cqupt.mislab.erp.game.manage.dao;

import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseMemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EnterpriseMemberInfoRepository extends JpaSpecificationExecutor, JpaRepository<EnterpriseMemberInfo, Long> {

    boolean existsByStudentInfo_IdAndEnterprise_GameInfo_Id(Long studentId,Long gameId);//判断当前用户是否已经加入了当前比赛的某个企业

    EnterpriseMemberInfo findByEnterprise_IdAndStudentInfo_Id(Long enterpriseId,Long userId);//查询指定的企业里面的用户数据

    List<EnterpriseMemberInfo> findByEnterprise_Id(Long enterpriseId);//查询一个企业里面的全部成员信息
}