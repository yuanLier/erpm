package edu.cqupt.mislab.erp.game.compete.operation.produce.service.factorymanagement;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.*;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineDevelopStatus;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineDevelopDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineDevelopVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineTypeVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-03-23 17:24
 * @description
 */
public interface FactoryManagementService {

    /**
     * @author yuanyiwen
     * @description 获取全部可新建的生产线（全部指的是当前设定下所有类型生产线），注意这里ProdlineTypeVo的id值同ProdlineBasicInfo
     * @date 17:36 2019/3/17
     **/
    List<ProdlineDevelopVo> getAllProdlineDevelopVos();


    /**
     * @author yuanyiwen
     * @description 新建生产线
     * @date 17:21 2019/3/17
     **/
    ProdlineDevelopDisplayVo buildProdlineOfHoldingFactory(Long prodlineBasicId, Long productId, Long factoryId, Long enterpriseId);


    /**
     * @author yuanyiwen
     * @description 更新生产线安装状态，主要用于暂停安装和继续安装
     * @date 17:41 2019/3/17
     **/
    ProdlineDevelopDisplayVo updateProdlineDevelopStatus(Long prodlineDevelopId, ProdlineDevelopStatus prodlineDevelopStatus);


    /**
     * @author yuanyiwen
     * @description 查看厂房详情
     * @date 20:38 2019/3/17
     **/
    FactoryDetailVo getFactoryDetailVo(Long factoryId);


    /**
     * @author yuanyiwen
     * @description 获取全部可新建的厂房（全部指的是当前设定下所有类型厂房），注意这里FactoryTypeVo的id值同FactoryBasicInfo
     * @date 21:05 2019/3/17
     **/
    List<FactoryDevelopVo> getAllFactoryDevelopVos();


    /**
     * @author yuanyiwen
     * @description 新建厂房
     * @date 21:01 2019/3/17
     **/
    FactoryDevelopDisplayVo buildFactoryOfEnterprise(Long enterpriseId, Long factoryBasicId);


    /**
     * @author yuanyiwen
     * @description 更新厂房修建状态，主要用于暂停修建和恢复修建（enable为厂房的建造状态，true为建造中，false为暂停建造）
     * @date 21:22 2019/3/17
     **/
    FactoryDevelopDisplayVo updateFactoryDevelopStatus(Long factoryDevelopId, boolean enable);


    /**
     * @author yuanyiwen
     * @description 获取一个企业全部修建中的厂房情况
     * @date 11:48 2019/4/7
     **/
    List<FactoryDevelopDisplayVo> getAllFactoryDevelopVos(Long enterpriseId);


    /**
     * @author yuanyiwen
     * @description 查看修建中的厂房详情
     * @date 21:35 2019/3/17
     **/
    FactoryDevelopDetailVo getFactoryDevelopDetailVo(Long factoryDevelopId);


    /**
     * @author yuanyiwen
     * @description 厂房出售 --> 返回String是因为感觉没啥好展示的但可能遇到的错误信息好像不少(Ｔ▽Ｔ)
     * @date 21:31 2019/3/18
     **/
    WebResponseVo<String> factorySell(Long factoryId);


    /**
     * @author yuanyiwen
     * @description 生产线出售（一次性收款，用prodlineProductId是因为只有修建完成的才能出售，而修建完成后即转入生产状态）
     * @date 21:37 2019/3/18
     **/
    WebResponseVo<String> prodlineSell(Long prodlineProductId);


    /**
     * @author yuanyiwen
     * @description 获取全部能租赁的厂房类型（全部指的是当前设定下所有厂房类型），注意这里FactoryLeaseVo的id值同FactoryBasicInfo
     * @date 21:43 2019/3/18
     **/
    List<FactoryLeaseVo> getAllFactoryLeaseVos();


    /**
     * @author yuanyiwen
     * @description 租厂房
     * @date 21:46 2019/3/18
     **/
    FactoryDisplayVo factoryLease(Long factoryBasicId, Long enterpriseId);


    /**
     * @author yuanyiwen
     * @description 更新厂房的租赁情况，主要用于停止租赁和续租（enable为厂房的租赁状态，true为租赁中，false为停止租赁）
     * @date 21:47 2019/3/18
     **/
    FactoryDisplayVo updateFactoryLeaseStatus(Long factoryId, boolean enable);


}
