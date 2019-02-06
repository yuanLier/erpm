package edu.cqupt.mislab.erp.user.dao;

import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserStudentRepository extends JpaSpecificationExecutor, JpaRepository<UserStudentInfo,Long> {
}
