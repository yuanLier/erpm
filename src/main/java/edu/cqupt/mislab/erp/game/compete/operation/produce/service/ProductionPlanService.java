package edu.cqupt.mislab.erp.game.compete.operation.produce.service;

import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineTypeVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-09 15:36
 * @description 生产计划Service
 */
public interface ProductionPlanService {
    /**
     * @author yuanyiwen
     * @description 获取一个企业可生产（指研发完毕的）的产品名称
     * @date 15:40 2019/3/9
     **/
    List<ProductTypeVo> getProductTypeOfEnterprise(Long enterpriseId);

    /**
     * @author yuanyiwen
     * @description 获取一个企业可使用（指含有空闲生产线的）的厂房类型
     * @date 15:50 2019/3/9
     **/
    List<FactoryTypeVo> getFactoryTypeOfEnterprise(Long enterpriseId);

    /**
     * @author yuanyiwen
     * @description 获取某个厂房中可使用（指空闲状态的）的生产线类型
     * @date 15:50 2019/3/9
     **/
    List<ProdlineTypeVo> getProdlineTypeOfEnterprise(Long factoryId);





}
