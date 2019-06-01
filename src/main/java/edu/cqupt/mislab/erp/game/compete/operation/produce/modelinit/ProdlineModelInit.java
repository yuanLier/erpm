package edu.cqupt.mislab.erp.game.compete.operation.produce.modelinit;

import edu.cqupt.mislab.erp.commons.basic.modelinit.ModelInit;
import edu.cqupt.mislab.erp.commons.basic.modelinit.ModelInitService;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-26 15:45
 * @description
 */
@Slf4j
@Component
public class ProdlineModelInit implements ModelInit {

    @Autowired
    private ModelInitService modelInitService;
    @Autowired
    private ProdlineBasicInfoRepository prodlineBasicInfoRepository;

    @Override
    public List<String> applicationModelInit() {
        //判断当前模块是否已经初始化完毕
        if(modelInitService.addInitializedModelIfNotExist(this)){

            try{
                log.info("开始初始化应用的生产线基本信息");

                //初始化材料信息
                initProdlineInfo();

                log.info("应用的生产线基本信息初始化完成");

                return null;
            }catch(Exception e){
                e.printStackTrace();
            }

            return Collections.singletonList("生产线模块基本数据信息初始化失败");
        }

        return null;
    }

    private void initProdlineInfo() {
        prodlineBasicInfoRepository.save(
                ProdlineBasicInfo.builder()
                        .prodlineType("手工线")
                        .prodlineSetupPeriodPrice(2D)
                        .prodlineSetupPeriod(4)
                        .prodlineChangePeriod(2)
                        .prodlineChangeCost(2D)
                        .prodlineMainCost(0.5D)
                        .prodlineStumpcost(5D)
                        .prodlineDepreciation(0.2D)
                        .prodlineValue(2D)
                        .extraValue(0.2D)
                        .extraPeriod(0.2D)
                        .enable(true)
                        .build()
        );

        prodlineBasicInfoRepository.save(
                ProdlineBasicInfo.builder()
                        .prodlineType("半自动线")
                        .prodlineSetupPeriodPrice(3D)
                        .prodlineSetupPeriod(5)
                        .prodlineChangePeriod(2)
                        .prodlineChangeCost(2D)
                        .prodlineMainCost(1D)
                        .prodlineStumpcost(5D)
                        .prodlineDepreciation(0.5D)
                        .prodlineValue(3D)
                        .extraValue(0.5D)
                        .extraPeriod(0.5D)
                        .enable(true)
                        .build()
        );

        prodlineBasicInfoRepository.save(
                ProdlineBasicInfo.builder()
                        .prodlineType("自动线")
                        .prodlineSetupPeriodPrice(4D)
                        .prodlineSetupPeriod(5)
                        .prodlineChangePeriod(2)
                        .prodlineChangeCost(2D)
                        .prodlineMainCost(1.5D)
                        .prodlineStumpcost(5D)
                        .prodlineDepreciation(1D)
                        .prodlineValue(4D)
                        .extraValue(1D)
                        .extraPeriod(1D)
                        .enable(true)
                        .build()
        );
    }
}
