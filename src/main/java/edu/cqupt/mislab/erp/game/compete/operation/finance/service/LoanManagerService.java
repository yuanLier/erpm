package edu.cqupt.mislab.erp.game.compete.operation.finance.service;


import edu.cqupt.mislab.erp.game.compete.operation.finance.model.dto.LoanBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.vo.LoanBasicVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-07-13 19:40
 * @description 贷款基本信息的管理端接口
 */
public interface LoanManagerService {


    /**
     * 增加一个贷款基本信息
     * @param loanBasicDto
     * @return
     */
    LoanBasicVo addLoanBasicInfo(LoanBasicDto loanBasicDto);


    /**
     * 修改贷款基本信息
     * @param loanBasicId
     * @param loanBasicDto
     * @return
     */
    LoanBasicVo updateLoanBasicInfo(Long loanBasicId, LoanBasicDto loanBasicDto);


    /**
     * 关闭一个贷款基本信息
     * @param loanBasicId
     * @return
     */
    LoanBasicVo closeLoanBasicInfo(Long loanBasicId);


    /**
     * 获取处于某一状态（可用or不可用）的贷款基本信息
     * @param enable 是否可用
     * @return
     */
    List<LoanBasicVo> getAllLoanBasicVoOfStatus(boolean enable);

}
