package edu.cqupt.mislab.erp.game.compete.operation.product.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.GameProductMaterialBasicInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-10-31 20:57
 * @description
 */
public interface GameProductMaterialBasicInfoRepository extends BasicRepository<GameProductMaterialBasicInfo, Long>, JpaSpecificationExecutor {

    /**
     * 获取当前比赛所使用的全部产品材料信息
     * @param gameId
     * @param productId
     * @return
     */
    List<GameProductMaterialBasicInfo> findByGameBasicInfo_IdAndProductMaterialBasicInfo_ProductBasicInfo_Id(Long gameId, Long productId);

}
