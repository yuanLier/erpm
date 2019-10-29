package edu.cqupt.mislab.erp.game.compete.operation.finance.advance;

import edu.cqupt.mislab.erp.commons.constant.FinanceOperationConstant;
import edu.cqupt.mislab.erp.commons.util.LoanAmountUtil;
import edu.cqupt.mislab.erp.game.compete.basic.ModelAdvance;
import edu.cqupt.mislab.erp.game.compete.operation.finance.dao.GameLoanBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.finance.dao.LoanEnterpriseRepository;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.entity.LoanBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.entity.LoanEnterpriseInfo;
import edu.cqupt.mislab.erp.game.compete.operation.finance.service.FinanceService;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-10-29 15:43
 * @description 财务模块周期推进
 */

@Slf4j
@Component
public class LoanAdvance implements ModelAdvance {

    @Autowired
    private GameLoanBasicInfoRepository gameLoanBasicInfoRepository;
    @Autowired
    private LoanEnterpriseRepository loanEnterpriseRepository;

    @Autowired
    private FinanceService financeService;

    @Override
    public boolean modelHistory(EnterpriseBasicInfo enterpriseBasicInfo) {

        log.info("开始记录财务模块比赛期间历史数据");

        // todo 财务模块历史数据记录

        log.info("财务模块-比赛期间历史数据记录成功");

        return true;
    }

    @Override
    public boolean modelAdvance(EnterpriseBasicInfo enterpriseBasicInfo) {

        log.info("财务模块-比赛期间周期推进成功");

        // 获取企业全部未还款的贷款信息
        List<LoanEnterpriseInfo> loanEnterpriseInfoList = loanEnterpriseRepository.getLoansOfEnterprise(null, false, enterpriseBasicInfo.getId());

        for(LoanEnterpriseInfo loanEnterpriseInfo : loanEnterpriseInfoList) {
            LoanBasicInfo loanBasicInfo = loanEnterpriseInfo.getGameLoanBasicInfo().getLoanBasicInfo();
            // 如果到了最后还款日期还没还上的，强制还款并收取违约金
            if(loanEnterpriseInfo.getBeginPeriod()+loanBasicInfo.getMaxDuration() == enterpriseBasicInfo.getEnterpriseCurrentPeriod()) {
                String changeOperating = FinanceOperationConstant.LOAN_FORCE;
                Double changeAmount = LoanAmountUtil.getRepaymentAmount(loanEnterpriseInfo);
                financeService.updateFinanceInfo(enterpriseBasicInfo.getId(), changeOperating, changeAmount, true, true);
                // 虽然是强制的但还是要给人家设置为已还款的
                loanEnterpriseInfo.setRepaid(true);
                loanEnterpriseRepository.save(loanEnterpriseInfo);
            }
        }

        log.info("财务模块-比赛期间周期成功");

        return true;
    }
}
