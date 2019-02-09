package edu.cqupt.mislab.erp.game.manage.controller;

import edu.cqupt.mislab.erp.commons.response.ResponseVo;
import edu.cqupt.mislab.erp.game.manage.model.dto.EnterpriseCreateDto;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseDetailInfoVo;
import edu.cqupt.mislab.erp.game.manage.service.EnterpriseManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.ResponseUtil.*;

@Api
@RestController
@RequestMapping("/game/manage/enterprise")
public class EnterpriseManageController {

    @Autowired
    private EnterpriseManageService enterpriseManageService;

    @ApiOperation("创建一个新的企业")
    @PostMapping("/create")
    public ResponseVo<EnterpriseDetailInfoVo> createNewEnterprise(@Valid @RequestBody EnterpriseCreateDto createDto){

        return enterpriseManageService.createNewEnterprise(createDto);
    }

    @ApiOperation("删除一个企业")
    @DeleteMapping("/delete")
    public ResponseVo<String> deleteOneEnterprise(@RequestParam Long enterpriseId,@RequestParam Long userId){

        if(enterpriseId < 1 || userId < 1){

            return toFailResponseVo(HttpStatus.BAD_REQUEST,"参数错误");
        }

        return enterpriseManageService.deleteOneEnterprise(enterpriseId,userId);
    }

    @ApiOperation("当前企业确定准备完成")
    @PostMapping("/sure")
    public ResponseVo<String> sureOneEnterprise(@RequestParam Long enterpriseId,@RequestParam Long userId){

        if(enterpriseId < 1 || userId < 1){

            return toFailResponseVo(HttpStatus.BAD_REQUEST,"参数错误");
        }

        return enterpriseManageService.sureOneEnterprise(enterpriseId,userId);
    }

    @ApiOperation("获取一个企业的详细信息")
    @GetMapping("/enterpriseInfo/get")
    public ResponseVo<EnterpriseDetailInfoVo> getOneEnterpriseInfo(@RequestParam Long enterpriseId){

        if(enterpriseId < 1){

            return toFailResponseVo(HttpStatus.BAD_REQUEST,"企业的ID出现问题");
        }

        EnterpriseDetailInfoVo enterpriseDetailInfoVo = enterpriseManageService.getOneEnterpriseInfo(enterpriseId);

        if(enterpriseDetailInfoVo == null){

            return toFailResponseVo(HttpStatus.NOT_FOUND,"该企业不存在");
        }

        return toSuccessResponseVo(enterpriseDetailInfoVo);
    }

    @ApiOperation("获取指定比赛的全部企业的详细信息")
    @GetMapping("/enterpriseInfos/get")
    public ResponseVo<List<EnterpriseDetailInfoVo>> getEnterpriseInfos(@RequestParam Long gameId){

        if(gameId < 1){

            return toFailResponseVo(HttpStatus.BAD_REQUEST,"比赛的ID出现问题");
        }

        List<EnterpriseDetailInfoVo> detailInfoVos = enterpriseManageService.getEnterpriseInfos(gameId);

        if(detailInfoVos == null){

            return toFailResponseVo(HttpStatus.NOT_FOUND,"该比赛还没有企业被创建");
        }

        return toSuccessResponseVo(detailInfoVos);
    }
}
