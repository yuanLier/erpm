package edu.cqupt.mislab.erp.game.compete.operation.produce.service.productionplan;

import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryProdlineTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineProduceStatus;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineDetailVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProductProduceVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductTypeVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-22 17:21
 * @description 生产计划service
 */
public interface ProductionPlanService {

    /**
     * 获取一个企业可生产的产品名称
     * @param enterpriseId
     * @return
     */
    List<ProductTypeVo> getProducableProduct(Long enterpriseId);


    /**
     * 获取能生产某种产品的生产线及生产线所在的厂房
     * @param enterpriseId
     * @param productId
     * @return
     */
    List<FactoryProdlineTypeVo> getProducableFactoryAndProdline(Long enterpriseId, Long productId);


    /**
     * 获取企业中某一产品的全部生产情况
     * @param enterpriseId
     * @param productId
     * @return
     */
    List<ProductProduceVo> getProductProduceVosOfOneProduct(Long enterpriseId, Long productId);


    /**
     * 查看产品的某一生产情况，即根据生产线生产id获取厂房及厂房中全部生产线信息（生产线包括生产中的和修建中的）
     * @param prodlineProduceId
     * @return
     */
    FactoryDisplayVo getFactoryDisplayVo(Long prodlineProduceId);


    /**
     * 获取一个企业全部的生产信息（即所有厂房及厂房内生产线），厂房包括自建的和租来的
     * @param enterpriseId
     * @return
     */
    List<FactoryDisplayVo> getAllFactoryDisplayVos(Long enterpriseId);


    /**
     * 查看生产线详情（只有处于生产状态下的可以查看）
     * @param prodlineId
     * @return
     */
    ProdlineDetailVo getProdlineDetailVo(Long prodlineId);


    /**
     * 开始生产（初始化开始生产的周期数；修改生产状态至生产中
     * @param prodlineId
     * @return
     */
    ProductProduceVo productProduction(Long prodlineId);


    /**
     * 修改生产线生产状态，主要用于暂停/恢复生产
     * @param prodlineId
     * @param prodlineProduceStatus
     * @return
     */
    ProductProduceVo updateProduceStatus(Long prodlineId, ProdlineProduceStatus prodlineProduceStatus);

}
