package edu.cqupt.mislab.erp.game.compete.operation.produce.service.factorymanagement.impl;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryHoldingInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineHoldingInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineProduceInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingStatus;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.*;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.*;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineDevelopDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineProduceDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.service.factorymanagement.FactoryManagementService;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toFailResponseVoWithMessage;
import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithNoData;

/**
 * @author yuanyiwen
 * @create 2019-03-23 17:25
 * @description
 */
@Service
public class FactoryManagementServiceImpl implements FactoryManagementService {

    @Autowired
    private FactoryBasicInfoRepository factoryBasicInfoRepository;
    @Autowired
    private FactoryDevelopInfoRepository factoryDevelopInfoRepository;
    @Autowired
    private FactoryHoldingInfoRepository factoryHoldingInfoRepository;

    @Autowired
    private ProdlineBasicInfoRepository prodlineBasicInfoRepository;
    @Autowired
    private ProdlineDevelopInfoRepository prodlineDevelopInfoRepository;
    @Autowired
    private ProdlineHoldingInfoRepository prodlineHoldingInfoRepository;
    @Autowired
    private ProdlineProduceInfoRepository prodlineProduceInfoRepository;

    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;

    @Autowired
    private ProductDevelopInfoRepository productDevelopInfoRepository;


    @Override
    public List<ProdlineTypeVo> getAllProdlineTypeVos() {
        // 获取当前设定下的全部生产线信息
        List<ProdlineBasicInfo> prodlineBasicInfoList = prodlineBasicInfoRepository.findNewestProdlineBasicInfos();

        // 将生产线信息转化为List<ProdlineTypeVo>
        List<ProdlineTypeVo> prodlineTypeVoList = new ArrayList<>();
        for (ProdlineBasicInfo prodlineBasicInfo : prodlineBasicInfoList) {
            ProdlineTypeVo prodlineTypeVo = new ProdlineTypeVo();

            prodlineTypeVo.setId(prodlineBasicInfo.getId());
            prodlineTypeVo.setProdlineType(prodlineBasicInfo.getProdlineType());

            prodlineTypeVoList.add(prodlineTypeVo);
        }

        return prodlineTypeVoList;
    }

