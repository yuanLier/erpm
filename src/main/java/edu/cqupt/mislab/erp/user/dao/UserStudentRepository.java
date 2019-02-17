package edu.cqupt.mislab.erp.user.dao;

import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserStudentRepository extends JpaSpecificationExecutor, JpaRepository<UserStudentInfo,Long> {

    //选择一个启用状态的学生用户
    UserStudentInfo findByIdAndAccountEnable(Long studentId,boolean accountEnable);

    //通过账户查询一个审核是否通过的账户信息
    UserStudentInfo findByStudentAccountAndAccountEnable(String studentAccount,boolean accountEnable);

    //选择一个教师的全部学生
    List<UserStudentInfo> findAllByUserTeacherInfo_Id(Long teacherId);

    //根据账号和密码查询一条数据
    UserStudentInfo findByStudentAccountAndStudentPasswordAndAccountEnable(String studentAccount,String studentPassword,boolean accountEnable);
}