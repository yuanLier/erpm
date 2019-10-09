package edu.cqupt.mislab.erp.game.compete.operation.order.service.impl;

import edu.cqupt.mislab.erp.commons.constant.FinanceOperationConstant;
import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.commons.websocket.CommonWebSocketMessagePublisher;
import edu.cqupt.mislab.erp.game.compete.operation.constant.GameSettingConstant;
import edu.cqupt.mislab.erp.game.compete.operation.finance.service.FinanceService;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.dao.EnterpriseAdInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.dao.GameOrderInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.dto.EnterpriseAdDto;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.EnterpriseAdInfo;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.GameOrderInfo;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.vo.GameOrderVo;
import edu.cqupt.mislab.erp.game.compete.operation.order.service.OrderChooseService;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.constant.ManageConstant;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author yuanyiwen
 * @create 2019-06-23 13:57
 * @description
 */

@Slf4j
@Component
public class OrderChooseServiceImpl implements OrderChooseService {


    @Autowired
    private EnterpriseAdInfoRepository enterpriseAdInfoRepository;
    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired
    private ProductBasicInfoRepository productBasicInfoRepository;
    @Autowired
    private MarketBasicInfoRepository marketBasicInfoRepository;

    @Autowired
    private GameOrderInfoRepository gameOrderInfoRepository;

    @Autowired
    private FinanceService financeService;

