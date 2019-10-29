package edu.cqupt.mislab.erp.game.compete.operation.finance.modelinit;

import edu.cqupt.mislab.erp.commons.basic.modelinit.ModelInit;
import edu.cqupt.mislab.erp.commons.basic.modelinit.ModelInitService;
import edu.cqupt.mislab.erp.game.compete.operation.finance.dao.LoanBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.entity.LoanBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-07-13 21:37
 * @description
 */
@Slf4j
@Component
public class FinanceModelInit implements ModelInit {

    @Autowired
    private LoanBasicInfoRepository loanBasicInfoRepository;

    @Autowired
    private ModelInitService modelInitService;

    @Override
    public List<String> applicationModelInit(){

        //判断当前模块是否已经被初始化了
        if(modelInitService.addInitializedModelIfNotExist(this)){

            try{
                log.info("开始初始化贷款基本信息");

                //初始化项目的贷款基本信息
                initLoanBasicInfo();

                log.info("初始化应用贷款基本信息完成");

                return null;
            }catch(Exception e){
                e.printStackTrace();
            }

            return Collections.singletonList("贷款基本信息初始化出现错误");
        }

        return null;
    }


    /**
     * 添加项目启动时的初始贷款信息
     */
    private void initLoanBasicInfo(){

        loanBasicInfoRepository.save(
                LoanBasicInfo.builder()
                        .loanRate(0.12)
                        .penaltyRate(0.06)
                        .loanType("长期贷款")
                        .maxDuration(10)
                        .enable(true)
                        .build()
        );

        loanBasicInfoRepository.save(
                LoanBasicInfo.builder()
                        .loanRate(0.08)
                        .penaltyRate(0.03)
                        .loanType("短期贷款")
                        .maxDuration(3)
                        .enable(true)
                        .build()
        );

        loanBasicInfoRepository.save(
                LoanBasicInfo.builder()
                        .loanRate(0.37)
                        .penaltyRate(0.1)
                        .loanType("高利贷")
                        .maxDuration(7)
                        .enable(true)
                        .build()
        );
    }


}
