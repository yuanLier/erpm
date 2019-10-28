package edu.cqupt.mislab.erp.game.compete.operation.iso.modelinit;

import edu.cqupt.mislab.erp.commons.basic.modelinit.ModelInit;
import edu.cqupt.mislab.erp.commons.basic.modelinit.ModelInitService;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author chuyunfei
 * @description
 **/

@Slf4j
@Service
public class IsoModelInit implements ModelInit {

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/2 20:18
     * @Description: 用于初始化ISO模块的基本数据信息，在应用启动的时候会被调用
     **/

    @Autowired private IsoBasicInfoRepository isoBasicInfoRepository;

    @Autowired private ModelInitService modelInitService;

    @Override
    public List<String> applicationModelInit(){

        //判断当前模块是否已经被初始化了
        if(modelInitService.addInitializedModelIfNotExist(this)){

            try{
                log.info("开始初始化应用ISO认证信息");

                //初始化项目的ISO基本数据信息
                initIsoBasicInfo();

                log.info("初始化应用ISO认证信息完成");

                return null;
            }catch(Exception e){
                e.printStackTrace();
            }

            return Collections.singletonList("ISO模块应用基本数据初始化出现错误");
        }

        return null;
    }

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/4 21:02
     * @Description: 系统初始化三个ISO基本认证信息
     **/
    private void initIsoBasicInfo(){

        isoBasicInfoRepository.save(
                IsoBasicInfo.builder()
                        .isoName("ISO9000质量认证")
                        .isoResearchCost(1.5)
                        .isoResearchPeriod(4)
                        .isoMaintainCost(0.5)
                        .extraValue(1.5)
                        .isoStatus(IsoStatusEnum.DEVELOPED)
                        .enable(true)
                        .build());

        isoBasicInfoRepository.save(
                IsoBasicInfo.builder()
                        .isoName("ISO14000质量认证")
                        .isoResearchCost(2.0)
                        .isoResearchPeriod(5)
                        .isoMaintainCost(1.0)
                        .extraValue(2.0)
                        .isoStatus(IsoStatusEnum.TODEVELOP)
                        .enable(true)
                        .build());

        isoBasicInfoRepository.save(
                IsoBasicInfo.builder()
                        .isoName("ISO20000质量认证")
                        .isoResearchCost(2.5)
                        .isoResearchPeriod(6)
                        .isoMaintainCost(1.5)
                        .extraValue(2.5)
                        .isoStatus(IsoStatusEnum.TODEVELOP)
                        .enable(true)
                        .build());
    }
}
