package edu.cqupt.mislab.erp.game.compete.basic.impl;

import edu.cqupt.mislab.erp.commons.constant.FinanceOperationConstant;
import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.util.LoanAmountUtil;
import edu.cqupt.mislab.erp.commons.websocket.CommonWebSocketMessagePublisher;
import edu.cqupt.mislab.erp.game.compete.basic.ModelAdvance;
import edu.cqupt.mislab.erp.game.compete.operation.finance.dao.FinanceEnterpriseRepository;
import edu.cqupt.mislab.erp.game.compete.operation.finance.dao.LoanEnterpriseRepository;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.entity.LoanEnterpriseInfo;
import edu.cqupt.mislab.erp.game.compete.operation.finance.service.FinanceService;
import edu.cqupt.mislab.erp.game.compete.operation.order.service.OrderChooseService;
import edu.cqupt.mislab.erp.game.manage.constant.ManageConstant;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.*;

/**
 * @author yuanyiwen
 * @create 2019-07-10 14:05
 * @description 用于进行周期推进和年推进
 */
@Slf4j
@Service
public class ModelAdvanceService implements ApplicationContextAware {

    /**
     * 用于发现Bean模块
     */
    private ApplicationContext applicationContext;

    @Autowired
    GameBasicInfoRepository gameBasicInfoRepository;
    @Autowired
    EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired
    FinanceEnterpriseRepository financeEnterpriseRepository;
    @Autowired
    LoanEnterpriseRepository loanEnterpriseRepository;

    @Autowired
    OrderChooseService orderChooseService;
    @Autowired
    FinanceService financeService;

    @Autowired
    CommonWebSocketMessagePublisher webSocketMessagePublisher;

