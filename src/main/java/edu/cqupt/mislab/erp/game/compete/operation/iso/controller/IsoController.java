package edu.cqupt.mislab.erp.game.compete.operation.iso.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.service.IsoService;
import edu.cqupt.mislab.erp.game.compete.operation.iso.util.EnumUtil;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toFailResponseVoWithMessage;
import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithData;

@Api
@CrossOrigin
@RestController
@RequestMapping("/game/compete/operation/iso")
public class IsoController {

    @Autowired
    private IsoService isoService;

    @ApiOperation(value = "获取某个企业的全部ISO认证信息")
    @PostMapping("/iso/infos/get")//todo 这里为什么使用POST诶？是因为使用了两个参数？
    public WebResponseVo<List<IsoDisplayVo>> findByEnterpriseId(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                    @RequestParam Long enterpriseId) {

        List<IsoDisplayVo> isoDisplayVoList = isoService.findByEnterpriseId(enterpriseId);

        if(isoDisplayVoList == null) {
            //todo 状态一定要写清楚，必须要有，参见edu.cqupt.mislab.erp.commons.response.WebResponseVo.ResponseStatus
            return toFailResponseVoWithMessage(null, "该企业对应的iso认证不存在");
        }

        return toSuccessResponseVoWithData(isoDisplayVoList);
    }


    @ApiOperation(value = "获取某企业中处于某认证状态的iso")
    @PostMapping("/iso/infos/get/{status}")
    public WebResponseVo<List<IsoDisplayVo>> findByEnterpriseIdAndIsoStatus(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                                                   @RequestParam Long enterpriseId,
                                                                               @PathVariable IsoStatusEnum isoStatus) {
        //todo springmvc自动转换，不需要这个方法来判断，转换失败是会直接抛异常的
        if(!EnumUtil.isInclude(isoStatus.toString())) {
            return toFailResponseVoWithMessage(null, "iso认证状态有误");
        }

        List<IsoDisplayVo> isoDisplayVoList = isoService.findByEnterpriseIdAndIsoStatus(enterpriseId, isoStatus);

        if(isoDisplayVoList == null) {
            return toFailResponseVoWithMessage(null, "该企业对应的iso认证不存在");
        }

        return toSuccessResponseVoWithData(isoDisplayVoList);
    }


    @ApiOperation(value = "修改某个iso的认证状态")
    @PostMapping("/iso/infos/update/{status}")
    public WebResponseVo<IsoDisplayVo> updateIsoStatus(@Exist(repository = IsoDevelopInfoRepository.class)
                                                           @RequestParam Long isoDevelopId,
                                                       @PathVariable IsoStatusEnum isoStatus) {

        if(!EnumUtil.isInclude(isoStatus.toString())) {
            return toFailResponseVoWithMessage(null, "iso认证状态有误");
        }

        return toSuccessResponseVoWithData(isoService.updateIsoStatus(isoDevelopId, isoStatus));

    }


    //todo 业务逻辑错误，修改基本信息会产生新的数据，而不是更改原来的数据
    @ApiOperation(value = "（管理员）修改iso基本信息")
    @PostMapping("/iso/basic/update")//todo 使用DTO代替 IsoBasicInfo
    public WebResponseVo<IsoBasicInfo> updateIsoBasicInfo(@RequestBody IsoBasicInfo isoBasicInfo) {
        if(isoBasicInfo != null) {
            return toSuccessResponseVoWithData(isoService.updateIsoBasicInfo(isoBasicInfo));
        } else {
            return toFailResponseVoWithMessage(null,"所传信息为空");
        }
    }
}
