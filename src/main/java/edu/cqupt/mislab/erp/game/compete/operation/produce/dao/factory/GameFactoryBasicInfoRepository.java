package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.GameFactoryBasicInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-22 15:25
 * @description
 */
public interface GameFactoryBasicInfoRepository extends BasicRepository<GameFactoryBasicInfo, Long>, JpaSpecificationExecutor {

    /**
     * 获取当前比赛所使用的全部厂房信息
     * @param gameId
     * @return
     */
    List<GameFactoryBasicInfo> findByGameBasicInfo_Id(Long gameId);
}
