package edu.cqupt.mislab.erp.game.compete.operation.material.gamemodelinit;

import edu.cqupt.mislab.erp.game.compete.basic.GameModelInit;
import edu.cqupt.mislab.erp.game.compete.basic.impl.GameModelInitService;
import edu.cqupt.mislab.erp.game.compete.operation.material.dao.GameMaterialInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.material.dao.MaterialBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.GameMaterialBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.MaterialBasicInfo;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
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

            try{
                log.info("开始初始化材料模块的比赛数据");

                //选取所有的基本材料信息
                final List<MaterialBasicInfo> materialBasicInfos = materialBasicInfoRepository.findNewestMaterialBasicInfos();

                //选取该场比赛对象
                final GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);

                //初始化比赛的材料信息
                for(MaterialBasicInfo materialBasicInfo : materialBasicInfos){

                    gameMaterialInfoRepository.save(
                            GameMaterialBasicInfo.builder()
                                    .materialBasicInfo(materialBasicInfo)
                                    .gameBasicInfo(gameBasicInfo)
                                    .build()
                    );
                }

                return null;
            }catch(Exception e){
                e.printStackTrace();
            }
            return Collections.singletonList("材料比赛模块数据初始化出错，无法初始化比赛");
        }

        return null;
    }
}
