package edu.cqupt.mislab.erp.game.compete.operation.iso.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.EnterpriseIsoDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoBasicInfoDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.service.IsoService;
import edu.cqupt.mislab.erp.game.manage.model.dto.GameCreateDto;
import edu.cqupt.mislab.erp.game.manage.model.vo.GameDetailInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.*;

@Api
@CrossOrigin
@RestController
@RequestMapping("/game/compete/operation/iso")
public class IsoController {

    @Autowired
    private IsoService isoService;

    @ApiOperation(value = "获取全部的ISO认证信息",notes = "如果是newest将只会返回每一种认证的最新的信息，否则将是全部版本的认证信息")
    @PostMapping("/iso/infos/get")
    public WebResponseVo<List<IsoBasicInfoDisplayVo>> getAllIsoInfos(@RequestParam(required = false) Boolean newest){

        //查询所有的基本ISO数据信息
        List<IsoBasicInfoDisplayVo> displayVos = isoService.getAllIsoInfos(newest);

        if(displayVos == null || displayVos.size() == 0){

            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND,"没有ISO认证信息");
        }

        return toSuccessResponseVoWithData(displayVos);
    }

    @ApiOperation(value = "获取某个企业的全部的ISO认证信息")
    @PostMapping("/iso/enterprise/infos/get")
    public WebResponseVo<EnterpriseIsoDisplayVo> getAllIsoInfosOfOneEnterprise(@RequestParam Long enterpriseId){

        EnterpriseIsoDisplayVo enterpriseIsoDisplayVo = isoService.getAllIsoInfosOfOneEnterprise(enterpriseId);

        if(enterpriseIsoDisplayVo == null){

            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND,"该企业没有认证信息或企业不存在");
        }

        return toSuccessResponseVoWithData(enterpriseIsoDisplayVo);
    }

}