    @Override
    public ProdlineDevelopDisplayVo buildProdlineOfHoldingFactory(Long prodlineBasicId, Long productId, Long factoryId, Long enterpriseId) {
        EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        // 添加一条ProdlineHoldingInfo
        ProdlineHoldingInfo prodlineHoldingInfo = new ProdlineHoldingInfo();
        prodlineHoldingInfo.setProdlineBasicInfo(prodlineBasicInfoRepository.findOne(prodlineBasicId));
        prodlineHoldingInfo.setFactoryHoldingInfo(factoryHoldingInfoRepository.findOne(factoryId));
        prodlineHoldingInfo.setEnterpriseBasicInfo(enterpriseBasicInfo);
        // 设置生产线拥有状态为生产中
        prodlineHoldingInfo.setProdlineHoldingStatus(ProdlineHoldingStatus.DEVELOPING);

        // 持久化该条ProdlineHoldingInfo
        Long prodlineHoldingId;
        try {
            prodlineHoldingId = prodlineHoldingInfoRepository.saveAndFlush(prodlineHoldingInfo).getId();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // 再添加一条生产线安装信息，即ProdlineDevelopInfo
        ProdlineDevelopInfo prodlineDevelopInfo = new ProdlineDevelopInfo();
        prodlineDevelopInfo.setProdlineHoldingInfo(prodlineHoldingInfoRepository.findOne(prodlineHoldingId));
        prodlineDevelopInfo.setProductDevelopInfo(productDevelopInfoRepository.findOne(productId));
        // 开始安装的周期为企业所处的当前周期
        prodlineDevelopInfo.setBeginPeriod(enterpriseBasicInfo.getEnterpriseCurrentPeriod());
        // 已经安装的周期数为0
        prodlineDevelopInfo.setDevelopedPeriod(0);
        // 设置生产线安装状态为安装中
        prodlineDevelopInfo.setProdlineDevelopStatus(ProdlineDevelopStatus.DEVELOPING);

        // 持久化该条prodlineDevelopInfo
        ProdlineDevelopInfo developInfo;
        try {
            developInfo = prodlineDevelopInfoRepository.saveAndFlush(prodlineDevelopInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return EntityVoUtil.copyFieldsFromEntityToVo(developInfo);
    }

    @Override
    public ProdlineDevelopDisplayVo updateProdlineDevelopStatus(Long prodlineDevelopId, ProdlineDevelopStatus prodlineDevelopStatus) {
        // 获取要修改的那条生产线信息
        ProdlineDevelopInfo prodlineDevelopInfo = prodlineDevelopInfoRepository.findOne(prodlineDevelopId);

        // 更新生产线修建状态
        prodlineDevelopInfo.setProdlineDevelopStatus(prodlineDevelopStatus);
        // 保存修改
        try {
            prodlineDevelopInfoRepository.save(prodlineDevelopInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return EntityVoUtil.copyFieldsFromEntityToVo(prodlineDevelopInfo);
    }

    @Override
    public FactoryDetailVo getFactoryDetailVo(Long factoryId) {
        // 获取要查看的那条厂房信息
        FactoryHoldingInfo factoryHoldingInfo = factoryHoldingInfoRepository.findOne(factoryId);

        return EntityVoUtil.copyFieldsFromEntityToVo(factoryHoldingInfo);
    }


    @Override
    public List<FactoryTypeVo> getAllFactoryTypeVos() {
        // 获取当前设定下的全部厂房信息
        List<FactoryBasicInfo> factoryBasicInfoList = factoryBasicInfoRepository.findNewestFactoryBasicInfos();

        // 将生产线信息转化为List<FactoryTypeVo>
        List<FactoryTypeVo> factoryTypeVoList = new ArrayList<>();
        for (FactoryBasicInfo factoryBasicInfo : factoryBasicInfoList) {
            FactoryTypeVo factoryTypeVo = new FactoryTypeVo();

            factoryTypeVo.setId(factoryBasicInfo.getId());
            factoryTypeVo.setFactoryType(factoryBasicInfo.getFactoryType());

            factoryTypeVoList.add(factoryTypeVo);
        }

        return factoryTypeVoList;
    }

    @Override
    public FactoryDevelopDisplayVo buildFactoryOfEnterprise(Long enterpriseId, Long factoryBasicId) {
        EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        // 直接添加一条厂房建造信息就好
        FactoryDevelopInfo factoryDevelopInfo = new FactoryDevelopInfo();

        factoryDevelopInfo.setEnterpriseBasicInfo(enterpriseBasicInfo);
        factoryDevelopInfo.setFactoryBasicInfo(factoryBasicInfoRepository.findOne(factoryBasicId));
        // 开始建造的周期为所属企业的当前周期
        factoryDevelopInfo.setBeginPeriod(enterpriseBasicInfo.getEnterpriseCurrentPeriod());
        // 已结修建的周期数为0
        factoryDevelopInfo.setDevelopedPeriod(0);
        // 修建状态为true，即修建中
        factoryDevelopInfo.setEnable(true);

        //持久化该建造信息
        FactoryDevelopInfo developInfo;
        try {
            developInfo = factoryDevelopInfoRepository.saveAndFlush(factoryDevelopInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return EntityVoUtil.copyFieldsFromEntityToVo(developInfo, new FactoryDevelopDisplayVo());
    }

    @Override
    public FactoryDevelopDisplayVo updateFactoryDevelopStatus(Long factoryDevelopId, boolean enable) {
        FactoryDevelopInfo factoryDevelopInfo = factoryDevelopInfoRepository.findOne(factoryDevelopId);

        // 更新并保存修改
        factoryDevelopInfo.setEnable(enable);
        try {
            factoryDevelopInfoRepository.save(factoryDevelopInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return EntityVoUtil.copyFieldsFromEntityToVo(factoryDevelopInfo, new FactoryDevelopDisplayVo());
    }

    @Override
    public List<FactoryDevelopDisplayVo> getAllFactoryDevelopVos(Long enterpriseId) {
        // 获取企业中的全部修建中厂房
        List<FactoryDevelopInfo> factoryDevelopInfoList = factoryDevelopInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId);

        List<FactoryDevelopDisplayVo> factoryDevelopDisplayVoList = new ArrayList<>();
        for (FactoryDevelopInfo factoryDevelopInfo : factoryDevelopInfoList) {
            factoryDevelopDisplayVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(factoryDevelopInfo, new FactoryDevelopDisplayVo()));
        }

        return factoryDevelopDisplayVoList;
    }

    @Override
    public FactoryDevelopDetailVo getFactoryDevelopDetailVo(Long factoryDevelopId) {
        // 获取要查看的那条厂房修建信息
        FactoryDevelopInfo factoryDevelopInfo = factoryDevelopInfoRepository.findOne(factoryDevelopId);

        return EntityVoUtil.copyFieldsFromEntityToVo(factoryDevelopInfo, new FactoryDevelopDetailVo());
    }

    @Override
    public WebResponseVo<String> factorySell(Long factoryId) {
        // 能出售的肯定是已经建好的厂房，所以首先获取这个厂房的信息
        FactoryHoldingInfo factoryHoldingInfo = factoryHoldingInfoRepository.findOne(factoryId);

        // 判断该厂房中是否存在正在生产的生产线信息
        List<ProdlineProduceInfo> prodlineProduceInfoList = prodlineProduceInfoRepository.findByProdlineHoldingInfo_FactoryHoldingInfo_IdAndProdlineProduceStatus(factoryId, ProdlineProduceStatus.PRODUCING);
        if(prodlineProduceInfoList.size() != 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "生产线运作中，无法出售厂房，请先停止生产");
        }

        // 判断该厂房中是否存在可用生产线
        List<ProdlineHoldingInfo> prodlineHoldingInfoList = prodlineHoldingInfoRepository.findByFactoryHoldingInfo_IdAndProdlineHoldingStatus(factoryId, ProdlineHoldingStatus.PRODUCING);
        if(prodlineHoldingInfoList.size() != 0) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "厂房中存在可用生产线，无法出售厂房，请先出售全部建造完成的生产线");
        }

        // 将enable设为false，表示该厂房（且一定是自建的）已出售
        factoryHoldingInfo.setEnable(false);

        // 保存修改
        try {
            factoryHoldingInfoRepository.save(factoryHoldingInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.INTERNAL_SERVER_ERROR, "厂房出售失败！请联系开发人员");
        }

        return toSuccessResponseVoWithNoData();
    }

    @Override
    public WebResponseVo<String> prodlineSell(Long prodlineProductId) {
        // 首先获取要出售的生产线
        ProdlineProduceInfo prodlineProduceInfo = prodlineProduceInfoRepository.findOne(prodlineProductId);

        // 判断生产线是否处于运作中
        if(prodlineProduceInfo.getProdlineProduceStatus().equals(ProdlineProduceStatus.PRODUCING)) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "生产线运作中，无法出售，请先暂停生产");
        }

        // 将生产线生产状态设置为已出售
        ProdlineHoldingInfo prodlineHoldingInfo = prodlineProduceInfo.getProdlineHoldingInfo();
        prodlineHoldingInfo.setProdlineHoldingStatus(ProdlineHoldingStatus.SELLED);

        // 保存修改
        try {
            prodlineHoldingInfoRepository.save(prodlineHoldingInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.INTERNAL_SERVER_ERROR, "生产线出售失败！请联系开发人员");
        }

        return toSuccessResponseVoWithNoData();
    }

    @Override
    public List<FactoryLeaseVo> getAllFactoryLeaseVos() {
        // 获取当前设定下的全部厂房信息
        List<FactoryBasicInfo> factoryBasicInfoList = factoryBasicInfoRepository.findNewestFactoryBasicInfos();

        // 将生产线信息转化为List<FactoryLeaseVo>
        List<FactoryLeaseVo> factoryLeaseVoList = new ArrayList<>();
        for (FactoryBasicInfo factoryBasicInfo : factoryBasicInfoList) {
            FactoryLeaseVo factoryLeaseVo = new FactoryLeaseVo();

            factoryLeaseVo.setId(factoryBasicInfo.getId());
            factoryLeaseVo.setFactoryType(factoryBasicInfo.getFactoryType());
            factoryLeaseVo.setFactoryRentCost(factoryBasicInfo.getFactoryRentCost());

            factoryLeaseVoList.add(factoryLeaseVo);
        }

        return factoryLeaseVoList;
    }

    @Override
    public FactoryDisplayVo factoryLease(Long factoryBasicId, Long enterpriseId) {

        EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        FactoryHoldingInfo factoryHoldingInfo = new FactoryHoldingInfo();

        factoryHoldingInfo.setEnterpriseBasicInfo(enterpriseBasicInfo);
        factoryHoldingInfo.setFactoryBasicInfo(factoryBasicInfoRepository.findOne(factoryBasicId));
        // 厂房的拥有状态为租来的
        factoryHoldingInfo.setFactoryHoldingStatus(FactoryHoldingStatus.LEASING);
        // 开始租赁的周期为厂房所处企业的当前周期
        factoryHoldingInfo.setBeginPeriod(enterpriseBasicInfo.getEnterpriseCurrentPeriod());
        // 已租赁的总周期数为0
        factoryHoldingInfo.setEndPeriod(0);
        // 设置当前租赁状态为租赁中
        factoryHoldingInfo.setEnable(true);

        // 持久化该条FactoryHoldingInfo
        FactoryHoldingInfo holdingInfo;
        try {
            holdingInfo = factoryHoldingInfoRepository.saveAndFlush(factoryHoldingInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return EntityVoUtil.copyFieldsFromEntityToVo(holdingInfo, new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public FactoryDisplayVo updateFactoryLeaseStatus(Long factoryId, boolean enable) {
        FactoryHoldingInfo factoryHoldingInfo = factoryHoldingInfoRepository.findOne(factoryId);

        if(factoryHoldingInfo == null) {
            return null;
        }

        // 更新并保存修改
        factoryHoldingInfo.setEnable(enable);
        try {
            factoryHoldingInfoRepository.save(factoryHoldingInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // 然后就是对获取厂房中生产线的一顿操作
        List<ProdlineProduceInfo> prodlineProduceInfoList = prodlineProduceInfoRepository.findByProdlineHoldingInfo_FactoryHoldingInfo_Id(factoryId);
        List<ProdlineProduceDisplayVo> produceDisplayVoList = EntityVoUtil.copyFieldsFromEntityToVo(prodlineProduceInfoList, new ProdlineProduceDisplayVo());
        List<ProdlineDevelopInfo> prodlineDevelopInfoList = prodlineDevelopInfoRepository.findByProdlineHoldingInfo_FactoryHoldingInfo_Id(factoryId);
        List<ProdlineDevelopDisplayVo> developDisplayVoList = EntityVoUtil.copyFieldsFromEntityToVo(prodlineDevelopInfoList, new ProdlineDevelopDisplayVo());

        // 最后返回这个租来的厂房（所以说少停租 多耗事的
        return EntityVoUtil.copyFieldsFromEntityToVo(factoryHoldingInfo, produceDisplayVoList, developDisplayVoList);
    }

}
