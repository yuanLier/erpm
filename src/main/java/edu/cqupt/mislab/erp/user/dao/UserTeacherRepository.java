package edu.cqupt.mislab.erp.user.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.user.model.entity.UserTeacherInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
 * @author chuyunfei
 * @description 
 * @date 21:21 2019/4/22
 **/
public interface UserTeacherRepository extends JpaSpecificationExecutor, BasicRepository<UserTeacherInfo,Long> {
}
