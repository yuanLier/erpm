package edu.cqupt.mislab.erp.game.compete.operation.product.dao;

import edu.cqupt.mislab.erp.commons.basic.repository.BasicRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductMaterialBasicInfo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author chuyunfei
 * @description
 **/

public interface ProductMaterialBasicInfoRepository extends BasicRepository<ProductMaterialBasicInfo,Long> {

    /**
     * @author yuanyiwen
     * @description 选取当前版本下的全部产品的材料构成信息
     * @date 20:47 2019/10/31
     **/
    @Query("from ProductMaterialBasicInfo pm where pm.enable = true")
    List<ProductMaterialBasicInfo> findNewestProductMaterialBasicInfos();


    /**
     * 选取某个产品的全部启用的材料组成信息
     * @param productId
     * @return
     */
    List<ProductMaterialBasicInfo> findByEnableIsTrueAndProductBasicInfo_Id(long productId);


    /**
     * @author yuanyiwen
     * @description 获取处于某种状态（可用or不可用）下的产品基本信息
     * @date 18:00 2019/5/18
     **/
    List<ProductMaterialBasicInfo> findByEnable(boolean enable);


    /**
     * 获取当前设定下由某种材料构成的所有产品
     * @param materialId
     * @return
     */
    List<ProductMaterialBasicInfo> findByMaterialBasicInfo_IdAndEnableIsTrue(Long materialId);

}
