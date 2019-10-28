package edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.GameProdlineBasicInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-09 16:36
 * @description
 */
public interface GameProdlineBasicInfoRepository extends BasicRepository<GameProdlineBasicInfo, Long>, JpaSpecificationExecutor {

    /**
     * 获取当前比赛所使用的全部生产线信息
     * @param gameId
     * @return
     */
    List<GameProdlineBasicInfo> findByGameBasicInfo_Id(Long gameId);

}
