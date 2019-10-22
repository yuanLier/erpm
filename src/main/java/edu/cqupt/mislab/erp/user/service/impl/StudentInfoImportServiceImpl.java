package edu.cqupt.mislab.erp.user.service.impl;

import edu.cqupt.mislab.erp.commons.aspect.ExcelOperationException;
import edu.cqupt.mislab.erp.commons.config.QiniuProperties;
import edu.cqupt.mislab.erp.commons.constant.StudentInfoTemplateConstant;
import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.util.ExcelUtil;
import edu.cqupt.mislab.erp.commons.util.FileUtil;
import edu.cqupt.mislab.erp.user.dao.UserAvatarRepository;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import edu.cqupt.mislab.erp.user.dao.UserTeacherRepository;
import edu.cqupt.mislab.erp.user.model.entity.UserGenderEnum;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import edu.cqupt.mislab.erp.user.model.entity.UserTeacherInfo;
import edu.cqupt.mislab.erp.user.service.StudentInfoImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-10-21 21:51
 * @description
 */
@Service
public class StudentInfoImportServiceImpl implements StudentInfoImportService {

    @Autowired
    private UserStudentRepository userStudentRepository;
    @Autowired
    private UserAvatarRepository userAvatarRepository;
    @Autowired
    private UserTeacherRepository userTeacherRepository;

    @Autowired
    private QiniuProperties qiniuProperties;

    @Override
    public String getStudentInfoTemplate() {
        return FileUtil.getLocationByKey(qiniuProperties, StudentInfoTemplateConstant.TEMPLATE_NAME);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean studentInfoImport(Long teacherId, String password, MultipartFile multipartFile) {

        // 首先需要进行密码校验 todo 密码校验+1 如果加密了记得改 最好封装一下
        UserTeacherInfo userTeacherInfo = userTeacherRepository.findOne(teacherId);
        if(!password.equals(userTeacherInfo.getPassword())) {
            return null;
        }

        // 获取excel中的数据
        List<List<String>> studentList = ExcelUtil.dataExtraction(multipartFile);

        // 处理数据并导入数据库
        for(List<String> student : studentList) {
            UserStudentInfo userStudentInfo;
            try {
                userStudentInfo = UserStudentInfo.builder()
                        .studentAccount(student.get(0))
                        .studentPassword(StudentInfoTemplateConstant.INIT_PASSWORD)
                        // 默认为男
                        .gender(UserGenderEnum.Man)
                        .studentName(student.get(1))
                        .studentClass(student.get(2))
                        .phone(student.get(3))
                        // 默认导入账号的全部可用
                        .accountEnable(true).build();
            } catch (IndexOutOfBoundsException e) {
                throw new ExcelOperationException(WebResponseVo.ResponseStatus.BAD_REQUEST, "导入失败！学生信息空缺！");
            }

            if(student.size() >= 5) {
                userStudentInfo.setEmail(student.get(4));
            }
            userStudentInfo.setUserAvatarInfo(userAvatarRepository.findOne(StudentInfoTemplateConstant.AVATAR_INDEX));
            userStudentInfo.setUserTeacherInfo(userTeacherInfo);

            try {
                userStudentRepository.save(userStudentInfo);
            } catch (Exception e) {
                throw new ExcelOperationException(WebResponseVo.ResponseStatus.BAD_REQUEST, "导入失败！请检查学生信息是否完整，或文件是否重复上传");
            }
        }

        return true;
    }
}
