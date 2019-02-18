package edu.cqupt.mislab.erp.game.compete.operation.iso.modelinit;

import edu.cqupt.mislab.erp.commons.basic.ModelInit;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class IsoModelInit implements ModelInit {

    @Autowired
    private IsoBasicInfoRepository isoBasicInfoRepository;

    @Override
    public boolean init(){

        log.info("开始初始化应用认证信息");

        initIsoBasicInfo();

        log.info("初始化应用认证信息完成");

        return true;
    }

    //系统初始化认证信息
    private void initIsoBasicInfo(){

        isoBasicInfoRepository.save(
                IsoBasicInfo.builder()
                .isoName("ISO1400质量认证")
                .isoResearchCost(0.5)
                .isoResearchPeriod(4)
                .isoMaintainCost(0.2)
                .extraValue(0.2)
                .timeStamp(new Date()).build());

        isoBasicInfoRepository.save(
                IsoBasicInfo.builder()
                        .isoName("ISO9000质量认证")
                        .isoResearchCost(0.6)
                        .isoResearchPeriod(5)
                        .isoMaintainCost(0.4)
                        .extraValue(0.5)
                        .timeStamp(new Date()).build());
    }
}
