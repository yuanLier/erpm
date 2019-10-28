package edu.cqupt.mislab.erp.user.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author yuanyiwen
 * @create 2019-10-21 21:46
 * @description 学生信息导入接口
 */
public interface StudentInfoImportService {


    /**
     * 获取模板Excel
     * @return 返回模板下载路径
     */
    String getStudentInfoTemplate();


    /**
     * 从Excel中导入学生信息
     * 注 ：由于性别、学院、专业信息需要保证格式统一，所以不从外部导入；需要学生在操作时自主修改
     * @param teacherId 由哪名教师导入，则默认为该教师的学生
     * @param password
     * @param multipartFile
     * @return
     */
    Boolean studentInfoImport(Long teacherId, String password, MultipartFile multipartFile);
}
