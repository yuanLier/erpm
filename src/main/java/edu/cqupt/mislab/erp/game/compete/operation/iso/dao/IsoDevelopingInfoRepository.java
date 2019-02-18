package edu.cqupt.mislab.erp.game.compete.operation.iso.dao;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoDevelopedInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoDevelopingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface IsoDevelopingInfoRepository extends JpaSpecificationExecutor, JpaRepository<IsoDevelopingInfo, Long> {

    //选取某个企业正在认证的认证信息
    List<IsoDevelopingInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);
}
