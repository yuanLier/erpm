package edu.cqupt.mislab.erp.game.compete.operation.iso.advance;

import edu.cqupt.mislab.erp.game.compete.basic.ModelAdvance;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoStatusEnum;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-08 17:58
 * @description ISO模块周期推进
 */
public class IsoAdvance implements ModelAdvance {

    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired
    private IsoDevelopInfoRepository isoDevelopInfoRepository;

    @Override
    public boolean advance(Long gameId) {

        // 获取该比赛中全部进行中企业
        List<EnterpriseBasicInfo> enterpriseBasicInfoList = enterpriseBasicInfoRepository.findByGameBasicInfo_IdAndEnterpriseStatus(gameId, EnterpriseStatusEnum.PLAYING);

        for (EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfoList) {

            // 获取该企业中全部认证完成的iso（一定是先认证完成再认证中，顺序不可颠倒）
            List<IsoDevelopInfo> isoDevelopInfoList = isoDevelopInfoRepository.findByEnterpriseBasicInfo_IdAndIsoStatus(enterpriseBasicInfo.getId(), IsoStatusEnum.DEVELOPED);

            for (IsoDevelopInfo isoDevelopInfo : isoDevelopInfoList) {

                // todo 扣除认证完成后需要支付的维护费用（余额判断的部分可以抽出来）

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

                // todo 扣除认证过程中需要支付的费用

            }
        }

        // 运行到这里说明周期推进正常
        return true;
    }
}
