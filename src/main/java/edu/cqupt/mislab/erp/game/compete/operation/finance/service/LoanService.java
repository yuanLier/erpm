package edu.cqupt.mislab.erp.game.compete.operation.finance.service;

import edu.cqupt.mislab.erp.game.compete.operation.finance.model.dto.LoanEnterpriseDto;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.dto.LoanSelectDto;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.vo.LoanBasicDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.vo.LoanEnterpriseDisplayVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-07-11 16:20
 * @description
 */
public interface LoanService {

    /**
     * 获取一场比赛中的全部可贷款类型及相关信息
     * @param gameId
     * @return
     */
    List<LoanBasicDisplayVo> getAllLoanBasicDisplayInfo(Long gameId);


    /**
     * 提交贷款
     * @param loanEnterpriseDto
     * @return
     */
    LoanEnterpriseDisplayVo submitEnterpriseLoan(LoanEnterpriseDto loanEnterpriseDto);


    /**
     * 归还贷款
     * @param loanEnterpriseId
     * @return
     */
    LoanEnterpriseDisplayVo repayEnterpriseLoan(Long loanEnterpriseId);


    /**
     * 贷款类型与还款状态的组合查询，参数允许为空
     * @param loanSelectDto
     * @return
     */
    List<LoanEnterpriseDisplayVo> getLoansOfEnterprise(LoanSelectDto loanSelectDto);

}
