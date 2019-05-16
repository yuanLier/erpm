package edu.cqupt.mislab.erp.game.compete.operation.product.modelinit;

import edu.cqupt.mislab.erp.commons.basic.modelinit.ModelInit;
import edu.cqupt.mislab.erp.commons.basic.modelinit.ModelInitService;
import edu.cqupt.mislab.erp.game.compete.operation.material.dao.MaterialBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.MaterialBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.material.modelinit.MaterialModelInit;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductMaterialBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo.ProductBasicInfoBuilder;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductMaterialBasicInfo;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author chuyunfei
 * @description 
 **/

@Slf4j
@Component
public class ProductModelInit implements ModelInit {

    @Autowired private MaterialBasicInfoRepository materialBasicInfoRepository;
    @Autowired private ProductBasicInfoRepository productBasicInfoRepository;
    @Autowired private ProductMaterialBasicInfoRepository productMaterialBasicInfoRepository;
    @Autowired private ProductDevelopInfoRepository productDevelopInfoRepository;
    @Autowired private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired private ModelInitService modelInitService;

    @Override
    public List<String> applicationModelInit(){

        //判断当前模块是否已经被初始化过了
        if(modelInitService.addInitializedModelIfNotExist(this)){

            try{
                log.info("产品初始化先决条件初始化");

                final List<String> strings = initPreInfo();

                if(strings != null){

                    return strings;
                }

                log.info("开始初始化应用的产品基本信息");

                //初始化产品基本数据信息
                initProductBasicInfo();

                log.info("初始化应用的产品基本信息完成");

                return null;
            }catch(Exception e){
                e.printStackTrace();
            }

            return Collections.singletonList("初始化产品基本数据信息失败");
        }

        return null;
    }

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/4 22:14
     * @Description: 初始化产品的基本信息
     **/
    private void initProductBasicInfo(){

        //肯定是有原料信息的
        final List<MaterialBasicInfo> materialBasicInfos = materialBasicInfoRepository.findNewestMaterialBasicInfos();

        int size = materialBasicInfos.size();

        //随机开始初始化产品
        for(int i = 0; i < 4; i++){

            final ProductBasicInfoBuilder builder = ProductBasicInfo.builder()
                    .productName("P" + (i + 1))
                    .productResearchPeriod((int)Math.ceil(Math.random() * 5) + 1)
                    .productResearchCost(Math.ceil(Math.random() * 4) + 1)
                    .price(Math.ceil(Math.random() * 4) + 2)
                    .mount((int)Math.ceil(Math.random() * 50) + 40)
                    .priceDifference(Math.ceil(Math.random() * 10) + 2)
                    .mountDifference((int)Math.ceil(Math.random() * 20) + 10)
                    .priceFloat((Math.random() + Math.random()) / 2)
                    .mountFloat((Math.random() + Math.random()) / 2)
                    .enable(true);

            if(i % 2 == 0){

                builder.productDevelopStatus(ProductDevelopStatusEnum.TODEVELOP);
            }else {

                builder.productDevelopStatus(ProductDevelopStatusEnum.DEVELOPED);
            }

            productBasicInfoRepository.save(builder.build());
        }

        //随机初始化产品构成
        final List<ProductBasicInfo> productBasicInfos = productBasicInfoRepository.findAll();

        //获取全部产品的数量
        size = productBasicInfos.size();

        for(ProductBasicInfo productBasicInfo : productBasicInfos){

            //随机选择材料种类数据
            int number = (int) Math.ceil(Math.random() * size) ;

            for(int i = 0; i < number; i ++){

                final ProductMaterialBasicInfo productMaterialBasicInfo = ProductMaterialBasicInfo
                        .builder()
                        .productBasicInfo(productBasicInfo)
                        .materialBasicInfo(materialBasicInfos.get(i))
                        .number((int) Math.ceil(Math.random() * 3))//随机生成材料个数数据
                        .enable(true)
                        .build();

                productMaterialBasicInfoRepository.save(productMaterialBasicInfo);
            }
        }
    }

    @Autowired private MaterialModelInit materialModelInit;

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/3 15:28
     * @Description: 初始化产品模块依赖模块的信息
     **/
    private List<String> initPreInfo(){

        //初始化材料模块信息
        return materialModelInit.applicationModelInit();
    }
}
