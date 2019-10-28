package edu.cqupt.mislab.erp.game.manage.service;

import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseMemberReportVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-10-20 14:25
 * @description 实验报告相关接口
 */
public interface EnterpriseMemberReportService {

    /**
     * 企业经营结束后，用户提交实验报告
     * @param userId
     * @param gameId
     * @param multipartFile 文件需要以.pdf/.doc/.docx/.zip结尾
     * @return 返回文件存储路径，通过这个路径可以直接访问刚刚上传的文件
     */
    Boolean submitExperimentalReport(Long userId, Long gameId, MultipartFile multipartFile);

    /**
     * 查看某一成员的实验报告 todo 教师端批量查看
     * @param userId
     * @param gameId
     * @return
     */
    EnterpriseMemberReportVo checkExperimentalReport(Long userId, Long gameId);


    /**
     * 删除成员报告；删除前需要校验密码
     * @param userId
     * @param password
     * @param gameId
     * @return
     */
    Boolean deleteExperimentalReport(Long userId, String password, Long gameId);


    /**
     * 获取某场比赛中的全部实验报告
     * @param gameId
     * @return todo 教师端写完之后把这个接口挪过去
     */
    List<EnterpriseMemberReportVo> getAllReportsOfGame(Long gameId);
}
