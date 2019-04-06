package edu.cqupt.mislab.erp.game.compete.operation.stock.service;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.dto.MaterialOrderDto;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.TransportStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo.*;

import java.util.List;

/**
 * @author yuanyiwen
 * @description 采购与仓库管理service
 * @date 19:25 2019/4/1
 **/
public interface StockService {

    /**
     * @author yuanyiwen
     * @description 展示某一企业的原材料库存信息
     * @date 21:27 2019/4/1
     **/
    List<MaterialStockDisplayVo> getMaterialStockVosOfEnterprise(Long enterpriseId);


    /**
     * @author yuanyiwen
     * @description 展示当前设定下的全部可用的运输方式
     * @date 21:31 2019/4/1
     **/
    List<TransportMethodDisplayVo> getAllTransportVos();


    /**
     * @author yuanyiwen
     * @description 根据id获取某种运输方式详情（我想的是让订单详情里面那个运输方式变为可点击的
     * @date 21:33 2019/4/1
     **/
    TransportMethodDisplayVo getTransportVoyId(Long transportBasicId);


    /**
     * @author yuanyiwen
     * @description 提交订单（在用户选择完运输方式之后再调用
     * @date 21:42 2019/4/1
     **/
    List<MaterialOrderDisplayVo> submitMaterialOrder(List<MaterialOrderDto> materialOrderDtoList);

    
    /**
     * @author yuanyiwen
     * @description 获取一个企业的全部订单情况
     * @date 21:21 2019/4/6
     **/
    List<MaterialOrderDisplayVo> getAllMaterialOrdersOfEnterprise(Long enterpriseId);


    /**
     * @author yuanyiwen
     * @description 更新材料运送状态
     * @date 21:58 2019/4/1
     **/
    MaterialOrderDisplayVo updateTransportStatus(Long materialOrderId, TransportStatusEnum transportStatusEnum);


    /**
     * @author yuanyiwen
     * @description 根据材料采购订单id获取原料入库的具体信息（也就是在MaterialOrderDisplayVo的基础上多了个总额和库存数
     * @date 21:45 2019/4/1
     **/
    MaterialOrderDetailVo getMaterialOrderDetailVos(Long materialOrderId);


    /**
     * @author yuanyiwen
     * @description 展示某一企业的产品库存信息
     * @date 22:02 2019/4/1
     **/
    List<ProductStockDisplayVo> getProductStockVosOfEnterprise(Long enterpriseId);


    /**
     * @author yuanyiwen
     * @description 原材料售卖（由于某种不可抗力必须直接返回状态码
     * @date 22:05 2019/4/1
     **/
    WebResponseVo<String> sellMaterial(Long materialStockId, Integer sellNumber);


    /**
     * @author yuanyiwen
     * @description 产品售卖（同上
     * @date 22:04 2019/4/1
     **/
    WebResponseVo<String> sellProduct(Long productStockId, Integer sellNumber);
}
