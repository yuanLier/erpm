package edu.cqupt.mislab.erp.game.compete.operation.product.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductMaterialBasicInfo;

import java.util.List;

/**
 * @author chuyunfei
 * @description
 **/

public interface ProductMaterialBasicInfoRepository extends BasicRepository<ProductMaterialBasicInfo,Long> {

    /**
     * 选取某个产品的全部启用的材料组成信息
     * @param productId
     * @return
     */
    List<ProductMaterialBasicInfo> findByEnableIsTrueAndProductBasicInfo_Id(long productId);
}
