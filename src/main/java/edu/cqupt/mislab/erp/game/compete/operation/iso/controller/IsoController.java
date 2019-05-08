package edu.cqupt.mislab.erp.game.compete.operation.iso.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.dto.IsoBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoBasicVo;
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

@Api
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/iso")
public class IsoController {

    @Autowired
    private IsoService isoService;

    @ApiOperation(value = "获取某个企业的全部ISO认证信息")
    @GetMapping("/iso/infos/get")
    public WebResponseVo<List<IsoDisplayVo>> findByEnterpriseId(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                    @RequestParam Long enterpriseId) {

        List<IsoDisplayVo> isoDisplayVoList = isoService.findByEnterpriseId(enterpriseId);

        if(isoDisplayVoList == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND, "该企业对应的iso认证不存在");
        }

        return toSuccessResponseVoWithData(isoDisplayVoList);
    }


    @ApiOperation(value = "获取某企业中处于某认证状态的iso")
    @GetMapping("/iso/infos/get/status")
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
    @PutMapping("/iso/infos/update/start")
    public WebResponseVo<IsoDisplayVo> startDevelopIso(@Exist(repository = IsoDevelopInfoRepository.class)
                                                           @RequestParam Long isoDevelopId) {

        return toSuccessResponseVoWithData(isoService.updateIsoStatus(isoDevelopId, IsoStatusEnum.DEVELOPING));
    }


    @ApiOperation(value = "暂停认证")
    @PutMapping("/iso/infos/update/pause")
    public WebResponseVo<IsoDisplayVo> updateIsoStatusToPause(@Exist(repository = IsoDevelopInfoRepository.class)
                                                       @RequestParam Long isoDevelopId) {

        return toSuccessResponseVoWithData(isoService.updateIsoStatus(isoDevelopId, IsoStatusEnum.DEVELOPPAUSE));
    }


    @ApiOperation(value = "继续认证")
    @PutMapping("/iso/infos/update/develop")
    public WebResponseVo<IsoDisplayVo> updateIsoStatusToDeveloping(@Exist(repository = IsoDevelopInfoRepository.class)
                                                       @RequestParam Long isoDevelopId) {

        return toSuccessResponseVoWithData(isoService.updateIsoStatus(isoDevelopId, IsoStatusEnum.DEVELOPING));
    }


    @ApiOperation(value = "（管理员）修改iso基本信息")
    @PostMapping("/iso/basic/update")
    public WebResponseVo<IsoBasicVo> updateIsoBasicInfo(@RequestBody IsoBasicDto isoBasicDto) {
        if(isoBasicDto != null) {
            return toSuccessResponseVoWithData(isoService.updateIsoBasicInfo(isoBasicDto));
        } else {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.NOT_FOUND,"所传信息为空");
        }
    }
}