    @Autowired
    private CommonWebSocketMessagePublisher webSocketMessagePublisher;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean advertising(Long enterpriseId, List<EnterpriseAdDto> enterpriseAdDtoList) {

        // 首先获取这个企业
        EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        // 如果这个企业已经投放完成了 就不允许再投放
        if(enterpriseBasicInfo.getFinishAdvertising()) {
            return null;
        }

        // 获取广告费用总和
        Double totalMoney = enterpriseAdDtoList.stream().mapToDouble(EnterpriseAdDto::getMoney).sum();
        // 扣除研投放广告过程中需要支付的费用
        String changeOperating = FinanceOperationConstant.ENTERPRISE_ADVERTISING;
        financeService.updateFinanceInfo(enterpriseId, changeOperating, totalMoney, true, false);

        // 走到这里说明余额充足，允许投放广告
        for (EnterpriseAdDto enterpriseAdDto : enterpriseAdDtoList) {
            // 持久化企业广告投放情况
            enterpriseAdInfoRepository.save(EnterpriseAdInfo.builder()
                    .enterpriseBasicInfo(enterpriseBasicInfo)
                    .marketBasicInfo(marketBasicInfoRepository.findOne(enterpriseAdDto.getMarketBasicInfoId()))
                    .productBasicInfo(productBasicInfoRepository.findOne(enterpriseAdDto.getProductBasicInfoId()))
                    .year(getCurrentYearByCurrentPeriod(enterpriseBasicInfo))
                    .money(enterpriseAdDto.getMoney())
                    .build());
        }

        // 设置该企业为已投放
        enterpriseBasicInfo.setFinishAdvertising(true);
        enterpriseBasicInfoRepository.save(enterpriseBasicInfo);

        // 判断是否全部企业都投放完毕
        finishAdvertise(enterpriseBasicInfo.getGameBasicInfo().getId(), getCurrentYearByCurrentPeriod(enterpriseBasicInfo));

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<GameOrderInfo> selectOrdersOfOneYear(Long gameId, Integer year) {

        // 获取该年的订单
        List<GameOrderInfo> gameOrderInfoList = gameOrderInfoRepository.findByGameBasicInfo_IdAndYear(gameId, year);

        // 获取该比赛中全部存活的企业数量
        final int enterpriseSize = enterpriseBasicInfoRepository.findByGameBasicInfo_IdAndEnterpriseStatus(gameId, EnterpriseStatusEnum.PLAYING).size();
        // 订单总数设置为企业总数的ENTERPRISE_SIZE_RATE倍
        final double enterpriseSizeRate = GameSettingConstant.ENTERPRISE_SIZE_RATE;

        // totalOrders取当前订单总数与预期订单总数的较小值
        int totalOrders = Math.min(gameOrderInfoList.size(), (int)(enterpriseSize*enterpriseSizeRate));
        // 打乱顺序然后取前totalOrders个元素作为返回结果，相当于随机选取totalOrders个元素
        Collections.shuffle(gameOrderInfoList);
        gameOrderInfoList = gameOrderInfoList.subList(0, totalOrders);

        for(GameOrderInfo gameOrderInfo : gameOrderInfoList) {
            // 更新订单状态为可被选取
            gameOrderInfo.setSelected(true);
            gameOrderInfoRepository.save(gameOrderInfo);
        }

        return gameOrderInfoList;
    }

    @Override
    public List<GameOrderVo> getOrderOfOneYear(Long gameId, Integer year) {

        // 获取该场比赛处于某年时，当前订单池中剩余的订单
        List<GameOrderInfo> gameOrderInfoList = gameOrderInfoRepository.findByGameBasicInfo_IdAndYearAndSelectedIsTrueAndEnterpriseBasicInfoIsNull(gameId, year);

        List<GameOrderVo> gameOrderVoList = new ArrayList<>();
        for(GameOrderInfo gameOrderInfo : gameOrderInfoList) {
            gameOrderVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(gameOrderInfo, new GameOrderVo()));
        }

        return gameOrderVoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean enterpriseChooseOrder(Long enterpriseId, Long gameOrderId) {

        GameOrderInfo gameOrderInfo = gameOrderInfoRepository.findOne(gameOrderId);
        EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        // 更新订单的拥有者
        gameOrderInfo.setEnterpriseBasicInfo(enterpriseBasicInfo);
        gameOrderInfoRepository.save(gameOrderInfo);

        return true;
    }

    @Override
    public Long enterpriseFinishCurrentChoice(Long enterpriseId) {

        // 获取当前企业
        EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);
        // 获取比赛id与当前年数
        Long gameId = enterpriseBasicInfo.getGameBasicInfo().getId();
        Integer year = getCurrentYearByCurrentPeriod(enterpriseBasicInfo);

        // 获取下一个企业的选取顺序（记得对总数取模，总数指该年实际投放了订单的企业总数）
        // 注 ：已破产的企业虽然处于完成投放状态，但实际并未投放订单，所以在这里不用考虑
        Integer nextSequence = enterpriseBasicInfo.getSequence() % (int)(long)enterpriseAdInfoRepository.distinctByEnterpriseOfOneYear(year) + 1;

        // 获取下一个选取订单的企业
        EnterpriseBasicInfo nextEnterprise = enterpriseBasicInfoRepository.findByGameBasicInfo_IdAndSequence(gameId, nextSequence);

        // 如果下一个企业已经退出订单会了，重复，直到选出没有退出订单会的那个
        // 注 ：这里不需要考虑死循环，因为选择了结束选单就说明没有退出订单会，也就是说至少还有一个当前企业的getFinishAdvertising()为false
        // 所以最极端的情况就是只有当前企业没有退出订单会，那么下一个选单的企业就还是它，还是符合订单会的选单流程的
        while(nextEnterprise.getFinishChoice()) {
            nextSequence = enterpriseBasicInfo.getSequence() % (int)(long)enterpriseAdInfoRepository.distinctByEnterpriseOfOneYear(year) + 1;
            nextEnterprise = enterpriseBasicInfoRepository.findByGameBasicInfo_IdAndSequence(gameId, nextSequence);
        }

        // 轮换顺序
        enterpriseBasicInfo.setMyTurn(false);
        nextEnterprise.setMyTurn(true);
        enterpriseBasicInfoRepository.save(enterpriseBasicInfo);
        enterpriseBasicInfoRepository.save(nextEnterprise);

        // 通知前端并返回结果
        webSocketMessagePublisher.publish(gameId, new TextMessage(ManageConstant.ENTERPRISE_ORDER_SEQUENCE + nextEnterprise.getId()));

        return nextEnterprise.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean enterpriseFinishChoice(Long enterpriseId) {

        EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        // 如果是轮到这个用户选单 就必须先完成选单再退出订单会，除非只剩这一个企业了
        List<EnterpriseBasicInfo> enterpriseBasicInfoList = enterpriseBasicInfoRepository.findByGameBasicInfo_IdAndFinishChoiceIsFalse(enterpriseBasicInfo.getGameBasicInfo().getId());
        if(isTurnOfEnterprise(enterpriseId) && enterpriseBasicInfoList.size() > 1) {
            return false;
        }

        // 是否退出订单会 置为true
        enterpriseBasicInfo.setFinishChoice(true);
        // 保险起见将“是否轮到这个企业”置为false
        enterpriseBasicInfo.setMyTurn(false);
        enterpriseBasicInfoRepository.save(enterpriseBasicInfo);

        // 判断是否所有企业均已退出订单会
        finishOrderMeeting(enterpriseBasicInfo.getGameBasicInfo().getId());

        return true;
    }

    @Override
    public boolean isTurnOfEnterprise(Long enterpriseId) {

        EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        return enterpriseBasicInfo.getMyTurn();
    }

    @Override
    public boolean isEnterpriseFinishChoice(Long enterpriseId) {

        EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        return enterpriseBasicInfo.getFinishChoice();
    }


    /**
     * 获取当前周期所属的年数
     * @param enterpriseBasicInfo
     * @return
     */
    private Integer getCurrentYearByCurrentPeriod(EnterpriseBasicInfo enterpriseBasicInfo) {

        Integer currentPeriod = enterpriseBasicInfo.getEnterpriseCurrentPeriod();
        Integer totalOfOneYear = enterpriseBasicInfo.getGameBasicInfo().getGameInitBasicInfo().getPeriodOfOneYear();

        return (int)Math.ceil(currentPeriod*0.1/totalOfOneYear);
    }


    /**
     * 判断是否全部企业已经投放完毕，仅作为内部接口在每个企业广告投放完成后调用
     * @param gameId
     * @param year
     * @return 若全部投放完毕，通知前端第一个选取的企业；否则不做操作
     */
    private void finishAdvertise(Long gameId, Integer year) {

        // 获取该年完成广告投放的企业
        List<EnterpriseBasicInfo> finishList = enterpriseBasicInfoRepository.findByGameBasicInfo_IdAndFinishAdvertisingIsTrue(gameId);
        // 获取比赛中的全部企业（因为破产了的企业广告投放状态一定是true，所以不用区分企业状态
        List<EnterpriseBasicInfo> totalList = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId);

        // 若全部企业都投放完毕了
        if(finishList.size() == totalList.size()) {

            // 确定企业订单的选取顺序
            enterpriseChooseSequence(gameId, year);

            // 获取第一个轮到哪个企业选取订单
            EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findByGameBasicInfo_IdAndSequence(gameId, 1);
            // 设置这个企业为当前订单的选取者
            enterpriseBasicInfo.setMyTurn(true);
            enterpriseBasicInfoRepository.save(enterpriseBasicInfo);
            // 通知前端
            webSocketMessagePublisher.publish(gameId, new TextMessage(ManageConstant.ORDER_CHOOSE_BEGIN + enterpriseBasicInfo.getId()));

        }
    }

