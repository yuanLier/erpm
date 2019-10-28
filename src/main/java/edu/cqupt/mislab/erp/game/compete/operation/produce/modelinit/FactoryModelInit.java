package edu.cqupt.mislab.erp.game.compete.operation.produce.modelinit;

import edu.cqupt.mislab.erp.commons.basic.modelinit.ModelInit;
import edu.cqupt.mislab.erp.commons.basic.modelinit.ModelInitService;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-26 15:12
 * @description 厂房模块 基本信息初始化
 */
@Slf4j
@Service
public class FactoryModelInit implements ModelInit {

    @Autowired
    private ModelInitService modelInitService;
    @Autowired
    private FactoryBasicInfoRepository factoryBasicInfoRepository;


    @Override
    public List<String> applicationModelInit() {
        //判断当前模块是否已经初始化完毕
        if(modelInitService.addInitializedModelIfNotExist(this)){

            try{
                log.info("开始初始化应用的厂房基本信息");

                //初始化材料信息
                initFactoryInfo();

                log.info("应用的厂房基本信息初始化完成");

                return null;
            }catch(Exception e){
                e.printStackTrace();
            }

            return Collections.singletonList("厂房模块基本数据信息初始化失败");
        }

        return null;
    }

    private void initFactoryInfo() {
        factoryBasicInfoRepository.save(
                FactoryBasicInfo.builder()
                        .factoryType("大厂房")
                        .factoryMakePeriod(5)
                        .factoryMakeCost(5D)
                        .factoryDepreciation(1D)
                        .factoryValue(30D)
                        .factoryStumpCost(20D)
                        .factoryCapacity(6)
                        .factoryRentCost(4D)
                        .factoryDelayTime(2)
                        .factoryMaintainCost(1D)
                        .enable(true)
                        .build()
        );

        factoryBasicInfoRepository.save(
                FactoryBasicInfo.builder()
                        .factoryType("小厂房")
                        .factoryMakePeriod(3)
                        .factoryMakeCost(5D)
                        .factoryDepreciation(1D)
                        .factoryValue(20D)
                        .factoryStumpCost(10D)
                        .factoryCapacity(4)
                        .factoryRentCost(4D)
                        .factoryDelayTime(2)
                        .factoryMaintainCost(1D)
                        .enable(true)
                        .build()
        );
    }
}
