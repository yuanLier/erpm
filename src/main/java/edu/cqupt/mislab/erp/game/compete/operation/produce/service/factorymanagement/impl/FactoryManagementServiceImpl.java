package edu.cqupt.mislab.erp.game.compete.operation.produce.service.factorymanagement.impl;

import edu.cqupt.mislab.erp.commons.constant.FinanceOperationConstant;
import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.game.compete.operation.finance.service.FinanceService;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryHoldingInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.GameFactoryBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.GameProdlineBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineHoldingInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineProduceInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingStatus;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.GameFactoryBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.*;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.*;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineDevelopDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineDevelopVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineProduceDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.service.factorymanagement.FactoryManagementService;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toFailResponseVoWithMessage;
import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithNoData;
import static edu.cqupt.mislab.erp.commons.util.EntityVoUtil.copyFieldsFromEntityToVo;

/**
 * @author yuanyiwen
 * @create 2019-03-23 17:25
 * @description
 */
@Service
public class FactoryManagementServiceImpl implements FactoryManagementService {

    @Autowired
    private GameFactoryBasicInfoRepository gameFactoryBasicInfoRepository;
    @Autowired
    private FactoryDevelopInfoRepository factoryDevelopInfoRepository;
    @Autowired
    private FactoryHoldingInfoRepository factoryHoldingInfoRepository;

    @Autowired
    private GameProdlineBasicInfoRepository gameProdlineBasicInfoRepository;
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

    @Autowired
    private FinanceService financeService;


    @Override
    public List<ProdlineDevelopVo> getAllProdlineDevelopVosByType(Long gameId) {
        // 获取当前设定下的全部生产线信息
        List<GameProdlineBasicInfo> prodlineBasicInfoList = gameProdlineBasicInfoRepository.findByGameBasicInfo_Id(gameId);

        // 将生产线信息转化为List<ProdlineDevelopVo>
        List<ProdlineDevelopVo> prodlineDevelopVoList = new ArrayList<>();
        for (GameProdlineBasicInfo prodlineBasicInfo : prodlineBasicInfoList) {
            prodlineDevelopVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(prodlineBasicInfo));
        }

