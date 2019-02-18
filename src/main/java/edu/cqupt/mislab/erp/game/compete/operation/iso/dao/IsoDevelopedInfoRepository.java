package edu.cqupt.mislab.erp.game.compete.operation.iso.dao;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoDevelopedInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface IsoDevelopedInfoRepository extends JpaSpecificationExecutor, JpaRepository<IsoDevelopedInfo, Long> {

    //选取某个企业已经认证完成的认证信息
    List<IsoDevelopedInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);
}
