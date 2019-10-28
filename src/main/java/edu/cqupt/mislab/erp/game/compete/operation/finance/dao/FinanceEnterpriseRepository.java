package edu.cqupt.mislab.erp.game.compete.operation.finance.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.entity.FinanceEnterpriseInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-22 8:10
 * @description
 */
public interface FinanceEnterpriseRepository extends BasicRepository<FinanceEnterpriseInfo, Long>, JpaSpecificationExecutor {


    /**
     * 获取某一企业的全部财务记录
     * @param enterpriseId
     * @return
     */
    List<FinanceEnterpriseInfo> findByEnterpriseBasicInfo_Id(Long enterpriseId);


    /**
     * 获取某一企业的当前财务信息
     * @param enterpriseId
     * @return
     */
    FinanceEnterpriseInfo findByEnterpriseBasicInfo_IdAndCurrentIsTrue(Long enterpriseId);
}
