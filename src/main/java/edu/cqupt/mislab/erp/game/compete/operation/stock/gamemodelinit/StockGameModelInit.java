package edu.cqupt.mislab.erp.game.compete.operation.stock.gamemodelinit;

import edu.cqupt.mislab.erp.game.compete.basic.GameModelInit;
import edu.cqupt.mislab.erp.game.compete.basic.impl.GameModelInitService;
import edu.cqupt.mislab.erp.game.compete.operation.material.dao.MaterialBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.MaterialBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.gamemodelinit.ProductGameModelInit;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.dao.MaterialStockInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.dao.ProductStockInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.MaterialStockInfo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.ProductStockInfo;
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
public class StockGameModelInit implements GameModelInit {

    @Autowired private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired private MaterialStockInfoRepository materialStockInfoRepository;
    @Autowired private MaterialBasicInfoRepository materialBasicInfoRepository;
    @Autowired private ProductStockInfoRepository productStockInfoRepository;
    @Autowired private ProductBasicInfoRepository productBasicInfoRepository;
    @Autowired private GameModelInitService gameModelInitService;

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/2 17:37
     * @Description: 初始化Stock模块的比赛信息
     **/
    @Override
    public List<String> initGameModel(Long gameId){

        //判断当前模块是否被初始化
        if(gameModelInitService.addInitializedModelIfNotExist(gameId,this)){

            //初始化库存的先决条件
            List<String> warns = preStockInit(gameId);

            if(warns != null){

                return warns;
            }

            try{

                log.info("开始比赛库存模块数据初始化");

                //初始化存储信息
                initStockInfos(gameId);

                log.info("完成比赛库存模块数据初始化");

                return null;
            }catch(Exception e){
                e.printStackTrace();
            }

            return Collections.singletonList("比赛库存模块数据初始化失败");
        }

        return null;
    }

    /**
     * @author yuanyiwen
     * @description 为每个企业初始化产品及原材料的库存信息
     * @date 12:45 2019/4/4
     **/
    private void initStockInfos(Long gameId){

        // 选取所有的企业
        final List<EnterpriseBasicInfo> enterpriseBasicInfos = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId);

        // 选取当前设定下的全部产品信息
        final List<ProductBasicInfo> productBasicInfoList = productBasicInfoRepository.findNewestProductBasicInfos();

        // 选取当前设定下的全部原材料信息
        final List<MaterialBasicInfo> materialBasicInfoList = materialBasicInfoRepository.findNewestMaterialBasicInfos();

        if(productBasicInfoList.size() == 0 || materialBasicInfoList.size() == 0){
            return;
        }

        // 产品库存初始化
        for(EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfos){
            for(ProductBasicInfo productBasicInfo : productBasicInfoList){

                productStockInfoRepository.save(
                        ProductStockInfo.builder()
                                .enterpriseBasicInfo(enterpriseBasicInfo)
                                .productBasicInfo(productBasicInfo)
                                .productNumber(0)
                                .period(1)
                                .build()
                );
            }
        }

        // 原材料库存初始化
        for(EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfos){
            for(MaterialBasicInfo materialBasicInfo : materialBasicInfoList){

                materialStockInfoRepository.save(
                        MaterialStockInfo.builder()
                        .enterpriseBasicInfo(enterpriseBasicInfo)
                        .materialBasicInfo(materialBasicInfo)
                        .materialNumber(0)
                        .build()
                );
            }
        }
    }

    @Autowired private ProductGameModelInit productGameModelInit;

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/5 11:57
     * @Description: 库存初始化前需要产品模块被初始化
     **/
    private List<String> preStockInit(Long gameId){
        return productGameModelInit.initGameModel(gameId);
    }
}