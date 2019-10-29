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
     * 2019/8/4更新 ：
     *  说出来你可能不信  false != '' 结果为false  false != null 结果也为false  砰wsl
     *  不知道为什么SQL里面好像默认了false在某种程度上等价于''等价于null，以至于以下语句的效果完全相同：
     *      select * from loan_enterprise_info l where l.game_loan_basic_info_id = 1 and if(false != null, l.repaid = false, 1=1);
     *      select * from loan_enterprise_info l where l.game_loan_basic_info_id = 1 and if(false != '', l.repaid = false, 1=1);
     *      select * from loan_enterprise_info l where l.game_loan_basic_info_id = 1 and 1=1;
     *  所以这边只能把两个逻辑分开了，l.game_loan_basic_info_id(非布尔值)为空时可以直接在这里判断，但l.repaid(布尔值)为空的情况就必须单独提出来
     * @param gameLoanBasicInfoId
     * @param repaid
     * @return
     */
    @Query(value = "select * from loan_enterprise_info l where if(?1 != '', l.game_loan_basic_info_id = ?1, 1=1) and l.repaid = ?2 and enterprise_basic_info_id = ?3", nativeQuery = true)
    List<LoanEnterpriseInfo> getLoansOfEnterprise(Long gameLoanBasicInfoId, Boolean repaid, Long enterpriseId);

    /**
     * 当repaid为空，调用这个函数
     * @param gameLoanBasicInfoId
     * @return
     */
    @Query(value = "select * from loan_enterprise_info l where if(?1 != '', l.game_loan_basic_info_id = ?1, 1=1) and enterprise_basic_info_id = ?2", nativeQuery = true)
    List<LoanEnterpriseInfo> getLoansOfEnterprise(Long gameLoanBasicInfoId, Long enterpriseId);
}
