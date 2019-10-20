package edu.cqupt.mislab.erp.game.manage.controller;

import com.google.common.collect.Sets;
import edu.cqupt.mislab.erp.commons.response.WebResponseUtil;
import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseMemberReportVo;
import edu.cqupt.mislab.erp.game.manage.service.EnterpriseMemberReportService;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toFailResponseVoWithMessage;
import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithData;

/**
 * @author yuanyiwen
 * @create 2019-10-20 18:51
 * @description
 */

@Api
@Validated
@CrossOrigin
@RestController
@RequestMapping("/game/manage/enterprise/member/report")
public class EnterpriseMemberReportController {

    @Autowired
    private EnterpriseMemberReportService enterpriseMemberReportService;

    /**
     * 允许上传的全部文件后缀
     */
    private static final Set<String> CORRECT_FILE_SUFFIX = Sets.newHashSet("pdf", "doc", "docx", "zip");

    @ApiOperation(value = "企业经营结束后，用户提交实验报告", notes = "上传的文件格式只能为.pdf/.doc/.docx/.zip")
    @PostMapping
    public WebResponseVo<Boolean> submitExperimentalReport(@Exist(repository = UserStudentRepository.class)
                                                                @RequestParam Long userId,
                                                           @Exist(repository = GameBasicInfoRepository.class)
                                                                @RequestParam Long gameId,
                                                           @RequestParam("file") MultipartFile multipartFile) {

        // 校验文件格式
        String name = multipartFile.getOriginalFilename();
        String suffix = name.substring((name.lastIndexOf(".")+1), name.length());

        if(!CORRECT_FILE_SUFFIX.contains(suffix)) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "上传的文件格式只能为.pdf/.doc/.docx/.zip哦");
        }

        Boolean resp = enterpriseMemberReportService.submitExperimentalReport(userId, gameId, multipartFile);

        if(resp == null) {
            return WebResponseUtil.toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "企业经营未结束，无法填写实验报告");
        }

        return toSuccessResponseVoWithData(resp);
    }


    @ApiOperation(value = "查看某一学生的实验报告", notes = "返回null说明该成员报告未提交或已删除，下同")
    @GetMapping
    public WebResponseVo<EnterpriseMemberReportVo> checkExperimentalReport(@Exist(repository = UserStudentRepository.class)
                                                           @RequestParam Long userId,
                                                           @Exist(repository = GameBasicInfoRepository.class)
                                                           @RequestParam Long gameId) {

        EnterpriseMemberReportVo enterpriseMemberReportVo = enterpriseMemberReportService.checkExperimentalReport(userId, gameId);

        if(enterpriseMemberReportVo == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "比赛未完成！");
        }

        return toSuccessResponseVoWithData(enterpriseMemberReportVo);
    }

    @ApiOperation(value = "删除成员报告", notes = "删除前需要进行密码校验")
    @DeleteMapping
    public WebResponseVo<Boolean> deleteExperimentalReport(Long userId, String password, Long gameId) {

        Boolean resp = enterpriseMemberReportService.deleteExperimentalReport(userId, password, gameId);

        if(resp == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.UNAUTHORIZED, "请输入正确的用户密码");
        }

        return toSuccessResponseVoWithData(resp);
    }


    @ApiOperation("获取某场比赛中的全部实验报告")
    @GetMapping("/all")
    public WebResponseVo<List<EnterpriseMemberReportVo>> getAllReportsOfGame(@Exist(repository = GameBasicInfoRepository.class)
                                                                                 @RequestParam Long gameId) {

        List<EnterpriseMemberReportVo> enterpriseMemberReportVoList = enterpriseMemberReportService.getAllReportsOfGame(gameId);

        if(enterpriseMemberReportVoList == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "比赛未完成！");
        }

        return toSuccessResponseVoWithData(enterpriseMemberReportVoList);
    }

}
