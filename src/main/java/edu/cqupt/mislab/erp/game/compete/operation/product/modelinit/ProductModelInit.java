package edu.cqupt.mislab.erp.game.compete.operation.product.modelinit;

import edu.cqupt.mislab.erp.commons.basic.ModelInit;
import edu.cqupt.mislab.erp.game.compete.operation.material.gamemodelinit.MaterialGameModelInit;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.MaterialBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.material.modelinit.MaterialModelInit;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.MaterialBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo.ProductBasicInfoBuilder;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatus;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class ProductModelInit implements ModelInit {

    @Autowired
    private MaterialBasicInfoRepository materialBasicInfoRepository;
    @Autowired
    private ProductBasicInfoRepository productBasicInfoRepository;
    @Autowired
    private ProductDevelopInfoRepository productDevelopInfoRepository;
    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;

    @Override
    public boolean init(){

        log.info("产品初始化先决条件初始化");

        initPreInfo();

        log.info("初始化应用的产品基本信息");

        initProductBasicInfo();

        return true;
    }

    /**
     * 初始化产品的基本信息
     */
    private void initProductBasicInfo(){

        //肯定是有原料信息的
        final List<MaterialBasicInfo> materialBasicInfos = materialBasicInfoRepository.findNewestMaterialBasicInfos();

        final int size = materialBasicInfos.size();

        for(int i = 0; i < 4; i++){

            Map<MaterialBasicInfo,Integer> materialBasicInfoIntegerMap = new HashMap<>();

            Random random = new Random();

            for(int j = 0; j < size; j++){

                final MaterialBasicInfo materialBasicInfo = materialBasicInfos.get(j);

                materialBasicInfoIntegerMap.put(materialBasicInfo,random.nextInt(3) + j + 1);
            }

            final ProductBasicInfoBuilder builder = ProductBasicInfo.builder()
                    .productName("P" + (i + 1))
                    .productResearchPeriod(i + 1)
                    .productResearchCost(i+1.0D)
                    .price((i+1)*5.0D)
                    .mount((i+1)*random.nextInt(5))
                    .priceDifference((i+1)*1D)
                    .mountDifference((i+1)*random.nextInt(5))
                    .priceFloat((i+1)*1D)
                    .mountFloat((i+1)*1D)
                    .materialBasicInfoIntegerMap(materialBasicInfoIntegerMap)
                    .timeStamp(new Date());

            if(i % 2 == 0){

                builder.productDevelopStatus(ProductDevelopStatus.TODEVELOP);
            }else {

                builder.productDevelopStatus(ProductDevelopStatus.DEVELOPED);
            }

            productBasicInfoRepository.save(builder.build());
        }
    }

    @Autowired private MaterialModelInit materialModelInit;

    /**
     * 初始化产品模块依赖模块的信息
     */
    private void initPreInfo(){

        //初始化材料模块信息
        materialModelInit.init();
    }
}
