package edu.cqupt.mislab.erp.game.compete.operation.finance.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.entity.LoanEnterpriseInfo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-07-11 16:11
 * @description
 */
public interface LoanEnterpriseRepository extends BasicRepository<LoanEnterpriseInfo, Long> {


    /**
     * 贷款类型与还款状态的组合查询，参数允许为空
     * @param loanType
     * @param repaid
     * @return
     */
    @Query(value = "select * from loan_enterprise_info l where if(?1 != '', l.loan_type = ?1, 1=1) and if(?2 != '', l.repaid = ?2, 1=1)", nativeQuery = true)
    List<LoanEnterpriseInfo> getLoansOfEnterprise(String loanType, Boolean repaid);

}
