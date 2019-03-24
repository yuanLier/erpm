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
     * @author yuanyiwen
     * @description 获取一个企业可生产的产品名称
     * @date 23:56 2019/3/14
     **/
    List<ProductTypeVo> getProducableProduct(Long enterpriseId);


    /**
     * @author yuanyiwen
     * @description 获取能生产某种产品的生产线及生产线所在的厂房
     * @date 9:09 2019/3/15
     **/
    List<FactoryProdlineTypeVo> getProducableFactoryAndProdline(Long enterpriseId, Long productId);


    /**
     * @author yuanyiwen
     * @description 获取企业中某一产品的全部生产情况
     * @date 0:11 2019/3/15
     **/
    List<ProductProduceVo> getProductProduceVosOfOneProduct(Long enterpriseId, Long productId);


    /**
     * @author yuanyiwen
     * @description 查看产品的某一生产情况，即根据生产线生产id获取厂房及厂房中全部生产线信息（生产线包括生产中的和修建中的）
     * @date 18:12 2019/3/12
     **/
    FactoryDisplayVo getFactoryDisplayVo(Long prodlineProduceId);


    /**
     * @author yuanyiwen
     * @description 获取一个企业全部的生产信息（即所有厂房及厂房内生产线），厂房包括自建的和租来的
     * @date 21:37 2019/3/12
     **/
    List<FactoryDisplayVo> getAllFactoryDisplayVos(Long enterpriseId);


    /**
     * @author yuanyiwen
     * @description 查看生产线详情（只有处于生产状态下的可以查看）
     * @date 9:13 2019/3/15
     **/
    ProdlineDetailVo getProdlineDetailVo(Long prodlineId);


    /**
     * @author yuanyiwen
     * @description 开始生产（初始化开始生产的周期数；修改生产状态至生产中
     * @date 0:08 2019/3/15
     **/
    ProductProduceVo productProduction(Long prodlineId);


    /**
     * @author yuanyiwen
     * @description 修改生产线生产状态，主要用于暂停/恢复生产
     * @date 9:00 2019/3/15
     **/
    ProductProduceVo updateProduceStatus(Long prodlineId, ProdlineProduceStatus prodlineProduceStatus);

}
