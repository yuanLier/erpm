package edu.cqupt.mislab.erp.user.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.user.model.entity.MajorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MajorInfoRepository extends JpaSpecificationExecutor, BasicRepository<MajorInfo,Long> {
}
