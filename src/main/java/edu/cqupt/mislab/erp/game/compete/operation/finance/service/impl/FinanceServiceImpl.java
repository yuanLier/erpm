package edu.cqupt.mislab.erp.game.compete.operation.finance.service.impl;

import edu.cqupt.mislab.erp.commons.aspect.InsufficientBalanceException;
import edu.cqupt.mislab.erp.commons.util.BeanCopyUtil;
import edu.cqupt.mislab.erp.game.compete.operation.finance.dao.FinanceEnterpriseRepository;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.entity.FinanceEnterpriseInfo;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.vo.FinanceEnterpriseVo;
import edu.cqupt.mislab.erp.game.compete.operation.finance.service.FinanceService;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-22 8:41
 * @description
 */
@Slf4j
@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private FinanceEnterpriseRepository financeEnterpriseRepository;

    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFinanceInfo(Long enterpriseId, String changeOperating, double changeAmount, boolean minus, boolean force) {

        // 是否为扣除操作
        changeAmount = minus ? -changeAmount : changeAmount;

        // 获取企业的当前账户余额
        FinanceEnterpriseInfo financeEnterpriseInfo = financeEnterpriseRepository.findByEnterpriseBasicInfo_IdAndCurrentIsTrue(enterpriseId);

        // 若为非强制扣款且当前余额不支持该操作
        Double account = financeEnterpriseInfo.getCurrentAccount() + changeAmount;
        if(!force && account < 0) {
            throw new InsufficientBalanceException();
        }

        // 走到这里说明允许更新账户余额

        // 保留两位小数
        account = new BigDecimal(account).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        // 关闭前一个账户余额信息
        financeEnterpriseInfo.setCurrent(false);
        financeEnterpriseRepository.save(financeEnterpriseInfo);

        // 构建并持久化新的余额信息
        EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);
        FinanceEnterpriseInfo currentInfo = FinanceEnterpriseInfo.builder()
                .enterpriseBasicInfo(enterpriseBasicInfo)
                .changeOperating(changeOperating)
                .changeAmount(changeAmount)
                .currentAccount(account)
                .current(true).build();
        financeEnterpriseRepository.save(currentInfo);

        log.info("余额变动成功！操作 ：" + changeOperating + "；变动数量 ：" + changeAmount + "；当前余额 ：" + account);
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
            financeEnterpriseVo.setEnterpriseId(financeEnterpriseInfo.getEnterpriseBasicInfo().getId());
            financeEnterpriseVoList.add(financeEnterpriseVo);
        }

        return financeEnterpriseVoList;
    }

    @Override
    public FinanceEnterpriseVo getCurrentFinanceInfo(Long enterpriseId) {

        FinanceEnterpriseInfo financeEnterpriseInfo = financeEnterpriseRepository.findByEnterpriseBasicInfo_IdAndCurrentIsTrue(enterpriseId);

        if (financeEnterpriseInfo == null) {
            return null;
        }

        FinanceEnterpriseVo financeEnterpriseVo = new FinanceEnterpriseVo();
        financeEnterpriseVo.setEnterpriseId(financeEnterpriseInfo.getEnterpriseBasicInfo().getId());
        BeanCopyUtil.copyPropertiesSimple(financeEnterpriseInfo, financeEnterpriseVo);

        return financeEnterpriseVo;
    }
}
