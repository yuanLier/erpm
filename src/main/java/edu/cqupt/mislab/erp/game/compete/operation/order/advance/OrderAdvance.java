package edu.cqupt.mislab.erp.game.compete.operation.order.advance;

import edu.cqupt.mislab.erp.commons.websocket.CommonWebSocketMessagePublisher;
import edu.cqupt.mislab.erp.game.compete.basic.ModelAdvance;
import edu.cqupt.mislab.erp.game.manage.constant.ManageConstant;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-07-08 20:28
 * @description
 */
@Slf4j
@Component
public class OrderAdvance implements ModelAdvance {

    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;

    @Autowired
    private CommonWebSocketMessagePublisher webSocketMessagePublisher;

    @Override
    public boolean modelHistory(Long gameId) {
        return true;
    }

    @Override
    public boolean modelAdvance(Long gameId) {


        log.info("开始进行订单模块比赛期间周期推进");

        // 获取该比赛中全部进行中企业
        List<EnterpriseBasicInfo> enterpriseBasicInfoList = enterpriseBasicInfoRepository.findByGameBasicInfo_IdAndEnterpriseStatus(gameId, EnterpriseStatusEnum.PLAYING);

        for(EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfoList) {

            // 获取当前企业所处的周期
            Integer currentPeriod = enterpriseBasicInfo.getEnterpriseCurrentPeriod();
            // 获取一年的总周期数
            Integer totalPeriod = enterpriseBasicInfo.getGameBasicInfo().getGameInitBasicInfo().getPeriodOfOneYear();

            // 如果下一周期就是新的一年
            if(currentPeriod % totalPeriod == 0) {

                // 更新订单会情况
                enterpriseBasicInfo.setAdvertising(true);
                enterpriseBasicInfo.setFinishAdvertising(false);
                enterpriseBasicInfo.setSequence(0);
                enterpriseBasicInfo.setFinishChoice(false);

                enterpriseBasicInfoRepository.save(enterpriseBasicInfo);

                // 通知前端订单会已开始
                webSocketMessagePublisher.publish(gameId, new TextMessage(ManageConstant.ORDER_MEETING_BEGIN + gameId));
            }
        }

        log.info("订单模块-周期推进成功");

        return true;
    }
}
