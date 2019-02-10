package edu.cqupt.mislab.erp.game.manage.dao;

import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EnterpriseBasicInfoRepository extends JpaSpecificationExecutor, JpaRepository<EnterpriseBasicInfo, Long> {

    //判断一个用户是否已经创建了一个比赛
    boolean existsByEnterpriseCeo_IdAndGameInfo_Id(Long userStudentId,Long gameId);

    //获取一个比赛的全部企业信息
    List<EnterpriseBasicInfo> findByGameInfo_Id(Long gameId);

    //根据创建者和企业ID来判断是否存在
    boolean existsByIdAndEnterpriseCeo_Id(Long enterpriseId,Long userId);

    //判断某个比赛的企业中是否有指定状态的企业
    boolean existsByGameInfo_IdAndEnterpriseStatus(Long gameId,EnterpriseStatus enterpriseStatus);
}