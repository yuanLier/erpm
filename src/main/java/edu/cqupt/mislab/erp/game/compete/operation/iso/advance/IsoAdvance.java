package edu.cqupt.mislab.erp.game.compete.operation.iso.advance;

import edu.cqupt.mislab.erp.commons.constant.FinanceOperationConstant;
import edu.cqupt.mislab.erp.game.compete.basic.ModelAdvance;
import edu.cqupt.mislab.erp.game.compete.operation.finance.service.FinanceService;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoHistoryRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoHistoryInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoStatusEnum;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-08 17:58
 * @description ISO模块周期推进
 */
@Slf4j
@Service
public class IsoAdvance implements ModelAdvance {

    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired
    private IsoDevelopInfoRepository isoDevelopInfoRepository;
    @Autowired
    private IsoHistoryRepository isoHistoryRepository;

    @Autowired
    private FinanceService financeService;


    /**
     * Iso模块比赛期间历史数据 ：
     *      查一下企业拥有的ISO信息就好
     *      市场开拓、产品研发同理（产品生产隶属于生产线 所以不在这里考虑）
     * @param gameId
     * @return
     */
    @Override
    public boolean modelHistory(Long gameId) {

        log.info("开始记录iso模块比赛期间历史数据");

        // 获取该比赛中全部进行中企业
        List<EnterpriseBasicInfo> enterpriseBasicInfoList = enterpriseBasicInfoRepository.findByGameBasicInfo_IdAndEnterpriseStatus(gameId, EnterpriseStatusEnum.PLAYING);

        for(EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfoList) {

            // 获取全部认证完成的iso
            List<IsoDevelopInfo> isoDevelopInfoList = isoDevelopInfoRepository.findByEnterpriseBasicInfo_IdAndIsoStatus(enterpriseBasicInfo.getId(), IsoStatusEnum.DEVELOPED);

            for(IsoDevelopInfo isoDevelopInfo : isoDevelopInfoList) {

                IsoHistoryInfo isoHistoryInfo = new IsoHistoryInfo();

                isoHistoryInfo.setEnterpriseBasicInfo(enterpriseBasicInfo);
                isoHistoryInfo.setIsoDevelopInfo(isoDevelopInfo);
                isoHistoryInfo.setPeriod(enterpriseBasicInfo.getEnterpriseCurrentPeriod());

                // 记录截止到当前周期，各个企业认证完成的iso
                isoHistoryRepository.save(isoHistoryInfo);
            }
        }

        log.info("iso模块-比赛期间历史数据记录成功");

        return true;
    }

    @Override
    public boolean modelAdvance(Long gameId) {

        log.info("开始进行iso模块比赛期间周期推进");

        // 获取该比赛中全部进行中企业
        List<EnterpriseBasicInfo> enterpriseBasicInfoList = enterpriseBasicInfoRepository.findByGameBasicInfo_IdAndEnterpriseStatus(gameId, EnterpriseStatusEnum.PLAYING);

        for (EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfoList) {

            // 获取该企业中全部认证完成的iso（一定是先认证完成再认证中，顺序不可颠倒）
            List<IsoDevelopInfo> isoDevelopInfoList = isoDevelopInfoRepository.findByEnterpriseBasicInfo_IdAndIsoStatus(enterpriseBasicInfo.getId(), IsoStatusEnum.DEVELOPED);

            for (IsoDevelopInfo isoDevelopInfo : isoDevelopInfoList) {

                // 扣除认证完成后需要支付的维护费用
                Long enterpriseId = enterpriseBasicInfo.getId();
                String changeOperating = FinanceOperationConstant.ISO_MAINTAIN;
                Double changeAmount = isoDevelopInfo.getIsoBasicInfo().getIsoMaintainCost();
                financeService.updateFinanceInfo(enterpriseId, changeOperating, changeAmount, true);

            }

            // 获取该企业中全部认证中的iso
            isoDevelopInfoList = isoDevelopInfoRepository.findByEnterpriseBasicInfo_IdAndIsoStatus(enterpriseBasicInfo.getId(), IsoStatusEnum.DEVELOPING);

            for (IsoDevelopInfo isoDevelopInfo : isoDevelopInfoList) {

                // 已经研发的周期数+1
                isoDevelopInfo.setResearchedPeriod(isoDevelopInfo.getResearchedPeriod()+1);

                // 如果已经认证的周期数达到了该iso认证所需的周期数
                if(isoDevelopInfo.getResearchedPeriod() == isoDevelopInfo.getIsoBasicInfo().getIsoResearchPeriod()) {
                    // 设置为已认证
                    isoDevelopInfo.setIsoStatus(IsoStatusEnum.DEVELOPED);
                }

                // 保存修改
                isoDevelopInfoRepository.save(isoDevelopInfo);

                // 扣除认证过程中需要支付的费用
                Long enterpriseId = enterpriseBasicInfo.getId();
                String changeOperating = FinanceOperationConstant.ISO_DEVELOP;
                Double changeAmount = isoDevelopInfo.getIsoBasicInfo().getIsoResearchCost();
                financeService.updateFinanceInfo(enterpriseId, changeOperating, changeAmount, true);
            }
        }

        log.info("iso模块-比赛期间周期推进正常");

        // 运行到这里说明周期推进正常
        return true;
    }
}
