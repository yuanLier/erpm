package edu.cqupt.mislab.erp.game.compete.operation.order.controller;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.order.dao.GameOrderInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.dto.EnterpriseAdDto;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.vo.GameOrderVo;
import edu.cqupt.mislab.erp.game.compete.operation.order.service.OrderChooseService;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.*;

/**
 * @author yuanyiwen
 * @create 2019-07-07 20:12
 * @description
 */
@Api(description = "学生端-订单会")
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/order/choose")
public class OrderChooseController {


    @Autowired
    private OrderChooseService orderChooseService;


    @ApiOperation(value = "企业投放广告，一次可同时投放多条")
    @PostMapping("/advertise")
    WebResponseVo<Boolean> advertising(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                            @RequestParam Long enterpriseId,
                                       @RequestBody List<EnterpriseAdDto> enterpriseAdDtoList) {

        Boolean advertise = orderChooseService.advertising(enterpriseId, enterpriseAdDtoList);

        if(advertise == null) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "该企业已完成广告投放，请不要重复进行");
        }

        return toSuccessResponseVoWithData(advertise);
    }


    @ApiOperation(value = "获取某年中挑选出的订单")
    @GetMapping("/all")
    WebResponseVo<List<GameOrderVo>> getOrderOfOneYear(@Exist(repository = GameBasicInfoRepository.class)
                                                               @RequestParam Long gameId,
                                                       @RequestParam Integer year) {

        if(year < 1) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "年份应大于1");
        }

        return toSuccessResponseVoWithData(orderChooseService.getOrderOfOneYear(gameId, year));
    }


    @ApiOperation(value = "企业选取订单，一次只能选取一个")
    @PostMapping("/choose")
    WebResponseVo<Boolean> enterpriseChooseOrder(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                                        @RequestParam Long enterpriseId,
                                                 @Exist(repository = GameOrderInfoRepository.class)
                                                         @RequestParam Long gameOrderId) {

        return toSuccessResponseVoWithData(orderChooseService.enterpriseChooseOrder(enterpriseId, gameOrderId));
    }


    @ApiOperation(value = "企业结束选单")
    @GetMapping("/finish")
    WebResponseVo<Long> enterpriseFinishChoose(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                               @RequestParam Long enterpriseId) {

        return toSuccessResponseVoWithData(orderChooseService.enterpriseFinishCurrentChoice(enterpriseId));
    }


    @ApiOperation(value = "企业退出订单会")
    @GetMapping("/drop")
    WebResponseVo enterpriseDropOut(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                             @RequestParam Long enterpriseId) {

        boolean finish = orderChooseService.enterpriseFinishChoice(enterpriseId);

        if(!finish) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "请先结束本轮的订单选取！");
        }

        return toSuccessResponseVoWithNoData();
    }


    @ApiOperation(value = "是否轮到某企业选取订单")
    @GetMapping("/turn")
    WebResponseVo<Boolean> isTurnOfEnterprise(@Exist(repository = EnterpriseBasicInfoRepository.class)
                                              @RequestParam Long enterpriseId) {

        return toSuccessResponseVoWithData(orderChooseService.isTurnOfEnterprise(enterpriseId));
    }

}
