package edu.cqupt.mislab.erp.game.compete.operation.material.modelinit;

import edu.cqupt.mislab.erp.commons.basic.ModelInit;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.material.dao.MaterialBasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.MaterialBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class MaterialModelInit implements ModelInit {

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/2 21:18
     * @Description: 初始化材料的基本信息，在应用第一次启动的时候调用
     **/

    @Autowired
    private MaterialBasicRepository materialBasicRepository;

    @Override
    public boolean init(){

        log.info("开始初始化应用材料信息");

        initMaterialInfo();

        log.info("初始化材料信息完成");

        return true;
    }

    //系统初始化材料信息
    private void initMaterialInfo(){

        materialBasicRepository.save(
                MaterialBasicInfo.builder()
                        .materialName("M1")
                        .materialPrice(1D)
                        .materialDelayTime(2)
                        .timeStamp(new Date())
                        .build()
        );
        materialBasicRepository.save(
                MaterialBasicInfo.builder()
                        .materialName("M2")
                        .materialPrice(1D)
                        .materialDelayTime(2)
                        .timeStamp(new Date())
                        .build()
        );
        materialBasicRepository.save(
                MaterialBasicInfo.builder()
                        .materialName("M3")
                        .materialPrice(1D)
                        .materialDelayTime(2)
                        .timeStamp(new Date())
                        .build()
        );
        materialBasicRepository.save(
                MaterialBasicInfo.builder()
                        .materialName("M4")
                        .materialPrice(1D)
                        .materialDelayTime(2)
                        .timeStamp(new Date())
                        .build()
        );
    }
}
