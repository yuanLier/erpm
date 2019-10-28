package edu.cqupt.mislab.erp.game.manage.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameInitBasicInfo;

/**
 * @author chuyunfei
 * @description 
 * @date 21:05 2019/4/26
 **/

public interface GameInitInfoRepository extends BasicRepository<GameInitBasicInfo, Long> {

    /**
     * @author chuyunfei
     * @description 获取启用的数据，业务保障这条数据必须存在且只有一条，必须要保证
     * @date 21:04 2019/4/26
     **/
    GameInitBasicInfo findBySettingEnableIsTrue();
}