    /**
     * 这是考虑年推进的情况
     *
     * @param enterpriseId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public WebResponseVo advance(Long enterpriseId) {

        final EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        // 若企业未处于比赛进行阶段，则不允许进行周期推进
        if (!EnterpriseStatusEnum.PLAYING.equals(enterpriseBasicInfo.getEnterpriseStatus())) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "企业未处于比赛进行阶段，无法推进周期！");
        }

        // 判断下一周期是否为新的一年
        Integer currentPeriod = enterpriseBasicInfo.getEnterpriseCurrentPeriod();
        Integer totalPeriod = enterpriseBasicInfo.getGameBasicInfo().getGameInitBasicInfo().getPeriodOfOneYear();

        // 若下一周期即新的一年
        if (currentPeriod % totalPeriod == 0) {

            // 获取当前比赛的全部存活中企业
            Long gameId = enterpriseBasicInfo.getGameBasicInfo().getId();
            List<EnterpriseBasicInfo> enterpriseBasicInfoList = enterpriseBasicInfoRepository.findByGameBasicInfo_IdAndEnterpriseStatus(gameId, EnterpriseStatusEnum.PLAYING);

            // 判断是否所有企业都达到了这个阶段
            for (EnterpriseBasicInfo enterprise : enterpriseBasicInfoList) {
                if (!currentPeriod.equals(enterprise.getEnterpriseCurrentPeriod())) {
                    // 若不是全部企业均到达，则不允许进行周期推进
                    return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.FORBIDDEN, "比赛将在全部企业完成推进后统一进入下一年，请耐心等待");
                }
            }

            // 如果所有企业都到达

            // 同时进行所有企业的周期推进
            for (EnterpriseBasicInfo enterprise : enterpriseBasicInfoList) {

                log.warn("============================ "+enterprise.getId()+" =================================");

                periodAdvance(enterprise);

                // 更新企业订单会相关记录
                enterprise.setAdvertising(true);
                enterprise.setFinishAdvertising(false);
                enterprise.setSequence(0);
                enterprise.setMyTurn(false);
                enterprise.setFinishChoice(false);
                enterpriseBasicInfoRepository.save(enterprise);
            }

            // 走到这里说明推进成功

            log.info("开始推进比赛：" + gameId + " 进入下一年");

            // 更新比赛所处的年份
            GameBasicInfo gameBasicInfo = enterpriseBasicInfo.getGameBasicInfo();
            Integer year = gameBasicInfo.getGameCurrentYear() + 1;
            gameBasicInfo.setGameCurrentYear(year);
            gameBasicInfoRepository.save(gameBasicInfo);
            // 通知前端已经进入下一年
            webSocketMessagePublisher.publish(gameId, new TextMessage(ManageConstant.NEW_YEAR + gameBasicInfo.getGameCurrentYear()));
            // 生成可供企业选择的订单
            orderChooseService.selectOrdersOfOneYear(gameId, year);
            // 通知前端订单会已开始
            webSocketMessagePublisher.publish(gameId, new TextMessage(ManageConstant.ORDER_MEETING_BEGIN + gameId));

            log.info("比赛：" + gameId + " 进入下一年，订单会已开始");

            return toSuccessResponseVoWithNoData();
        }

        // 若下一年不是新的一年，正常进行周期推进
        return periodAdvance(enterpriseBasicInfo);
    }

    /**
     * 这是单独的周期推进
     *
     * @param enterpriseBasicInfo
     */
    private WebResponseVo periodAdvance(EnterpriseBasicInfo enterpriseBasicInfo) {

        Long enterpriseId = enterpriseBasicInfo.getId();

        log.info("开始推进企业：" + enterpriseId + " 进入下一个比赛周期");

        //获取所有的模块的推进函数
        final Map<String, ModelAdvance> advanceMap = applicationContext.getBeansOfType(ModelAdvance.class);

        if (advanceMap != null && advanceMap.size() > 0) {

            final Iterator<String> iterator = advanceMap.keySet().iterator();

            while (iterator.hasNext()) {

                final ModelAdvance modelAdvance = advanceMap.get(iterator.next());

                //只要有一个模块没有被推进完成将无法完成周期的推进
                if (!modelAdvance.advance(enterpriseBasicInfo)) {

                    log.info("推进企业：" + enterpriseId + " 进入下一个比赛周期失败");

                    throw new RuntimeException("模块推进异常");
                }
            }
        }

        // 走到这里说明模块推进成功

        // 若推进周期后，企业资产为负，企业破产，比赛结束
        Double currentFinanceAmount = financeEnterpriseRepository.findByEnterpriseBasicInfo_IdAndCurrentIsTrue(enterpriseId).getCurrentAccount();
        if (currentFinanceAmount < 0) {

            // 更新企业所处状态
            enterpriseBasicInfo.setEnterpriseStatus(EnterpriseStatusEnum.BANKRUPT);
            enterpriseBasicInfoRepository.save(enterpriseBasicInfo);

            // 判断这个比赛是否随之结束
            gameOverJudge(enterpriseBasicInfo);

            return toSuccessResponseVoWithData("企业破产，游戏结束！");
        }


        log.info("比赛各模块推进成功，企业将进入下一周期...");

        enterpriseBasicInfo.setEnterpriseCurrentPeriod(enterpriseBasicInfo.getEnterpriseCurrentPeriod() + 1);

        // 若推进周期后，企业完成全部比赛进程，则更新企业状态：若账户余额支持还款，则正常结束比赛；否则，以破产的方式结束
        GameInitBasicInfo gameInitBasicInfo = enterpriseBasicInfo.getGameBasicInfo().getGameInitBasicInfo();
        Integer overPeriod = gameInitBasicInfo.getPeriodOfOneYear() * gameInitBasicInfo.getTotalYear();
        if (overPeriod.equals(enterpriseBasicInfo.getEnterpriseCurrentPeriod())) {

            // 默认为正常结束比赛
            EnterpriseStatusEnum enterpriseStatus = EnterpriseStatusEnum.OVER;
            String message = "企业已完成全部周期，游戏结束！";

            // 归还全部未归还的借款并更新贷款状态
            Double totalLoanAmount = 0D;
            List<LoanEnterpriseInfo> loanEnterpriseInfoList = loanEnterpriseRepository.getLoansOfEnterprise(null, false, enterpriseId);
            for(LoanEnterpriseInfo loanEnterpriseInfo : loanEnterpriseInfoList) {
                loanEnterpriseInfo.setEndPeriod(enterpriseBasicInfo.getEnterpriseCurrentPeriod());
                totalLoanAmount += LoanAmountUtil.getRepaymentAmount(loanEnterpriseInfo);
                loanEnterpriseInfo.setRepaid(true);
                loanEnterpriseRepository.save(loanEnterpriseInfo);
            }
            // 一次性更新企业余额
            if(totalLoanAmount > 0) {
                String changeOperating = FinanceOperationConstant.REPAY_WHEN_GAME_END;
                financeService.updateFinanceInfo(enterpriseId, changeOperating, totalLoanAmount, true, true);
            }

            // 若企业余额不足以支撑还款，更新结束状态与返回信息
            if(currentFinanceAmount < totalLoanAmount) {
                enterpriseStatus = EnterpriseStatusEnum.BANKRUPT;
                message = "企业已完成全部周期，当前余额不足以归还还款，企业破产，游戏结束！";
            }

            enterpriseBasicInfo.setEnterpriseStatus(enterpriseStatus);
            enterpriseBasicInfoRepository.save(enterpriseBasicInfo);

            gameOverJudge(enterpriseBasicInfo);

            return toSuccessResponseVoWithData(message);
        }

        log.info("推进企业：" + enterpriseId + " 进入下一个比赛周期成功");

        return toSuccessResponseVoWithNoData();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 一个企业结束运营后（不论是正常结束还是破产结束），判断是否比赛也随之结束
     * @param enterpriseBasicInfo
     */
    private void gameOverJudge(EnterpriseBasicInfo enterpriseBasicInfo) {
        // 获取当前比赛的全部存活中企业
        List<EnterpriseBasicInfo> enterpriseBasicInfoList = enterpriseBasicInfoRepository.findByGameBasicInfo_IdAndEnterpriseStatus(enterpriseBasicInfo.getGameBasicInfo().getId(), EnterpriseStatusEnum.PLAYING);
        // 如果这个企业退出后，比赛中一个存活的企业都没有了
        if(enterpriseBasicInfoList.size() == 0) {
            // 比赛结束
            GameBasicInfo gameBasicInfo = enterpriseBasicInfo.getGameBasicInfo();
            gameBasicInfo.setGameStatus(GameStatusEnum.OVER);
            gameBasicInfoRepository.save(gameBasicInfo);
        }
    }
}