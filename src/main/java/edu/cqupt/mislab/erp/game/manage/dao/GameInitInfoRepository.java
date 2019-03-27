package edu.cqupt.mislab.erp.game.manage.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameInitBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameInitInfoRepository extends BasicRepository<GameInitBasicInfo, Long> {

    //获取启用的数据，业务保障这条数据必须存在且只有一条，必须要保证
    GameInitBasicInfo findBySettingEnableIsTrue();
}