package edu.cqupt.mislab.erp.game.manage.modelinit;

import edu.cqupt.mislab.erp.commons.basic.ModelInit;
import edu.cqupt.mislab.erp.game.manage.dao.GameInitInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameInitInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class GameManageModelInit implements ModelInit {

    @Autowired private GameInitInfoRepository gameInitInfoRepository;

    @Override
    public void init(){

        log.info("初始化比赛的基本初始化信息");
        initGameInitInfo();

    }

    /**
     * 初始化比赛的初始化信息
     */
    private void initGameInitInfo(){

        GameInitInfo gameInitInfo = GameInitInfo.builder()
                .maxEnterpriseNumber(20)
                .maxMemberNumber(6)
                .period(4)
                .totalYear(5)
                .timeStamp(new Date())
                .build();

        gameInitInfoRepository.save(gameInitInfo);
    }
}