        return prodlineDevelopVoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProdlineDevelopDisplayVo buildProdlineOfHoldingFactory(Long prodlineBasicId, Long productId, Long factoryId, Long enterpriseId) {
        EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        // 添加一条ProdlineHoldingInfo
        ProdlineHoldingInfo prodlineHoldingInfo = new ProdlineHoldingInfo();
        prodlineHoldingInfo.setProdlineBasicInfo(gameProdlineBasicInfoRepository.findOne(prodlineBasicId));
        prodlineHoldingInfo.setFactoryHoldingInfo(factoryHoldingInfoRepository.findOne(factoryId));
        prodlineHoldingInfo.setEnterpriseBasicInfo(enterpriseBasicInfo);
        // 设置生产线拥有状态为修建中
        prodlineHoldingInfo.setProdlineHoldingStatus(ProdlineHoldingStatus.DEVELOPING);

        // 持久化该条ProdlineHoldingInfo
        Long prodlineHoldingId = prodlineHoldingInfoRepository.saveAndFlush(prodlineHoldingInfo).getId();

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
        ProdlineDevelopInfo developInfo = prodlineDevelopInfoRepository.saveAndFlush(prodlineDevelopInfo);

        return copyFieldsFromEntityToVo(developInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProdlineDevelopDisplayVo updateProdlineDevelopStatus(Long prodlineDevelopId, ProdlineDevelopStatus prodlineDevelopStatus) {
        // 获取要修改的那条生产线信息
        ProdlineDevelopInfo prodlineDevelopInfo = prodlineDevelopInfoRepository.findOne(prodlineDevelopId);

        // 更新生产线修建状态
        prodlineDevelopInfo.setProdlineDevelopStatus(prodlineDevelopStatus);
        // 保存修改
        prodlineDevelopInfoRepository.save(prodlineDevelopInfo);

        return copyFieldsFromEntityToVo(prodlineDevelopInfo);
    }

    @Override
    public FactoryDetailVo getFactoryDetailVo(Long factoryId) {
        // 获取要查看的那条厂房信息
        FactoryHoldingInfo factoryHoldingInfo = factoryHoldingInfoRepository.findOne(factoryId);

        return copyFieldsFromEntityToVo(factoryHoldingInfo);
    }


    @Override
    public List<FactoryDevelopVo> getAllFactoryDevelopVosByType(Long gameId) {
        // 获取当前设定下的全部厂房信息
        List<GameFactoryBasicInfo> factoryBasicInfoList = gameFactoryBasicInfoRepository.findByGameBasicInfo_Id(gameId);

        // 将生产线信息转化为List<FactoryDevelopVo>
        List<FactoryDevelopVo> factoryDevelopVoList = new ArrayList<>();
        for (GameFactoryBasicInfo factoryBasicInfo : factoryBasicInfoList) {
            factoryDevelopVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(factoryBasicInfo));
        }

        return factoryDevelopVoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FactoryDevelopDisplayVo buildFactoryOfEnterprise(Long enterpriseId, Long factoryBasicId) {
        EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        // 直接添加一条厂房建造信息就好
        FactoryDevelopInfo factoryDevelopInfo = new FactoryDevelopInfo();

        factoryDevelopInfo.setEnterpriseBasicInfo(enterpriseBasicInfo);
        factoryDevelopInfo.setFactoryBasicInfo(gameFactoryBasicInfoRepository.findOne(factoryBasicId));
        // 开始建造的周期为所属企业的当前周期
        factoryDevelopInfo.setBeginPeriod(enterpriseBasicInfo.getEnterpriseCurrentPeriod());
        // 已结修建的周期数为0
        factoryDevelopInfo.setDevelopedPeriod(0);
        // 修建状态为true，即修建中
        factoryDevelopInfo.setEnable(true);
        // 设置为未修建完成
        factoryDevelopInfo.setDeveloped(false);

        //持久化该建造信息
        FactoryDevelopInfo developInfo = factoryDevelopInfoRepository.saveAndFlush(factoryDevelopInfo);

        return copyFieldsFromEntityToVo(developInfo, new FactoryDevelopDisplayVo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FactoryDevelopDisplayVo updateFactoryDevelopStatus(Long factoryDevelopId, boolean enable) {
        FactoryDevelopInfo factoryDevelopInfo = factoryDevelopInfoRepository.findOne(factoryDevelopId);

        // 更新并保存修改
        factoryDevelopInfo.setEnable(enable);
        factoryDevelopInfoRepository.save(factoryDevelopInfo);

        return copyFieldsFromEntityToVo(factoryDevelopInfo, new FactoryDevelopDisplayVo());
    }

    @Override
    public List<FactoryDevelopDisplayVo> getAllFactoryDevelopVos(Long enterpriseId) {
        // 获取企业中的全部修建中厂房
        List<FactoryDevelopInfo> factoryDevelopInfoList = factoryDevelopInfoRepository.findByEnterpriseBasicInfo_IdAndDeveloped(enterpriseId, false);

        List<FactoryDevelopDisplayVo> factoryDevelopDisplayVoList = new ArrayList<>();
        for (FactoryDevelopInfo factoryDevelopInfo : factoryDevelopInfoList) {
            factoryDevelopDisplayVoList.add(copyFieldsFromEntityToVo(factoryDevelopInfo, new FactoryDevelopDisplayVo()));
        }

        return factoryDevelopDisplayVoList;
    }

    @Override
    public FactoryDevelopDetailVo getFactoryDevelopDetailVo(Long factoryDevelopId) {
        // 获取要查看的那条厂房修建信息
        FactoryDevelopInfo factoryDevelopInfo = factoryDevelopInfoRepository.findOne(factoryDevelopId);

        return copyFieldsFromEntityToVo(factoryDevelopInfo, new FactoryDevelopDetailVo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
        factoryHoldingInfoRepository.save(factoryHoldingInfo);

        return toSuccessResponseVoWithNoData();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WebResponseVo<String> prodlineSell(Long prodlineProductId) {
        // 首先获取要出售的生产线
        ProdlineProduceInfo prodlineProduceInfo = prodlineProduceInfoRepository.findOne(prodlineProductId);

        // 判断生产线是否处于运作中
        if(prodlineProduceInfo.getProdlineProduceStatus().equals(ProdlineProduceStatus.PRODUCING)) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "生产线运作中，无法出售，请先暂停生产");
        }

        // 将生产线生产状态设置为已出售
        ProdlineHoldingInfo prodlineHoldingInfo = prodlineProduceInfo.getProdlineHoldingInfo();
        prodlineHoldingInfo.setProdlineHoldingStatus(ProdlineHoldingStatus.SOLD);

        // 由于生产线出售是当场到款，所以直接在这里更新余额

        // 计算不考虑残值的情况下，确认出售时生产线的剩余价值（该值 = 最初生产线价值 - 每期折旧价值*(企业当前周期-安装完成周期)）
        Integer developedPeriod = prodlineDevelopInfoRepository.findByProdlineHoldingInfo_Id(prodlineHoldingInfo.getId()).getEndPeriod();
        Double actualValue = prodlineHoldingInfo.getProdlineBasicInfo().getProdlineBasicInfo().getProdlineValue() - prodlineHoldingInfo.getProdlineBasicInfo().getProdlineBasicInfo().getProdlineDepreciation() * (prodlineHoldingInfo.getEnterpriseBasicInfo().getEnterpriseCurrentPeriod() - developedPeriod);
        // 则生产线实际售卖价值等于actualValue与生产线残值取其大
        Double changeAmount = Math.max(actualValue, prodlineHoldingInfo.getProdlineBasicInfo().getProdlineBasicInfo().getProdlineStumpcost());
        // 生产线出售所得金额到账
        Long enterpriseId = prodlineHoldingInfo.getEnterpriseBasicInfo().getId();
        String changeOperating = FinanceOperationConstant.PRODLINE_SELL;
        financeService.updateFinanceInfo(enterpriseId, changeOperating, changeAmount, false);

        // 保存修改
        prodlineHoldingInfoRepository.save(prodlineHoldingInfo);

        return toSuccessResponseVoWithNoData();
    }

    @Override
    public List<FactoryLeaseVo> getAllFactoryLeaseVosByType(Long gameId) {
        // 获取当前设定下的全部厂房信息
        List<GameFactoryBasicInfo> factoryBasicInfoList = gameFactoryBasicInfoRepository.findByGameBasicInfo_Id(gameId);

        // 将生产线信息转化为List<FactoryLeaseVo>
        List<FactoryLeaseVo> factoryLeaseVoList = new ArrayList<>();
        for (GameFactoryBasicInfo factoryBasicInfo : factoryBasicInfoList) {
            FactoryLeaseVo factoryLeaseVo = new FactoryLeaseVo();

            factoryLeaseVo.setId(factoryBasicInfo.getId());
            factoryLeaseVo.setFactoryType(factoryBasicInfo.getFactoryBasicInfo().getFactoryType());
            factoryLeaseVo.setFactoryRentCost(factoryBasicInfo.getFactoryBasicInfo().getFactoryRentCost());

            factoryLeaseVoList.add(factoryLeaseVo);
        }

        return factoryLeaseVoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FactoryDisplayVo factoryLease(Long factoryBasicId, Long enterpriseId) {

        EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(enterpriseId);

        FactoryHoldingInfo factoryHoldingInfo = new FactoryHoldingInfo();

        factoryHoldingInfo.setEnterpriseBasicInfo(enterpriseBasicInfo);
        factoryHoldingInfo.setFactoryBasicInfo(gameFactoryBasicInfoRepository.findOne(factoryBasicId));
        // 厂房的拥有状态为租来的
        factoryHoldingInfo.setFactoryHoldingStatus(FactoryHoldingStatus.LEASING);
        // 开始租赁的周期为厂房所处企业的当前周期
        factoryHoldingInfo.setBeginPeriod(enterpriseBasicInfo.getEnterpriseCurrentPeriod());
        // 停止租赁的周期数置为空
        factoryHoldingInfo.setEndPeriod(null);
        // 设置当前租赁状态为租赁中
        factoryHoldingInfo.setEnable(true);

        // 持久化该条FactoryHoldingInfo
        FactoryHoldingInfo holdingInfo = factoryHoldingInfoRepository.saveAndFlush(factoryHoldingInfo);

        return copyFieldsFromEntityToVo(holdingInfo, new ArrayList<>(), new ArrayList<>());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FactoryDisplayVo leasePause(Long factoryId) {
        FactoryHoldingInfo factoryHoldingInfo = factoryHoldingInfoRepository.findOne(factoryId);

        // 更新停租日期与租赁状态
        factoryHoldingInfo.setEndPeriod(factoryHoldingInfo.getEnterpriseBasicInfo().getEnterpriseCurrentPeriod());
        factoryHoldingInfo.setEnable(false);
        factoryHoldingInfoRepository.save(factoryHoldingInfo);

        // 清空厂房中全部生产线的生产状态
        // 首先将holdingInfo更新为不可用
        List<ProdlineHoldingInfo> prodlineHoldingInfoList = prodlineHoldingInfoRepository.findByFactoryHoldingInfo_Id(factoryId);
        for(ProdlineHoldingInfo prodlineHoldingInfo : prodlineHoldingInfoList) {
            prodlineHoldingInfo.setProdlineHoldingStatus(ProdlineHoldingStatus.NOT_USABLE);
            prodlineHoldingInfoRepository.save(prodlineHoldingInfo);
        }

        // 然后修建中的直接删除，生产中的清空生产状态
        List<ProdlineProduceInfo> prodlineProduceInfoList = prodlineProduceInfoRepository.findByProdlineHoldingInfo_FactoryHoldingInfo_Id(factoryId);
        List<ProdlineProduceDisplayVo> produceDisplayVoList = new ArrayList<>();
        for (ProdlineProduceInfo prodlineProduceInfo : prodlineProduceInfoList) {
            // 开始生产的时间置为空，已经生产的周期置为0，生产状态置为不可用
            prodlineProduceInfo.setBeginPeriod(null);
            prodlineProduceInfo.setProducedPeriod(0);
            prodlineProduceInfo.setProdlineProduceStatus(ProdlineProduceStatus.NOT_USABLE);
            produceDisplayVoList.add(copyFieldsFromEntityToVo(prodlineProduceInfo, new ProdlineProduceDisplayVo()));
        }
        List<ProdlineDevelopInfo> prodlineDevelopInfoList = prodlineDevelopInfoRepository.findByProdlineHoldingInfo_FactoryHoldingInfo_Id(factoryId);
        for(ProdlineDevelopInfo prodlineDevelopInfo : prodlineDevelopInfoList) {
            // 直接删除，不要怕；没修建完成的生产线是不会计入历史数据的，所以不会对生产线总数造成影响
            // 顺便这可能是整个系统唯一一个用到delete的地方了qvq
            prodlineDevelopInfoRepository.delete(prodlineDevelopInfo.getId());
            // 记得要把对应的holdingInfo也一起删了
            prodlineHoldingInfoRepository.delete(prodlineDevelopInfo.getProdlineHoldingInfo().getId());
        }

        // 最后返回这个停租了的厂房
        return copyFieldsFromEntityToVo(factoryHoldingInfo, produceDisplayVoList, new ArrayList<>());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public FactoryDisplayVo leaseContinue(Long factoryId) {

        FactoryHoldingInfo factoryHoldingInfo = factoryHoldingInfoRepository.findOne(factoryId);

        // 更新租赁日期与租赁状态
        factoryHoldingInfo.setBeginPeriod(factoryHoldingInfo.getEnterpriseBasicInfo().getEnterpriseCurrentPeriod());
        factoryHoldingInfo.setEndPeriod(null);
        factoryHoldingInfo.setEnable(true);
        factoryHoldingInfoRepository.save(factoryHoldingInfo);

        // 事实上，续租时这个厂房里只可能存在处于生产状态的生产线了；所以这里直接将holdingInfo更新为生产状态
        List<ProdlineHoldingInfo> prodlineHoldingInfoList = prodlineHoldingInfoRepository.findByFactoryHoldingInfo_Id(factoryId);
        for(ProdlineHoldingInfo prodlineHoldingInfo : prodlineHoldingInfoList) {
            prodlineHoldingInfo.setProdlineHoldingStatus(ProdlineHoldingStatus.PRODUCING);
            prodlineHoldingInfoRepository.save(prodlineHoldingInfo);
        }

        // 获取厂房中处于生产状态的生产线
        List<ProdlineProduceInfo> prodlineProduceInfoList = prodlineProduceInfoRepository.findByProdlineHoldingInfo_FactoryHoldingInfo_Id(factoryId);
        List<ProdlineProduceDisplayVo> produceDisplayVoList = new ArrayList<>();
        for (ProdlineProduceInfo prodlineProduceInfo : prodlineProduceInfoList) {
            // 生产状态置为未生产
            prodlineProduceInfo.setProdlineProduceStatus(ProdlineProduceStatus.TOPRODUCE);
            produceDisplayVoList.add(copyFieldsFromEntityToVo(prodlineProduceInfo, new ProdlineProduceDisplayVo()));
        }

        // 最后返回这个租来的厂房
        return copyFieldsFromEntityToVo(factoryHoldingInfo, produceDisplayVoList, new ArrayList<>());
    }

}
