package edu.cqupt.mislab.erp.game.compete.operation.stock.gamemodelinit;

import edu.cqupt.mislab.erp.game.compete.basic.GameModelInit;
import edu.cqupt.mislab.erp.game.compete.basic.impl.GameModelInitService;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.MaterialBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductMaterialBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.gamemodelinit.ProductGameModelInit;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatus;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductMaterialBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.dao.MaterialStockInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.dao.ProductStockInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.MaterialStockInfo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.ProductStockInfo;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author： chuyunfei date：2019/3/1
 */
@Slf4j
@Component
public class StockGameModelInit implements GameModelInit {

    @Autowired private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired private ProductDevelopInfoRepository productDevelopInfoRepository;
    @Autowired private MaterialStockInfoRepository materialStockInfoRepository;
    @Autowired private ProductStockInfoRepository productStockInfoRepository;
    @Autowired private GameModelInitService gameModelInitService;
    @Autowired private ProductMaterialBasicInfoRepository productMaterialBasicInfoRepository;

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

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/5 11:59
     * @Description: 为每一个企业初始化产品库存
     **/
    private void initStockInfos(Long gameId){

        //选取所有的企业
        final List<EnterpriseBasicInfo> enterpriseBasicInfos = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId);

        //选取企业已经研发的产品
        final Long enterpriseId = enterpriseBasicInfos.get(0).getId();

        //选取企业默认为研发成功的产品信息
        final List<ProductDevelopInfo> developInfos = productDevelopInfoRepository.findByEnterpriseBasicInfo_IdAndProductDevelopStatus(enterpriseId,ProductDevelopStatus.DEVELOPED);

        if(developInfos == null || developInfos.size() == 0){
            return;
        }

        //随机生成个数
        int productNumber = (int) Math.ceil(Math.random() * 2) + 1;

        //为每一个企业附加产品库存、材料库存信息
        for(EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfos){
            for(ProductDevelopInfo developInfo : developInfos){

                //生成随机库存
                productStockInfoRepository.save(
                        ProductStockInfo.builder()
                                .enterpriseBasicInfo(enterpriseBasicInfo)
                                .productBasicInfo(developInfo.getProductBasicInfo())
                                .productNumber(productNumber)
                                .period(1)
                                .build()
                );

            }
        }

        //提取需要的材料信息并进行库存初始化
        List<MaterialBasicInfo> materialBasicInfos = developInfos.stream()
                .map(ProductDevelopInfo::getProductBasicInfo)
                .map(productBasicInfo -> {
                    //获取该产品的材料组成信息
                    return productMaterialBasicInfoRepository.findByEnableIsTrueAndProductBasicInfo_Id(productBasicInfo.getId());
                })
                .flatMap(Collection::stream)
                .map(ProductMaterialBasicInfo::getMaterialBasicInfo)
                .distinct()
                .collect(Collectors.toList());

        //生成材料的数量
        int materialNumber = (int) Math.ceil(Math.random() * 5) + 1;

        //为企业生成原料库存原始数据
        for(EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfos){
            for(MaterialBasicInfo materialBasicInfo : materialBasicInfos){

                materialStockInfoRepository.save(
                        MaterialStockInfo.builder()
                        .enterpriseBasicInfo(enterpriseBasicInfo)
                        .materialBasicInfo(materialBasicInfo)
                        .materialNumber(materialNumber)
                        .period(1)
                        .build()
                );
            }
        }
    }

    @Autowired private ProductGameModelInit productGameModelInit;

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/5 11:57
     * @Description: 库存初始化前需要产品模块被初始化
     **/
    private List<String> preStockInit(Long gameId){
        return productGameModelInit.initGameModel(gameId);
    }
}