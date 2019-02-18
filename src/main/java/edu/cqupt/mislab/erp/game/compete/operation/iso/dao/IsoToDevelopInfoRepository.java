package edu.cqupt.mislab.erp.game.compete.operation.iso.dao;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoDevelopingInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoToDevelopInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface IsoToDevelopInfoRepository extends JpaSpecificationExecutor, JpaRepository<IsoToDevelopInfo, Long> {

    //选取某个企业的未认证信息
    List<IsoToDevelopInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);
}
