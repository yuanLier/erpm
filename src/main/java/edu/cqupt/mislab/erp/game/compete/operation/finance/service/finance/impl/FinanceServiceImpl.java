package edu.cqupt.mislab.erp.game.compete.operation.finance.service.finance.impl;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.util.BeanCopyUtil;
import edu.cqupt.mislab.erp.game.compete.operation.finance.dao.FinanceEnterpriseRepository;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.entity.FinanceEnterpriseInfo;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.vo.FinanceEnterpriseVo;
import edu.cqupt.mislab.erp.game.compete.operation.finance.service.finance.FinanceService;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toFailResponseVoWithMessage;

/**
 * @author yuanyiwen
 * @create 2019-05-22 8:41
 * @description
 */
@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private FinanceEnterpriseRepository financeEnterpriseRepository;

    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WebResponseVo updateFinanceInfo(Long enterpriseId, String changeOperating, double changeAmount, boolean minus) {

        // 是否为扣除操作
        changeAmount = minus ? -changeAmount : changeAmount;

        // 获取企业的当前账户余额
        FinanceEnterpriseInfo financeEnterpriseInfo = financeEnterpriseRepository.findByEnterpriseBasicInfo_IdAndCurrent(enterpriseId, true);

        // 若当前余额不支持该操作
        Double account = financeEnterpriseInfo.getCurrentAccount();
        if(account + changeAmount < 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "操作失败！请检查当前余额");
        }

        // 走到这里说明允许更新账户余额

        // 关闭前一个账户余额信息
        financeEnterpriseInfo.setCurrent(false);
        financeEnterpriseRepository.save(financeEnterpriseInfo);

        // 构建并持久化新的余额信息
        EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);
        FinanceEnterpriseInfo currentInfo = FinanceEnterpriseInfo.builder()
                .enterpriseBasicInfo(enterpriseBasicInfo)
                .changeOperating(changeOperating)
                .changeAmount(changeAmount)
                .currentAccount(account + changeAmount)
                .current(true).build();
        financeEnterpriseRepository.save(currentInfo);

        return null;
    }

    @Override
    public List<FinanceEnterpriseVo> getAllFinanceInfoOfEnterprise(Long enterpriseId) {

        List<FinanceEnterpriseInfo> financeEnterpriseInfoList = financeEnterpriseRepository.findByEnterpriseBasicInfo_Id(enterpriseId);

        if (financeEnterpriseInfoList.size() == 0) {
            return null;
        }

        List<FinanceEnterpriseVo> financeEnterpriseVoList = new ArrayList<>();
        for (FinanceEnterpriseInfo financeEnterpriseInfo : financeEnterpriseInfoList) {
            FinanceEnterpriseVo financeEnterpriseVo = new FinanceEnterpriseVo();

            BeanCopyUtil.copyPropertiesSimple(financeEnterpriseInfo, financeEnterpriseVo);
            financeEnterpriseVoList.add(financeEnterpriseVo);
        }

        return financeEnterpriseVoList;
    }

    @Override
    public FinanceEnterpriseVo getCurrentFinanceInfo(Long enterpriseId) {

        FinanceEnterpriseInfo financeEnterpriseInfo = financeEnterpriseRepository.findByEnterpriseBasicInfo_IdAndCurrent(enterpriseId, true);

        if (financeEnterpriseInfo == null) {
            return null;
        }

        FinanceEnterpriseVo financeEnterpriseVo = new FinanceEnterpriseVo();
        BeanCopyUtil.copyPropertiesSimple(financeEnterpriseInfo, financeEnterpriseVo);

        return financeEnterpriseVo;
    }
}
