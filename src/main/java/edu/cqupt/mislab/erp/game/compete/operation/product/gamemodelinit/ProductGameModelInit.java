package edu.cqupt.mislab.erp.game.compete.operation.product.gamemodelinit;

import edu.cqupt.mislab.erp.game.compete.basic.GameModelInit;
import edu.cqupt.mislab.erp.game.compete.basic.impl.GameModelInitService;
import edu.cqupt.mislab.erp.game.compete.operation.material.gamemodelinit.MaterialGameModelInit;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo.ProductDevelopInfoBuilder;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatusEnum;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author： chuyunfei
 * @date：2019/3/1
 */
@Slf4j
@Component
public class ProductGameModelInit implements GameModelInit {

    @Autowired private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired private ProductBasicInfoRepository productBasicInfoRepository;
    @Autowired private ProductDevelopInfoRepository productDevelopInfoRepository;

    @Autowired private GameModelInitService gameModelInitService;
    @Autowired private MaterialGameModelInit materialGameModelInit;

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/2 17:37
     * @Description: 初始化Product模块的比赛信息
     **/
    @Override
    public List<String> initGameModel(Long gameId){

        //判断是否已经被初始化过了
        if(gameModelInitService.addInitializedModelIfNotExist(gameId,this)){
            
            //产品初始化的先决条件
            List<String> preCheckWarns = preProductGameModel(gameId);
            
            if(preCheckWarns != null){
                
                return preCheckWarns;
            }

            try{
                log.info("开始初始化产品模块的比赛数据");

                //选取所有的产品基本数据信息
                final List<ProductBasicInfo> productBasicInfos = productBasicInfoRepository.findNewestProductBasicInfos();

                //选取全部的企业
                final List<EnterpriseBasicInfo> enterpriseBasicInfos = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId);

                //初始化所有企业的产品基本数据信息
                for(EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfos){

                    for(ProductBasicInfo productBasicInfo : productBasicInfos){

                        final ProductDevelopInfoBuilder builder = ProductDevelopInfo.builder();

                        builder.enterpriseBasicInfo(enterpriseBasicInfo)
                                .productBasicInfo(productBasicInfo)
                                .productDevelopStatus(productBasicInfo.getProductDevelopStatus());

                        //特殊状态处理
                        if(productBasicInfo.getProductDevelopStatus() == ProductDevelopStatusEnum.DEVELOPED){
                            builder.beginPeriod(1)
                                    .developedPeriod(0)
                                    .endPeriod(1);
                        }
                        
                        productDevelopInfoRepository.save(builder.build());
                    }
                }

                log.info("初始化产品模块的比赛数据完成");
                
                return null;
            }catch(Exception e){
                e.printStackTrace();
            }
            return Collections.singletonList("产品比赛模块数据初始化出错，无法初始化比赛");
        }

        return null;
    }

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/3 16:21
     * @Description: 产品初始化的先决条件是材料初始化
     **/
    private List<String> preProductGameModel(Long gameId){

        return materialGameModelInit.initGameModel(gameId);
    }
}
