package edu.cqupt.mislab.erp.user.dao;

import edu.cqupt.mislab.erp.user.model.entity.UserAvatarInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserAvatarRepository extends JpaSpecificationExecutor, JpaRepository<UserAvatarInfo,Long> {
}
