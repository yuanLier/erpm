package edu.cqupt.mislab.erp.game.compete.operation.finance.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.entity.LoanBasicInfo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-07-11 16:10
 * @description
 */
public interface LoanBasicInfoRepository extends BasicRepository<LoanBasicInfo, Long> {


    /**
     * 选取当前版本下的全部贷款基本信息
     * @return
     */
    @Query("from LoanBasicInfo l where l.enable = true group by l.loanType")
    List<LoanBasicInfo> findNewestLoanBasicInfos();


    /**
     * 查找可用or不可用的
     * @param enable
     * @return
     */
    List<LoanBasicInfo> findByEnable(boolean enable);
}
