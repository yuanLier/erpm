package edu.cqupt.mislab.erp.user.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author chuyunfei
 * @description 
 * @date 21:09 2019/4/22
 **/

public interface UserStudentRepository extends JpaSpecificationExecutor, BasicRepository<UserStudentInfo,Long> {

    /**
     * @author chuyunfei
     * @description 选择一个启用状态的学生用户
     * @date 21:09 2019/4/22
     **/
    UserStudentInfo findByIdAndAccountEnable(Long studentId,boolean accountEnable);

    /**
     * @author chuyunfei
     * @description 通过账户查询一个审核是否通过的账户信息
     * @date 21:10 2019/4/22
     **/
    UserStudentInfo findByStudentAccountAndAccountEnable(String studentAccount,boolean accountEnable);

    /**
     * @author chuyunfei
     * @description 选择一个教师的全部学生
     * @date 21:10 2019/4/22
     **/
    List<UserStudentInfo> findAllByUserTeacherInfo_Id(Long teacherId);

    /**
     * @author chuyunfei
     * @description 根据账号和密码查询一条数据
     * @date 21:10 2019/4/22
     **/
    UserStudentInfo findByStudentAccountAndStudentPasswordAndAccountEnable(String studentAccount,String studentPassword,boolean accountEnable);
}