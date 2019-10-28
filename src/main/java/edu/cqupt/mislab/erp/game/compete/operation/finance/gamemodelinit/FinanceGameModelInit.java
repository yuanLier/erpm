package edu.cqupt.mislab.erp.game.compete.operation.finance.gamemodelinit;

import edu.cqupt.mislab.erp.commons.constant.FinanceOperationConstant;
import edu.cqupt.mislab.erp.game.compete.basic.GameModelInit;
import edu.cqupt.mislab.erp.game.compete.basic.impl.GameModelInitService;
import edu.cqupt.mislab.erp.game.compete.operation.constant.GameSettingConstant;
import edu.cqupt.mislab.erp.game.compete.operation.finance.dao.FinanceEnterpriseRepository;
import edu.cqupt.mislab.erp.game.compete.operation.finance.dao.GameLoanBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.finance.dao.LoanBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.entity.FinanceEnterpriseInfo;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.entity.GameLoanBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.entity.LoanBasicInfo;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-07-09 14:12
 * @description
 */
@Slf4j
@Component
public class FinanceGameModelInit implements GameModelInit {

    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired
    private FinanceEnterpriseRepository financeEnterpriseRepository;
    @Autowired
    private GameBasicInfoRepository gameBasicInfoRepository;
    @Autowired
    private LoanBasicInfoRepository loanBasicInfoRepository;
    @Autowired
    private GameLoanBasicInfoRepository gameLoanBasicInfoRepository;

    @Autowired
    private GameModelInitService gameModelInitService;


    @Override
    public List<String> initGameModel(Long gameId) {
        //判断是否已经被初始化过了
        if(gameModelInitService.addInitializedModelIfNotExist(gameId,this)){

            try{

                log.info("开始初始化财务模块的比赛数据");

                // 在比赛开始时初始化本场比赛中使用的贷款基本信息
                GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);

                List<LoanBasicInfo> loanBasicInfoList = loanBasicInfoRepository.findNewestLoanBasicInfos();
                for(LoanBasicInfo loanBasicInfo : loanBasicInfoList) {
                    gameLoanBasicInfoRepository.save(
                            GameLoanBasicInfo.builder()
                                    .gameBasicInfo(gameBasicInfo)
                                    .loanBasicInfo(loanBasicInfo)
                                    .build()
                    );
                }

                // 选取所有的比赛企业
                final List<EnterpriseBasicInfo> enterpriseBasicInfos = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId);

                // 为所有企业初始化财务信息
                for(EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfos){

                    // 获取设定的初始化金额
                    Double changeAmount = GameSettingConstant.INIT_FINANCE;

                    // 持久化财务信息
                    financeEnterpriseRepository.save(
                            FinanceEnterpriseInfo.builder()
                                    .enterpriseBasicInfo(enterpriseBasicInfo)
                                    .changeOperating(FinanceOperationConstant.INIT_AMOUNT)
                                    .changeAmount(changeAmount)
                                    .currentAccount(changeAmount)
                                    .current(true)
                                    .build()
                    );
                }

                return null;

            }catch(Exception e){
                e.printStackTrace();
            }

            return Collections.singletonList("财务模块比赛数据初始化出错");
        }

        return null;
    }

}
