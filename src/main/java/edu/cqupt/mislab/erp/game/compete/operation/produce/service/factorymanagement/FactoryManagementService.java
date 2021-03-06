package edu.cqupt.mislab.erp.game.compete.operation.produce.service.factorymanagement;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.*;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineDevelopStatus;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineDevelopDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineDevelopVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-23 17:24
 * @description
 */
public interface FactoryManagementService {

    /**
     * 获取全部可新建的生产线（全部指的是当前设定下所有类型生产线），其中ProdlineTypeVo的id值同ProdlineBasicInfo
     * @param gameId
     * @return
     */
    List<ProdlineDevelopVo> getAllProdlineDevelopVosByType(Long gameId);


    /**
     * 新建生产线
     * @param prodlineBasicId
     * @param productId
     * @param factoryId
     * @param enterpriseId
     * @return
     */
    ProdlineDevelopDisplayVo buildProdlineOfHoldingFactory(Long prodlineBasicId, Long productId, Long factoryId, Long enterpriseId);


    /**
     * 更新生产线安装状态，主要用于暂停安装和继续安装
     * @param prodlineDevelopId
     * @param prodlineDevelopStatus
     * @return
     */
    ProdlineDevelopDisplayVo updateProdlineDevelopStatus(Long prodlineDevelopId, ProdlineDevelopStatus prodlineDevelopStatus);


    /**
     * 查看厂房详情
     * @param factoryId
     * @return
     */
    FactoryDetailVo getFactoryDetailVo(Long factoryId);


    /**
     * 获取全部可新建的厂房（全部指的是当前设定下所有类型厂房），其中FactoryTypeVo的id值同FactoryBasicInfo
     * @param gameId
     * @return
     */
    List<FactoryDevelopVo> getAllFactoryDevelopVosByType(Long gameId);


    /**
     * 新建厂房
     * @param enterpriseId
     * @param factoryBasicId
     * @return
     */
    FactoryDevelopDisplayVo buildFactoryOfEnterprise(Long enterpriseId, Long factoryBasicId);


    /**
     * 更新厂房修建状态，主要用于暂停修建和恢复修建（enable为厂房的建造状态，true为建造中，false为暂停建造）
     * @param factoryDevelopId
     * @param enable
     * @return
     */
    FactoryDevelopDisplayVo updateFactoryDevelopStatus(Long factoryDevelopId, boolean enable);


    /**
     * 获取一个企业全部修建中的厂房情况
     * @param enterpriseId
     * @return
     */
    List<FactoryDevelopDisplayVo> getAllFactoryDevelopVos(Long enterpriseId);


    /**
     * 查看修建中的厂房详情
     * @param factoryDevelopId
     * @return
     */
    FactoryDevelopDetailVo getFactoryDevelopDetailVo(Long factoryDevelopId);


    /**
     * 厂房出售
     * @param factoryId
     * @return
     */
    WebResponseVo factorySell(Long factoryId);


    /**
     * 生产线出售（一次性收款，用prodlineProductId是因为只有修建完成的才能出售，而修建完成后即转入生产状态）
     * @param prodlineProductId
     * @return
     */
    WebResponseVo prodlineSell(Long prodlineProductId);


    /**
     * 获取全部能租赁的厂房类型（全部指的是当前设定下所有厂房类型），其中FactoryLeaseVo的id值同FactoryBasicInfo
     * @return
     */
    List<FactoryLeaseVo> getAllFactoryLeaseVosByType(Long gameId);


    /**
     * 租厂房
     * @param factoryBasicId
     * @param enterpriseId
     * @return
     */
    FactoryDisplayVo factoryLease(Long factoryBasicId, Long enterpriseId);

    /**
     * 厂房停租
     * @param factoryId
     * @return
     */
    FactoryDisplayVo leasePause(Long factoryId);


    /**
     * 厂房续租
     * @param factoryId
     * @return
     */
    FactoryDisplayVo leaseContinue(Long factoryId);



}
