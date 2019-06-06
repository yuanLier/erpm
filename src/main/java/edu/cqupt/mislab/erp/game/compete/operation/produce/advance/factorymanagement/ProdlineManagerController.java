package edu.cqupt.mislab.erp.game.compete.operation.produce.advance.factorymanagement;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.dto.ProdlineBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.service.factorymanagement.ProdlineManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toFailResponseVoWithMessage;
import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithData;

/**
 * @author yuanyiwen
 * @create 2019-06-05 22:36
 * @description
 */
@Api(description = "管理端-生产线基本信息")
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/factorymanagement/prodline/manager")
public class ProdlineManagerController {

    @Autowired
    private ProdlineManagerService prodlineManagerService;


    @ApiOperation(value = "添加一个生产线基本信息")
    @PostMapping
    public WebResponseVo<ProdlineBasicVo> addProdlineBasicInfo(@Valid @RequestBody ProdlineBasicDto prodlineBasicDto) {

        if (prodlineBasicDto == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "所传信息为空");
        }

        return toSuccessResponseVoWithData(prodlineManagerService.addProdlineBasicInfo(prodlineBasicDto));
    }


    @ApiOperation(value = "修改生产线基本信息")
    @PutMapping
    public WebResponseVo<ProdlineBasicVo> updateProdlineBasicInfo(@Exist(repository = ProdlineBasicInfoRepository.class)
                                                                  @RequestParam Long prodlineBasicId,
                                                                  @Valid @RequestBody ProdlineBasicDto prodlineBasicDto) {

        if (prodlineBasicDto == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "所传信息为空");
        }

        return toSuccessResponseVoWithData(prodlineManagerService.updateProdlineBasicInfo(prodlineBasicId, prodlineBasicDto));
    }


    @ApiOperation(value = "关闭一个生产线",
            notes = "一旦关闭将不能重新启用，如果要启用只能重新添加一个相同的；" +
                    "所以需要前端在调用前给用户一些必要的提示信息")
    @PutMapping("/close")
    public WebResponseVo<ProdlineBasicVo> closeProdlineBasicInfo(@Exist(repository = ProdlineBasicInfoRepository.class)
                                                                 @RequestParam Long prodlineBasicId) {

        return toSuccessResponseVoWithData(prodlineManagerService.closeProdlineBasicInfo(prodlineBasicId));
    }


    @ApiOperation(value = "获取处于某种状态（可用or不可用）下的生产线基本信息")
    @GetMapping("/status")
    public WebResponseVo<List<ProdlineBasicVo>> getProdlineBasicInfoOfStatus(@RequestParam boolean enable) {

        return toSuccessResponseVoWithData(prodlineManagerService.getAllProdlineBasicVoOfStatus(enable));
    }
}