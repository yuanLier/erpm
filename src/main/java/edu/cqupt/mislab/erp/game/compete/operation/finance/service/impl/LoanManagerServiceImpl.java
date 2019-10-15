package edu.cqupt.mislab.erp.game.compete.operation.finance.service.impl;

import edu.cqupt.mislab.erp.commons.aspect.BadModificationException;
import edu.cqupt.mislab.erp.commons.util.BeanCopyUtil;
import edu.cqupt.mislab.erp.game.compete.operation.finance.dao.LoanBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.dto.LoanBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.entity.LoanBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.vo.LoanBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.finance.service.LoanManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-07-13 19:41
 * @description
 */
@Service
public class LoanManagerServiceImpl implements LoanManagerService {


    @Autowired
    private LoanBasicInfoRepository loanBasicInfoRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoanBasicVo addLoanBasicInfo(LoanBasicDto loanBasicDto) {

        // 将接受到的dto中的数据复制给loanBasicInfo
        LoanBasicInfo loanBasicInfo = new LoanBasicInfo();
        BeanCopyUtil.copyPropertiesSimple(loanBasicDto,loanBasicInfo);

        // 启用该条设置
        loanBasicInfo.setEnable(true);

        // 保存修改并刷新
        loanBasicInfo = loanBasicInfoRepository.saveAndFlush(loanBasicInfo);

        // 将获取了新id的info数据复制给loanBasicVo
        LoanBasicVo loanBasicVo = new LoanBasicVo();
        BeanCopyUtil.copyPropertiesSimple(loanBasicInfo, loanBasicVo);
        loanBasicVo.setLoanBasicId(loanBasicInfo.getId());

        // 返回vo
        return loanBasicVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoanBasicVo updateLoanBasicInfo(Long loanBasicId, LoanBasicDto loanBasicDto) {

        // 获取之前的贷款方式信息并设置为不启用
        LoanBasicInfo loanBasicInfo = loanBasicInfoRepository.findOne(loanBasicId);
        if(!loanBasicInfo.isEnable()) {
            throw new BadModificationException();
        }
        loanBasicInfo.setEnable(false);

        loanBasicInfoRepository.save(loanBasicInfo);

        // 重新生成一条数据
        LoanBasicInfo newLoanBasicInfo = new LoanBasicInfo();
        BeanCopyUtil.copyPropertiesSimple(loanBasicDto,newLoanBasicInfo);
        // 设置可用
        newLoanBasicInfo.setEnable(true);

        newLoanBasicInfo = loanBasicInfoRepository.saveAndFlush(newLoanBasicInfo);

        LoanBasicVo loanBasicVo = new LoanBasicVo();
        BeanCopyUtil.copyPropertiesSimple(newLoanBasicInfo, loanBasicVo);
        loanBasicVo.setLoanBasicId(newLoanBasicInfo.getId());

        return loanBasicVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoanBasicVo closeLoanBasicInfo(Long loanBasicId) {

        // 获取这个贷款基本信息
        LoanBasicInfo loanBasicInfo = loanBasicInfoRepository.findOne(loanBasicId);

        // 设置为不启用
        loanBasicInfo.setEnable(false);

        // 保存修改
        loanBasicInfoRepository.save(loanBasicInfo);

        LoanBasicVo loanBasicVo = new LoanBasicVo();
        BeanCopyUtil.copyPropertiesSimple(loanBasicInfo, loanBasicVo);
        loanBasicVo.setLoanBasicId(loanBasicInfo.getId());

        return loanBasicVo;
    }

    @Override
    public List<LoanBasicVo> getAllLoanBasicVoOfStatus(boolean enable) {

        List<LoanBasicInfo> loanBasicInfoList = loanBasicInfoRepository.findByEnable(enable);

        List<LoanBasicVo> loanBasicVoList = new ArrayList<>();
        for (LoanBasicInfo loanBasicInfo : loanBasicInfoList) {
            LoanBasicVo loanBasicVo = new LoanBasicVo();
            BeanCopyUtil.copyPropertiesSimple(loanBasicInfo, loanBasicVo);
            loanBasicVo.setLoanBasicId(loanBasicInfo.getId());

            loanBasicVoList.add(loanBasicVo);
        }

        return loanBasicVoList;
    }
}
