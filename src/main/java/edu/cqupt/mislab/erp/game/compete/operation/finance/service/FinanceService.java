package edu.cqupt.mislab.erp.game.compete.operation.finance.service;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.vo.FinanceEnterpriseVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-22 8:22
 * @description
 */
public interface FinanceService {

    /**
     * 更新企业财务信息，只允许在后台程序间调用
     * @param enterpriseId 企业id
     * @param changeOperating 造成企业财务改变的操作
     * @param changeAmount 造成企业财务改变的金额，传入时金额统一为正
     * @param minus 是否为扣除操作
     */
    WebResponseVo updateFinanceInfo(Long enterpriseId, String changeOperating, double changeAmount, boolean minus);


    /**
     * 获取某一企业的全部财务记录
     * @param enterpriseId
     * @return
     */
    List<FinanceEnterpriseVo> getAllFinanceInfoOfEnterprise(Long enterpriseId);


    /**
     * 获取某一企业的当前（最新）财务信息
     * @param enterpriseId
     * @return
     */
    FinanceEnterpriseVo getCurrentFinanceInfo(Long enterpriseId);
}
