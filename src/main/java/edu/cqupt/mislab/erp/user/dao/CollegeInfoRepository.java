package edu.cqupt.mislab.erp.user.dao;

import edu.cqupt.mislab.erp.user.model.entity.CollegeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollegeInfoRepository extends JpaRepository<CollegeInfo,Long> {
}
