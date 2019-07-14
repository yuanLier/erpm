package edu.cqupt.mislab.erp.game.compete.operation.finance.service.impl;

import edu.cqupt.mislab.erp.commons.constant.FinanceOperationConstant;
import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.game.compete.operation.finance.dao.GameLoanBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.finance.dao.LoanEnterpriseRepository;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.dto.LoanEnterpriseDto;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.dto.LoanSelectDto;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.entity.GameLoanBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.entity.LoanEnterpriseInfo;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.vo.LoanBasicDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.vo.LoanEnterpriseDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.finance.service.FinanceService;
import edu.cqupt.mislab.erp.game.compete.operation.finance.service.LoanService;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-07-11 16:20
 * @description
 */
@Service
public class LoanServiceImpl implements LoanService {


    @Autowired
    private GameLoanBasicInfoRepository gameLoanBasicInfoRepository;
    @Autowired
    private LoanEnterpriseRepository loanEnterpriseRepository;
    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;

    @Autowired
    private FinanceService financeService;


    @Override
    public List<LoanBasicDisplayVo> getAllLoanBasicDisplayInfo(Long gameId) {

        List<GameLoanBasicInfo> gameLoanBasicInfoList = gameLoanBasicInfoRepository.findByGameBasicInfo_Id(gameId);

        List<LoanBasicDisplayVo> loanBasicDisplayVoList = new ArrayList<>();
        for(GameLoanBasicInfo gameLoanBasicInfo : gameLoanBasicInfoList) {

            LoanBasicDisplayVo loanBasicDisplayVo = new LoanBasicDisplayVo();
            loanBasicDisplayVo.setId(gameLoanBasicInfo.getId());
            loanBasicDisplayVo.setLoanRate(gameLoanBasicInfo.getLoanBasicInfo().getLoanRate());
            loanBasicDisplayVo.setLoanType(gameLoanBasicInfo.getLoanBasicInfo().getLoanType());
            loanBasicDisplayVo.setMaxDuration(gameLoanBasicInfo.getLoanBasicInfo().getMaxDuration());

            loanBasicDisplayVoList.add(loanBasicDisplayVo);
        }

        return loanBasicDisplayVoList;
    }

    @Override
    public LoanEnterpriseDisplayVo submitEnterpriseLoan(LoanEnterpriseDto loanEnterpriseDto) {

        EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(loanEnterpriseDto.getEnterpriseId());
        GameLoanBasicInfo gameLoanBasicInfo = gameLoanBasicInfoRepository.findOne(loanEnterpriseDto.getLoanBasicId());
        Double loanAmount = loanEnterpriseDto.getLoanAmount();

        // 还是要校验一下
        if(enterpriseBasicInfo == null || gameLoanBasicInfo == null || loanAmount < 0.0001) {
            return null;
        }

        // 生成并保存该条贷款信息
        LoanEnterpriseInfo loanEnterpriseInfo = LoanEnterpriseInfo.builder()
                .beginPeriod(enterpriseBasicInfo.getEnterpriseCurrentPeriod())
                .endPeriod(null)
                .repaid(false)
                .enterpriseBasicInfo(enterpriseBasicInfo)
                .gameLoanBasicInfo(gameLoanBasicInfo)
                .loanAmount(loanAmount)
                .loanType(gameLoanBasicInfo.getLoanBasicInfo().getLoanType())
                .build();
        loanEnterpriseRepository.save(loanEnterpriseInfo);

        // 打钱
        String changeOperating = FinanceOperationConstant.LOAN_AMOUNT;
        financeService.updateFinanceInfo(enterpriseBasicInfo.getId(), changeOperating, loanAmount, false, true);

        return EntityVoUtil.copyFieldsFromEntityToVo(loanEnterpriseInfo);

    }

    @Override
    public LoanEnterpriseDisplayVo repayEnterpriseLoan(Long loanEnterpriseId) {

        // 获取该条贷款信息
        LoanEnterpriseInfo loanEnterpriseInfo = loanEnterpriseRepository.findOne(loanEnterpriseId);

        EnterpriseBasicInfo enterpriseBasicInfo = loanEnterpriseInfo.getEnterpriseBasicInfo();

        // 计算还款数量
        Integer totalYear = enterpriseBasicInfo.getGameBasicInfo().getGameInitBasicInfo().getTotalYear();
        Integer year = (enterpriseBasicInfo.getEnterpriseCurrentPeriod()-loanEnterpriseInfo.getBeginPeriod())/totalYear;
        Double amount = loanEnterpriseInfo.getLoanAmount()*Math.pow(1+loanEnterpriseInfo.getGameLoanBasicInfo().getLoanBasicInfo().getLoanRate(), year);
        // 扣钱
        String changeOperating = FinanceOperationConstant.LOAN_REPLAY;
        financeService.updateFinanceInfo(enterpriseBasicInfo.getId(), changeOperating, amount, true, true);

        // 更新还款情况
        loanEnterpriseInfo.setEndPeriod(loanEnterpriseInfo.getEnterpriseBasicInfo().getEnterpriseCurrentPeriod());
        loanEnterpriseInfo.setRepaid(true);
        loanEnterpriseRepository.save(loanEnterpriseInfo);

        return EntityVoUtil.copyFieldsFromEntityToVo(loanEnterpriseInfo);
    }

    @Override
    public List<LoanEnterpriseDisplayVo> getLoansOfEnterprise(LoanSelectDto loanSelectDto) {


        List<LoanEnterpriseInfo> loanEnterpriseInfoList = loanEnterpriseRepository.getLoansOfEnterprise(loanSelectDto.getLoanType(), loanSelectDto.getRepaid());

        List<LoanEnterpriseDisplayVo> loanEnterpriseDisplayVoList = new ArrayList<>();
        for (LoanEnterpriseInfo loanEnterpriseInfo : loanEnterpriseInfoList) {
            loanEnterpriseDisplayVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(loanEnterpriseInfo));
        }

        return loanEnterpriseDisplayVoList;
    }
}
