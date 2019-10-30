package edu.cqupt.mislab.erp.user.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.user.dao.UserTeacherRepository;
import edu.cqupt.mislab.erp.user.service.StudentInfoImportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toFailResponseVoWithMessage;
import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithData;

/**
 * @author yuanyiwen
 * @create 2019-10-22 12:21
 * @description
 */
@Api(tags = "用户中心-学生信息收集模板下载与导入")
@Validated
@CrossOrigin
@RestController
@RequestMapping("/user/student/excel")
public class StudentInfoImportController {

    @Autowired
    private StudentInfoImportService studentInfoImportService;

    @ApiOperation(value = "获取模板Excel", notes = "直接访问该路径即可下载")
    @GetMapping
    public WebResponseVo<String> getStudentInfoTemplate() {

        return toSuccessResponseVoWithData(studentInfoImportService.getStudentInfoTemplate());
    }

    @ApiOperation(value = "从Excel中导入学生信息",
            notes = "由于性别、学院、专业信息需要保证格式统一，所以不从外部导入，需要学生在操作时自主修改；" +
                    "导入时需要传入教师id与密码，由哪名教师导入，则默认为该教师的学生")
    @PostMapping
    public WebResponseVo<Boolean> importExcel(@Exist(repository = UserTeacherRepository.class)
                                                     @RequestParam Long teacherId,
                                              @RequestParam String password,
                                              @RequestParam("file") MultipartFile multipartFile) {

        Boolean resp = studentInfoImportService.studentInfoImport(teacherId, password, multipartFile);

        if(resp == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "请输入正确的密码！");
        }

        return toSuccessResponseVoWithData(resp);
    }

}

