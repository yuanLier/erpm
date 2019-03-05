package edu.cqupt.mislab.erp.game.manage.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseUtil;
import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.dto.EnterpriseCreateDto;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseDetailInfoVo;
import edu.cqupt.mislab.erp.game.manage.service.EnterpriseManageService;
import edu.cqupt.mislab.erp.user.dao.UserStudentRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/manage/enterprise")
public class EnterpriseManageController {

    @Autowired private EnterpriseManageService enterpriseManageService;

    @ApiOperation("创建一个新的企业")
    @PostMapping("/create")
    public WebResponseVo<EnterpriseDetailInfoVo> createNewEnterprise(@Valid @RequestBody EnterpriseCreateDto createDto){

        return enterpriseManageService.createNewEnterprise(createDto);
    }

    @ApiOperation(value = "删除一个企业",notes = "敏感操作，需要提供密码")
    @DeleteMapping("/delete")
    public WebResponseVo<String> deleteOneEnterprise(@Exist(repository = EnterpriseBasicInfoRepository.class) @RequestParam Long enterpriseId,@Exist(repository = UserStudentRepository.class) @RequestParam Long userId,@RequestParam String password){

        return enterpriseManageService.deleteOneEnterprise(enterpriseId,userId,password);
    }

    @ApiOperation("当前企业确定准备完成")
    @PostMapping("/sure")
    public WebResponseVo<String> sureOneEnterprise(@Exist(repository = EnterpriseBasicInfoRepository.class) @RequestParam Long enterpriseId,@Exist(repository = UserStudentRepository.class) @RequestParam Long userId){

        return enterpriseManageService.sureOneEnterprise(enterpriseId,userId);
    }

    @ApiOperation("获取一个企业的详细信息")
    @GetMapping("/enterpriseInfo/get")
    public WebResponseVo<EnterpriseDetailInfoVo> getOneEnterpriseInfo(@Exist(repository = EnterpriseBasicInfoRepository.class) @RequestParam Long enterpriseId){

        EnterpriseDetailInfoVo enterpriseDetailInfoVo = enterpriseManageService.getOneEnterpriseInfo(enterpriseId);

        if(enterpriseDetailInfoVo == null){

            return WebResponseUtil.toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND,"该企业不存在");
        }

        return WebResponseUtil.toSuccessResponseVoWithData(enterpriseDetailInfoVo);
    }

    @ApiOperation("获取指定比赛的全部企业的详细信息")
    @GetMapping("/enterpriseInfos/get")
    public WebResponseVo<List<EnterpriseDetailInfoVo>> getEnterpriseInfos(@Exist(repository = GameBasicInfoRepository.class) @RequestParam Long gameId){

        List<EnterpriseDetailInfoVo> detailInfoVos = enterpriseManageService.getEnterpriseInfos(gameId);

        if(detailInfoVos == null){

            return WebResponseUtil.toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND,"该比赛还没有企业被创建");
        }

        return WebResponseUtil.toSuccessResponseVoWithData(detailInfoVos);
    }
}
