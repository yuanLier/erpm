package edu.cqupt.mislab.erp.game.compete.operation.product.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.EnterpriseStatusValid;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatus;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductEnterpriseBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.service.ProductService;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.*;

import java.util.List;

@Api
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/product")
public class ProductController {

    @Autowired private ProductService productService;

    @ApiOperation(value = "获取某个企业的某个状态的产品开发信息",notes = "返回204标识没有相关数据，200标识有数据")
    @GetMapping("/enterprise/status/get")
    public WebResponseVo<List<ProductEnterpriseBasicVo>> getProductDevelopInfoOfStatus(
            @EnterpriseStatusValid(enterpriseStatus = EnterpriseStatus.PLAYING) @RequestParam Long enterpriseId,
            @RequestParam ProductDevelopStatus status){

        List<ProductEnterpriseBasicVo> basicVos = productService.getProductDevelopInfoOfStatus(enterpriseId,status);

        if(basicVos == null || basicVos.size() == 0){

            //没有数据
            return toSuccessResponseVoWithNoData();
        }

        //返回数据
        return toSuccessResponseVoWithData(basicVos);
    }

}
