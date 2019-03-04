package edu.cqupt.mislab.erp.game.compete.operation.material.modelinit;

import edu.cqupt.mislab.erp.commons.basic.ModelInit;
import edu.cqupt.mislab.erp.commons.basic.ModelInitService;
import edu.cqupt.mislab.erp.game.compete.operation.material.dao.MaterialBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.MaterialBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class MaterialModelInit implements ModelInit {

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/2 21:18
     * @Description: 初始化材料的基本信息，在应用第一次启动的时候调用
     **/

    @Autowired private MaterialBasicInfoRepository materialBasicRepository;
    @Autowired private ModelInitService modelInitService;

    @Override
    public List<String> applicationModelInit(){

        //判断当前模块是否已经初始化完毕
        if(modelInitService.addInitializedModelIfNotExist(this)){

            try{
                log.info("开始初始化应用材料信息");

                //初始化材料信息
                initMaterialInfo();

                log.info("初始化材料信息完成");

                return null;
            }catch(Exception e){
                e.printStackTrace();
            }

            return Collections.singletonList("材料模块初始化基本数据出错");
        }

        return null;
    }

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/4 21:50
     * @Description: 系统初始化材料信息
     **/
    private void initMaterialInfo(){

        materialBasicRepository.save(
                MaterialBasicInfo.builder()
                        .materialName("M1")
                        .materialPrice(1D)
                        .materialDelayTime(2)
                        .enable(true)
                        .build()
        );
        materialBasicRepository.save(
                MaterialBasicInfo.builder()
                        .materialName("M2")
                        .materialPrice(2D)
                        .materialDelayTime(2)
                        .enable(true)
                        .build()
        );
        materialBasicRepository.save(
                MaterialBasicInfo.builder()
                        .materialName("M3")
                        .materialPrice(3D)
                        .materialDelayTime(2)
                        .enable(true)
                        .build()
        );
        materialBasicRepository.save(
                MaterialBasicInfo.builder()
                        .materialName("M4")
                        .materialPrice(4D)
                        .materialDelayTime(2)
                        .enable(true)
                        .build()
        );
    }
}
