package edu.cqupt.mislab.erp.game.compete.operation.stock.modelinit;

import edu.cqupt.mislab.erp.commons.basic.modelinit.ModelInit;
import edu.cqupt.mislab.erp.commons.basic.modelinit.ModelInitService;
import edu.cqupt.mislab.erp.game.compete.operation.stock.dao.TransportBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.TransportBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-25 19:10
 * @description 主要用于在应用启动时初始化一下运输方式
 */
@Slf4j
@Component
public class StockModelInit implements ModelInit {

    @Autowired
    private TransportBasicInfoRepository transportBasicInfoRepository;

    @Autowired private ModelInitService modelInitService;

    @Override
    public List<String> applicationModelInit(){

        //判断当前模块是否已经被初始化了
        if(modelInitService.addInitializedModelIfNotExist(this)){

            try{
                log.info("开始初始化运输方式基本信息");

                //初始化项目的传输方式基本信息
                initTransportBasicInfo();

                log.info("初始化应用运输方式基本信息完成");

                return null;
            }catch(Exception e){
                e.printStackTrace();
            }

            return Collections.singletonList("运输方式基本信息初始化出现错误");
        }

        return null;
    }


    /**
     * 添加四个运输方式基本信息
     */
    private void initTransportBasicInfo(){

        transportBasicInfoRepository.save(
                TransportBasicInfo.builder()
                        .transportName("航运")
                        .transportPeriod(1)
                        .transportPrice(2.0)
                        .enable(true)
                        .build());

        transportBasicInfoRepository.save(
                TransportBasicInfo.builder()
                        .transportName("铁运")
                        .transportPeriod(2)
                        .transportPrice(1.5)
                        .enable(true)
                        .build());

        transportBasicInfoRepository.save(
                TransportBasicInfo.builder()
                        .transportName("水运")
                        .transportPeriod(3)
                        .transportPrice(1.0)
                        .enable(true)
                        .build());

        transportBasicInfoRepository.save(
                TransportBasicInfo.builder()
                        .transportName("陆运")
                        .transportPeriod(4)
                        .transportPrice(0.5)
                        .enable(true)
                        .build());
    }
}
