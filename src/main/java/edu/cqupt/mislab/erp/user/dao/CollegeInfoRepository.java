package edu.cqupt.mislab.erp.user.dao;

import edu.cqupt.mislab.erp.user.model.entity.CollegeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CollegeInfoRepository extends JpaSpecificationExecutor, JpaRepository<CollegeInfo,Long> {
}
