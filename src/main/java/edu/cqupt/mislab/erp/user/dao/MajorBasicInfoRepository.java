package edu.cqupt.mislab.erp.user.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.user.model.entity.MajorBasicInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author chuyunfei
 * @description 
 * @date 21:21 2019/4/22
 **/

public interface MajorBasicInfoRepository extends JpaSpecificationExecutor, BasicRepository<MajorBasicInfo,Long> {
}
