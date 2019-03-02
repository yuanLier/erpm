package edu.cqupt.mislab.erp.game.compete.operation.material.gamemodelinit;

import edu.cqupt.mislab.erp.game.compete.basic.GameModelInit;
import edu.cqupt.mislab.erp.game.compete.basic.impl.GameModelInitService;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoDevelopInfo.IsoDevelopInfoBuilder;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.material.dao.GameMaterialInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.GameMaterialInfo;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.MaterialBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.MaterialBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * author： chuyunfei date：2019/3/1
 */
@Component
public class MaterialGameModelInit implements GameModelInit {

    @Autowired private GameBasicInfoRepository gameBasicInfoRepository;
    @Autowired private MaterialBasicInfoRepository materialBasicInfoRepository;
    @Autowired private GameMaterialInfoRepository gameMaterialInfoRepository;

    @Autowired private GameModelInitService gameModelInitService;

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/2 21:24
     * @Description: 初始化比赛的材料信息
     **/
    @Override
    public List<String> initGameModel(Long gameId){

        //判断是否已经被初始化过了
        if(gameModelInitService.addInitializedModelIfNotExist(gameId,this)){
            //如果没有被初始化过就进行初始化

            //选取所有的基本材料信息
            final List<MaterialBasicInfo> materialBasicInfos = materialBasicInfoRepository.findNewestMaterialBasicInfos();

            //如果没有材料信息就直接返回
            if(materialBasicInfos == null){

                return null;
            }

            //选取该场比赛对象
            final GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);

            //初始化比赛的材料信息
            for(MaterialBasicInfo materialBasicInfo : materialBasicInfos){

                gameMaterialInfoRepository.save(
                        GameMaterialInfo.builder()
                                .materialBasicInfo(materialBasicInfo)
                                .gameBasicInfo(gameBasicInfo)
                                .build()
                );
            }

        }

        return null;
    }
}
