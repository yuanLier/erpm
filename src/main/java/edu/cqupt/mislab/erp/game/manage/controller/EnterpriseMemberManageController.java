package edu.cqupt.mislab.erp.game.manage.controller;

import edu.cqupt.mislab.erp.commons.response.ResponseVo;
import edu.cqupt.mislab.erp.game.manage.model.dto.EnterpriseJoinDto;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseMemberDisplayVo;
import edu.cqupt.mislab.erp.game.manage.service.EnterpriseMemberManageService;
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
@RequestMapping("/game/manage/enterprise/member")
public class EnterpriseMemberManageController {

    @Autowired
    private EnterpriseMemberManageService enterpriseMemberManageService;

    @ApiOperation("用户加入一个企业")
    @PostMapping("/join")
    public ResponseVo<String> joinOneEnterprise(@Valid @RequestBody EnterpriseJoinDto joinDto){

        return enterpriseMemberManageService.joinOneEnterprise(joinDto);
    }

    @ApiOperation("用户退出一个企业")
    @DeleteMapping("/out")
    public ResponseVo<String> outOneEnterprise(@RequestParam Long userId,@RequestParam Long enterpriseId){

        if(userId < 1 || enterpriseId < 1){

            return toFailResponseVo(HttpStatus.BAD_REQUEST,"参数出现问题");
        }

        return enterpriseMemberManageService.outOneEnterprise(userId,enterpriseId);
    }

    @ApiOperation("获取指定企业的全部成员的详细信息")
    @GetMapping("/enterpriseMemberInfos/get")
    public ResponseVo<List<EnterpriseMemberDisplayVo>> getOneEnterpriseMemberInfos(@RequestParam Long enterpriseId){

        if(enterpriseId < 1){

            return toFailResponseVo(HttpStatus.BAD_REQUEST,"参数出现问题");
        }

        List<EnterpriseMemberDisplayVo> displayVos = enterpriseMemberManageService.getOneEnterpriseMemberInfos(enterpriseId);

        if(displayVos == null){

            return toFailResponseVo(HttpStatus.NOT_FOUND,"该企业还没有成员信息");
        }

        return toSuccessResponseVo(displayVos);
    }
}
