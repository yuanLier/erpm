package edu.cqupt.mislab.erp.user.dao;

import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserStudentRepository extends JpaSpecificationExecutor, JpaRepository<UserStudentInfo,Long> {

    //选择一个启用状态的学生用户
    UserStudentInfo findByIdAndAccountEnable(Long studentId,boolean accountEnable);

    //判断一个账户是否存在
    boolean existsByStudentAccountAndAccountEnable(String studentAccount,boolean accountEnable);

    //通过学生账户选择一个学生对象
    UserStudentInfo findFirstByStudentAccount(String studentAccount);

    //选择一个教师的全部学生
    List<UserStudentInfo> findAllByUserTeacherInfo_Id(Long teacherId);

    //根据账号和密码查询一条数据
    UserStudentInfo findDistinctByStudentAccountAndStudentPassword(String studentAccount,String studentPassword);
}