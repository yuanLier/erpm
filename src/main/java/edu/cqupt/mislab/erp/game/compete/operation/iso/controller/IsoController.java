package edu.cqupt.mislab.erp.game.compete.operation.iso.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.service.IsoService;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toFailResponseVoWithMessage;
import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithData;

/**
 * @author yuanyiwen
 * @description
 **/

@Api(description = "学生端-iso认证")
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/iso")
public class IsoController {

    @Autowired
    private IsoService isoService;

    @ApiOperation(value = "获取某个企业的全部ISO认证信息")
    @GetMapping
    public WebResponseVo<List<IsoDisplayVo>> findByEnterpriseId(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                    @RequestParam Long enterpriseId) {

        List<IsoDisplayVo> isoDisplayVoList = isoService.findByEnterpriseId(enterpriseId);

        if(isoDisplayVoList == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "该企业对应的iso认证不存在");
        }

        return toSuccessResponseVoWithData(isoDisplayVoList);
    }


    @ApiOperation(value = "获取某企业中处于某认证状态的ISO")
    @GetMapping("status")
    public WebResponseVo<List<IsoDisplayVo>> findByEnterpriseIdAndIsoStatus(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                                   @RequestParam Long enterpriseId,
                                                                               @RequestParam IsoStatusEnum isoStatus) {

        List<IsoDisplayVo> isoDisplayVoList = isoService.findByEnterpriseIdAndIsoStatus(enterpriseId, isoStatus);

        if(isoDisplayVoList == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "该企业对应的iso认证不存在");
        }

        return toSuccessResponseVoWithData(isoDisplayVoList);
    }


    @ApiOperation(value = "开始认证")
    @PutMapping("start")
    public WebResponseVo<IsoDisplayVo> startDevelopIso(@Exist(repository = IsoDevelopInfoRepository.class)
                                                           @RequestParam Long isoDevelopId) {

        return toSuccessResponseVoWithData(isoService.updateIsoStatus(isoDevelopId, IsoStatusEnum.DEVELOPING));
    }


    @ApiOperation(value = "暂停认证")
    @PutMapping("pause")
    public WebResponseVo<IsoDisplayVo> updateIsoStatusToPause(@Exist(repository = IsoDevelopInfoRepository.class)
                                                       @RequestParam Long isoDevelopId) {

        return toSuccessResponseVoWithData(isoService.updateIsoStatus(isoDevelopId, IsoStatusEnum.DEVELOPPAUSE));
    }


    @ApiOperation(value = "继续认证")
    @PutMapping("develop")
    public WebResponseVo<IsoDisplayVo> updateIsoStatusToDeveloping(@Exist(repository = IsoDevelopInfoRepository.class)
                                                       @RequestParam Long isoDevelopId) {

        return toSuccessResponseVoWithData(isoService.updateIsoStatus(isoDevelopId, IsoStatusEnum.DEVELOPING));
    }
}
