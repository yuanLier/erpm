package edu.cqupt.mislab.erp.game.compete.operation.order.service;

import edu.cqupt.mislab.erp.game.compete.operation.order.model.dto.EnterpriseAdDto;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.GameOrderInfo;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.vo.GameOrderVo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-06-23 13:57
 * @description 广告投放与订单选取
 */
public interface OrderChooseService {

    /**
     * 企业投放广告，一次可同时投放多条
     * @param enterpriseId 哪个企业投放的广告
     * @param enterpriseAdDtoList 广告投放列表
     * @return
     */
    boolean advertising(Long enterpriseId, List<EnterpriseAdDto> enterpriseAdDtoList);


    /**
     * 综合企业产品研发、ISO认证等情况，选择某一年中可供企业选取的订单，仅作为内部接口使用
     * @param year
     * @return
     */
    List<GameOrderInfo> selectOrdersOfOneYear(Long gameId, Integer year);


    /**
     * 获取某年中挑选出的订单，作为对外展示的接口使用
     * @param gameId
     * @param year
     * @return
     */
    List<GameOrderVo> getOrderOfOneYear(Long gameId, Integer year);


    /**
     * 企业选取订单，一次只能选取一个
     * @param enterpriseId
     * @param gameOrderId
     * @return
     */
    boolean enterpriseChooseOrder(Long enterpriseId, Long gameOrderId);


    /**
     * 企业结束选单 todo websocket通知前端
     * @param enterpriseId
     * @return 返回下一个进行选单操作的企业
     */
    EnterpriseBasicInfo enterpriseFinishChoose(Long enterpriseId);


    /**
     * 企业退出订单会
     * @param enterpriseId
     * @return 返回成功退出与否
     */
    boolean enterpriseDropOut(Long enterpriseId);
}