    /**
     * 判断是否全部企业已经退出订单会，仅作为内部接口在每个企业退出订单会时调用
     * @param gameId
     * @return 若全部退出，通知前端订单会已结束，并允许企业进入下一年
     */
    private boolean finishOrderMeeting(Long gameId) {

        // 获取该年已退出订单会的企业
        List<EnterpriseBasicInfo> finishList = enterpriseBasicInfoRepository.findByGameBasicInfo_IdAndFinishChoiceIsTrue(gameId);
        // 获取比赛中的全部企业（同样，破产了的企业退出状态为true
        List<EnterpriseBasicInfo> totalList = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId);

        // 若全部企业都退出了
        if(finishList.size() == totalList.size()) {

            // 通知前端订单会已结束
            webSocketMessagePublisher.publish(gameId, new TextMessage(ManageConstant.ORDER_MEETING_END + gameId));

            return true;
        }

        return false;
    }

    /**
     * 确定企业订单的选取顺序，仅作为内部接口使用
     * @param gameId 哪场比赛
     * @param year 哪一年
     * @return 返回按序排列的企业
     */
    private void enterpriseChooseSequence(Long gameId, Integer year) {

        Map<EnterpriseBasicInfo, Double> enterpriseMap = new HashMap<>(20);

        // 获取该场比赛中该年的全部广告
        List<EnterpriseAdInfo> enterpriseAdInfoList = enterpriseAdInfoRepository.findByEnterpriseBasicInfo_GameBasicInfo_IdAndYear(gameId, year);
        for (EnterpriseAdInfo enterpriseAdInfo : enterpriseAdInfoList) {

            // 计算各企业所投广告的总金额
            EnterpriseBasicInfo enterprise = enterpriseAdInfo.getEnterpriseBasicInfo();
            Double totalAmount = enterpriseAdInfo.getMoney() + (enterpriseMap.containsKey(enterprise) ? enterpriseMap.get(enterprise) : 0);

            enterpriseMap.put(enterprise, totalAmount);
        }

        List<EnterpriseBasicInfo> enterpriseTurn = new ArrayList<>();

        // 将每个企业所投广告的总金额降序排序，并将对应的企业id存入enterpriseTurn
        Stream<Map.Entry<EnterpriseBasicInfo, Double>> stream = enterpriseMap.entrySet().stream();
        stream.sorted(Map.Entry.<EnterpriseBasicInfo, Double>comparingByValue().reversed()).forEachOrdered(e -> enterpriseTurn.add(e.getKey()));

        for (int i = 0; i < enterpriseTurn.size(); i++) {

            EnterpriseBasicInfo enterpriseBasicInfo = enterpriseTurn.get(i);

            // 更新企业今年的订单选取顺序
            enterpriseBasicInfo.setSequence(i + 1);
            enterpriseBasicInfoRepository.save(enterpriseBasicInfo);
        }
    }
}