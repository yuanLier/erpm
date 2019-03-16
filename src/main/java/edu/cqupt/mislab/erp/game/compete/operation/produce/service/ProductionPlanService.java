package edu.cqupt.mislab.erp.game.compete.operation.produce.service;

import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryProdlineTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineProduceStatus;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineDetailVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProductProduceVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineTypeVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-09 15:36
 * @description 生产计划Service，其中涉及的部分id关系如下 ：
 *                  prodlineId --> ProdlineProduceInfo_Id
 *                  factoryId --> FactoryDevelopInfo_Id
 *                  productId --> ProductDevelopInfo_Id
 */
public interface ProductionPlanService {

//    /**
//     * @author yuanyiwen
//     * @description 获取一个企业可生产（指研发完毕的）的产品名称
//     * @date 15:40 2019/3/9
//     **/
//    List<ProductTypeVo> getProductTypeOfEnterprise(Long enterpriseId);
//
//
//    /**
//     * @author yuanyiwen
//     * @description 获取一个企业可使用（指含有空闲生产线的）的厂房类型
//     * @date 15:50 2019/3/9
//     **/
//    List<FactoryTypeVo> getFactoryTypeOfEnterprise(Long enterpriseId);
//
//
//    /**
//     * @author yuanyiwen
//     * @description 获取某个厂房中可使用（指空闲状态的）的生产线类型
//     * @date 15:50 2019/3/9
//     **/
//    List<ProdlineTypeVo> getProdlineTypeOfEnterprise(Long factoryId);
//
//
//    /**
//     * @author yuanyiwen
//     * @description 获取某个厂房中可生产某产品的生产线类型
//     * @date 15:40 2019/3/12
//     **/
//    List<ProdlineTypeVo> getProdlineTypeOfProduction(Long factoryId, Long productionId);
//
//
//    /**
//     * @author yuanyiwen
//     * @description 根据厂房id获取厂房及厂房中全部生产线信息
//     * @date 18:12 2019/3/12
//     **/
//    FactoryDisplayVo getFactoryDisplayInfo(Long factoryId);
//
//
//    /**
//     * @author yuanyiwen
//     * @description 获取一个企业全部的生产信息（即所有厂房及厂房内生产线）
//     * @date 21:37 2019/3/12
//     **/
//    List<FactoryDisplayVo> getAllFactoryDisplayVos(Long enterpriseId);
//
//
//    /**
//     * @author yuanyiwen
//     * @description 查看生产线详情
//     * @date 21:32 2019/3/12
//     * @param prodlineId 注意这里接收的值是prodlineDisplayVo中的id，即值同prodlineProduceInfo_id
//     * @return 这里返回的Vo实体中的id是prodlineHoldingInfo_id todo 也许可以直接用一个id？逻辑上分开好一点 操作上合并也许好一点
//     **/
//    ProdlineDetailVo getProdlineDetailVo(Long prodlineId);
//
//
//    /**
//     * @author yuanyiwen
//     * @description 获取某个企业中某个产品的生产信息
//     * @date 21:59 2019/3/12
//     **/
//    List<ProductProduceVo> getProductProduceVo(Long enterpriseId, Long productId);


    /**
     * @author yuanyiwen
     * @description 获取一个企业可生产的产品名称
     * @date 23:56 2019/3/14
     **/
    List<ProductTypeVo> getProducableProduct(Long enterpriseId);


//    /**
//     * @author yuanyiwen
//     * @description 获取一个企业能生产某种产品的空闲生产线 todo 合并到getProducableFactoryOfOneProduct?
//     * @date 0:00 2019/3/15
//     **/
//    List<ProdlineTypeVo> getProducableProdline(Long enterprise, Long productId);


//    todo 合并到getProducableFactoryAndProdline？
//    /**
//     * @author yuanyiwen
//     * @description 获取一个企业所拥有的 包含 能生产某种产品的空闲生产线 的 厂房
//     * @date 0:02 2019/3/15
//     **/
//    List<FactoryTypeVo> getProducableFactoryOfOneProduct(Long enterprise, Long productId);
//
//
//    /**
//     * @author yuanyiwen
//     * @description 获取一个厂房中可生产某产品的生产线
//     * @date 0:06 2019/3/15
//     **/
//    List<ProdlineTypeVo> getProducableProdlineOfOneProduct(Long factoryId, Long productId);


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
     * @description 查看产品的某一生产情况，即根据厂房id获取厂房及厂房中全部生产线信息
     * @date 18:12 2019/3/12
     **/
    FactoryDisplayVo getFactoryDisplayVo(Long factoryId);


    /**
     * @author yuanyiwen
     * @description 获取一个企业全部的生产信息（即所有厂房及厂房内生产线）
     * @date 21:37 2019/3/12
     **/
    List<FactoryDisplayVo> getAllFactoryDisplayVos(Long enterpriseId);


    /**
     * @author yuanyiwen
     * @description 获取生产线详情
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
     * @description 暂停/恢复生产
     * @date 9:00 2019/3/15
     **/
    ProductProduceVo updateProduceStatus(Long prodlineId, ProdlineProduceStatus prodlineProduceStatus);

}
