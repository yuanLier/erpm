package edu.cqupt.mislab.erp.commons.util;

import edu.cqupt.mislab.erp.game.compete.operation.finance.model.entity.LoanEnterpriseInfo;

/**
 * @author yuanyiwen
 * @create 2019-10-29 17:14
 * @description 计算贷款还款时具体所需还款数量的工具类
 */
public abstract class LoanAmountUtil {

    /**
     * 计算还款数量
     * @param loanEnterpriseInfo
     * @return
     */
    public static double getRepaymentAmount(LoanEnterpriseInfo loanEnterpriseInfo) {

        double amount = 0D;
        int duration = loanEnterpriseInfo.getGameLoanBasicInfo().getLoanBasicInfo().getMaxDuration();
        if(loanEnterpriseInfo.getEndPeriod() == null) {
            // 若还款日期为空，说明是非主动性还款，即逾期自动还款：则需要算上赔偿金
            amount += loanEnterpriseInfo.getLoanAmount()*loanEnterpriseInfo.getGameLoanBasicInfo().getLoanBasicInfo().getPenaltyRate();
        } else {
            // 若为主动还款，周期数更新为借款到还款这段时间的总周期数
            duration = loanEnterpriseInfo.getEndPeriod() - loanEnterpriseInfo.getBeginPeriod();
        }

        // 通过年利率与每年的周期数计算周期利率
        duration /= loanEnterpriseInfo.getEnterpriseBasicInfo().getGameBasicInfo().getGameInitBasicInfo().getPeriodOfOneYear();
        amount += loanEnterpriseInfo.getLoanAmount()*Math.pow(1+loanEnterpriseInfo.getGameLoanBasicInfo().getLoanBasicInfo().getLoanRate(), duration);

        return amount;
    }
}
