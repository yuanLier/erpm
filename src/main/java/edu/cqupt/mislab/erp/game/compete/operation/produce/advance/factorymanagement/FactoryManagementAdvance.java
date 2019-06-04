package edu.cqupt.mislab.erp.game.compete.operation.produce.advance.factorymanagement;

import edu.cqupt.mislab.erp.commons.constant.FinanceOperationConstant;
import edu.cqupt.mislab.erp.commons.constant.ProduceOperationConstant;
import edu.cqupt.mislab.erp.game.compete.basic.ModelAdvance;
import edu.cqupt.mislab.erp.game.compete.operation.finance.service.FinanceService;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryHistoryRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryHoldingInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineHoldingInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineProduceInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHistoryInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingStatus;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.*;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-06-01 14:29
 * @description
 */
@Slf4j
@Component
public class FactoryManagementAdvance implements ModelAdvance {

    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;

    @Autowired
    private FactoryDevelopInfoRepository factoryDevelopInfoRepository;
    @Autowired
    private FactoryHoldingInfoRepository factoryHoldingInfoRepository;
    @Autowired
    private ProdlineDevelopInfoRepository prodlineDevelopInfoRepository;
    @Autowired
    private ProdlineHoldingInfoRepository prodlineHoldingInfoRepository;
    @Autowired
    private ProdlineProduceInfoRepository prodlineProduceInfoRepository;

    @Autowired
    private FactoryHistoryRepository factoryHistoryRepository;

    @Autowired
    private FinanceService financeService;


    @Override
    public boolean modelHistory(Long gameId) {

        log.info("开始记录厂房管理模块比赛期间历史数据");

        // 获取该比赛中全部进行中企业
        List<EnterpriseBasicInfo> enterpriseBasicInfoList = enterpriseBasicInfoRepository.findByGameBasicInfo_IdAndEnterpriseStatus(gameId, EnterpriseStatusEnum.PLAYING);

        for(EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfoList) {

            // 记录厂房的历史数据
            factoryHistoryRecord(enterpriseBasicInfo);

            // todo 记录生产线的历史数据
            prodlineHistoryRecord(enterpriseBasicInfo);
        }

        log.info("厂房管理模块-比赛期间历史数据记录成功");

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean modelAdvance(Long gameId) {

        log.info("开始进行厂房管理模块比赛期间周期推进");

        // 获取该比赛中全部进行中企业
        List<EnterpriseBasicInfo> enterpriseBasicInfoList = enterpriseBasicInfoRepository.findByGameBasicInfo_IdAndEnterpriseStatus(gameId, EnterpriseStatusEnum.PLAYING);

        for (EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfoList) {

            // 对该企业中全部修建完成且投入使用的厂房进行周期推进
            holdingEnableFactory(enterpriseBasicInfo);

            // 对该企业中全部租来且投入使用的厂房进行周期推进
            leasingEnableFactory(enterpriseBasicInfo);

            // 对该企业中全部修建完成且确认出售的厂房进行周期推进
            soldFactory(enterpriseBasicInfo);

            // 对该企业中全部修建中的厂房进行周期推进
            developingFactory(enterpriseBasicInfo);


            // 对该企业中全部安装完成且投入使用的生产线进行周期推进
            holdingEnableProdline(enterpriseBasicInfo);

            // 对该企业中全部安装中的生产线进行周期推进
            developingProdline(enterpriseBasicInfo);

        }


        log.info("厂房管理模块-比赛期间周期推进正常");

        // 运行到这里说明周期推进正常
        return true;

    }


    /**
     * 对该企业中全部修建完成且投入使用的厂房进行周期推进
     * @param enterpriseBasicInfo
     */
    private void holdingEnableFactory(EnterpriseBasicInfo enterpriseBasicInfo) {

        List<FactoryHoldingInfo> factoryHoldingInfoList = factoryHoldingInfoRepository.findByEnterpriseBasicInfo_IdAndFactoryHoldingStatusAndEnable(enterpriseBasicInfo.getId(), FactoryHoldingStatus.HOLDING, true);
        for (FactoryHoldingInfo factoryHoldingInfo : factoryHoldingInfoList) {

            // 扣除修建完成后需要支付的维护费用
            Long enterpriseId = enterpriseBasicInfo.getId();
            String changeOperating = FinanceOperationConstant.FACTORY_MAINTAIN;
            Double changeAmount = factoryHoldingInfo.getFactoryBasicInfo().getFactoryMaintainCost();
            financeService.updateFinanceInfo(enterpriseId, changeOperating, changeAmount, true);

        }
    }

    /**
     * 对该企业中全部租来且投入使用的厂房进行周期推进
     * @param enterpriseBasicInfo
     */
    private void leasingEnableFactory(EnterpriseBasicInfo enterpriseBasicInfo) {

        List<FactoryHoldingInfo> factoryHoldingInfoList = factoryHoldingInfoRepository.findByEnterpriseBasicInfo_IdAndFactoryHoldingStatusAndEnable(enterpriseBasicInfo.getId(), FactoryHoldingStatus.LEASING, true);
        for (FactoryHoldingInfo factoryHoldingInfo : factoryHoldingInfoList) {

            // 扣除租赁过程中需要支付的费用
            Long enterpriseId = enterpriseBasicInfo.getId();
            String changeOperating = FinanceOperationConstant.FACTORY_LEASING;
            Double changeAmount = factoryHoldingInfo.getFactoryBasicInfo().getFactoryRentCost();
            financeService.updateFinanceInfo(enterpriseId, changeOperating, changeAmount, true);

        }
    }

    /**
     * 对该企业中全部修建完成且确认出售的厂房进行周期推进
     * @param enterpriseBasicInfo
     */
    private void soldFactory(EnterpriseBasicInfo enterpriseBasicInfo) {

        List<FactoryHoldingInfo> factoryHoldingInfoList = factoryHoldingInfoRepository.findByEnterpriseBasicInfo_IdAndFactoryHoldingStatusAndEnable(enterpriseBasicInfo.getId(), FactoryHoldingStatus.HOLDING, false);
        for (FactoryHoldingInfo factoryHoldingInfo : factoryHoldingInfoList) {

            // 若当前周期 - 确认出售的周期 = 延期到账所需要的周期
            if(enterpriseBasicInfo.getEnterpriseCurrentPeriod() - factoryHoldingInfo.getEndPeriod() == factoryHoldingInfo.getFactoryBasicInfo().getFactoryDelayTime()) {

                // 计算不考虑残值的情况下，确认出售时厂房的剩余价值（该值 = 最初厂房价值 - 每期折旧价值*(确认售卖周期-建造完成周期)）
                Double actualValue = factoryHoldingInfo.getFactoryBasicInfo().getFactoryValue() - factoryHoldingInfo.getFactoryBasicInfo().getFactoryDepreciation()*(factoryHoldingInfo.getEndPeriod()-factoryHoldingInfo.getBeginPeriod());
                // 则厂房实际售卖价值等于actualValue与厂房残值取其大
                Double changeAmount = Math.max(actualValue, factoryHoldingInfo.getFactoryBasicInfo().getFactoryStumpCost());

                // 厂房出售所得金额到账
                Long enterpriseId = enterpriseBasicInfo.getId();
                String changeOperating = FinanceOperationConstant.FACTORY_SELL;
                financeService.updateFinanceInfo(enterpriseId, changeOperating, changeAmount, false);
            }
        }
    }

    /**
     * 对该企业中全部修建中的厂房进行周期推进
     * @param enterpriseBasicInfo
     */
    private void developingFactory(EnterpriseBasicInfo enterpriseBasicInfo) {

        List<FactoryDevelopInfo> factoryDevelopInfoList = factoryDevelopInfoRepository.findByEnterpriseBasicInfo_IdAndDeveloped(enterpriseBasicInfo.getId(), false);
        for (FactoryDevelopInfo factoryDevelopInfo : factoryDevelopInfoList) {

            // 已经修建的周期数+1
            factoryDevelopInfo.setDevelopedPeriod(factoryDevelopInfo.getDevelopedPeriod()+1);

            // 如果已经修建的周期数达到了该厂房修建所需的周期数
            if(factoryDevelopInfo.getDevelopedPeriod() == factoryDevelopInfo.getFactoryBasicInfo().getFactoryMakePeriod()) {

                // 设置为修建完成
                factoryDevelopInfo.setEndPeriod(enterpriseBasicInfo.getEnterpriseCurrentPeriod());
                factoryDevelopInfo.setDeveloped(true);

                // 将该条厂房的信息加入HoldingInfo
                factoryHoldingInfoRepository.save(FactoryHoldingInfo.builder()
                        .enterpriseBasicInfo(enterpriseBasicInfo)
                        .factoryBasicInfo(factoryDevelopInfo.getFactoryBasicInfo())
                        .factoryHoldingStatus(FactoryHoldingStatus.HOLDING)
                        .beginPeriod(enterpriseBasicInfo.getEnterpriseCurrentPeriod())
                        .endPeriod(null)
                        .enable(true)
                        .build());
            }

            // 保存修改
            factoryDevelopInfoRepository.save(factoryDevelopInfo);

            // 扣除修建过程中需要支付的费用
            Long enterpriseId = enterpriseBasicInfo.getId();
            String changeOperating = FinanceOperationConstant.FACTORY_DEVELOP;
            Double changeAmount = factoryDevelopInfo.getFactoryBasicInfo().getFactoryMakeCost();
            financeService.updateFinanceInfo(enterpriseId, changeOperating, changeAmount, true);
        }
    }

    /**
     * 对该企业中全部安装完成且投入使用的生产线进行周期推进
     * @param enterpriseBasicInfo
     */
    private void holdingEnableProdline(EnterpriseBasicInfo enterpriseBasicInfo) {

        // 处于producing状态下就说明安装完成且未被售卖，即投入使用了
        List<ProdlineHoldingInfo> prodlineHoldingInfoList = prodlineHoldingInfoRepository.findByEnterpriseBasicInfo_IdAndProdlineHoldingStatus(enterpriseBasicInfo.getId(), ProdlineHoldingStatus.PRODUCING);
        for (ProdlineHoldingInfo prodlineHoldingInfo : prodlineHoldingInfoList) {

            // 扣除安装完成后需要支付的维护费用
            Long enterpriseId = enterpriseBasicInfo.getId();
            String changeOperating = FinanceOperationConstant.PRODLINE_MAINTAIN;
            Double changeAmount = prodlineHoldingInfo.getProdlineBasicInfo().getProdlineMainCost();
            financeService.updateFinanceInfo(enterpriseId, changeOperating, changeAmount, true);

        }
    }


    /**
     * 对该企业中全部安装中的生产线进行周期推进
     * @param enterpriseBasicInfo
     */
    private void developingProdline(EnterpriseBasicInfo enterpriseBasicInfo) {

        List<ProdlineDevelopInfo> prodlineDevelopInfoList = prodlineDevelopInfoRepository.findByProdlineHoldingInfo_EnterpriseBasicInfo_IdAndProdlineDevelopStatus(enterpriseBasicInfo.getId(), ProdlineDevelopStatus.DEVELOPING);
        for (ProdlineDevelopInfo prodlineDevelopInfo : prodlineDevelopInfoList) {

            // 已经安装的周期数+1
            prodlineDevelopInfo.setDevelopedPeriod(prodlineDevelopInfo.getDevelopedPeriod()+1);

            // 如果已经安装的周期数达到了该生产线安装所需的周期数
            if(prodlineDevelopInfo.getDevelopedPeriod() == prodlineDevelopInfo.getProdlineHoldingInfo().getProdlineBasicInfo().getProdlineSetupPeriod()) {

                // 设置为安装完成
                prodlineDevelopInfo.setProdlineDevelopStatus(ProdlineDevelopStatus.DEVELOPED);

                // 更新生产线的holding状态
                ProdlineHoldingInfo prodlineHoldingInfo = prodlineDevelopInfo.getProdlineHoldingInfo();
                prodlineHoldingInfo.setProdlineHoldingStatus(ProdlineHoldingStatus.PRODUCING);
                
                // 保存修改
                prodlineHoldingInfoRepository.save(prodlineHoldingInfo);

                // 计算产品实际需要的生产时间（值为产品的生产时间*生产线的加速时间取整）
                Integer productDuration = (int)(prodlineDevelopInfo.getProductDevelopInfo().getProductBasicInfo().getProduceProductPeriod() * prodlineDevelopInfo.getProdlineHoldingInfo().getProdlineBasicInfo().getExtraPeriod());
                // 添加一条信息到produceInfo
                prodlineProduceInfoRepository.save(ProdlineProduceInfo.builder()
                        .prodlineHoldingInfo(prodlineHoldingInfo)
                        .productDevelopInfo(prodlineDevelopInfo.getProductDevelopInfo())
                        .produceDuration(productDuration)
                        .beginPeriod(null)
                        .endPeriod(null)
                        .producedPeriod(0)
                        .prodlineProduceStatus(ProdlineProduceStatus.TOPRODUCE)
                        .build()
                );
            }

            // 保存修改
            prodlineDevelopInfoRepository.save(prodlineDevelopInfo);

            // 扣除安装过程中需要支付的费用
            Long enterpriseId = enterpriseBasicInfo.getId();
            String changeOperating = FinanceOperationConstant.PRODLINE_DEVELOP;
            Double changeAmount = prodlineDevelopInfo.getProdlineHoldingInfo().getProdlineBasicInfo().getProdlineSetupPeriodPrice();
            financeService.updateFinanceInfo(enterpriseId, changeOperating, changeAmount, true);
        }
    }


    /**
     * 记录厂房的历史数据
     * @param enterpriseBasicInfo
     */
    private void factoryHistoryRecord(EnterpriseBasicInfo enterpriseBasicInfo) {
        // 获取企业全部拥有的厂房 暴力比较一下算了
        List<FactoryHoldingInfo> factoryHoldingInfoList = factoryHoldingInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseBasicInfo.getId());

        for(FactoryHoldingInfo factoryHoldingInfo : factoryHoldingInfoList) {

            final FactoryHistoryInfo.FactoryHistoryInfoBuilder factoryHistoryInfo = FactoryHistoryInfo.builder()
                    .enterpriseBasicInfo(enterpriseBasicInfo)
                    .factoryBasicInfo(factoryHoldingInfo.getFactoryBasicInfo())
                    .period(enterpriseBasicInfo.getEnterpriseCurrentPeriod());

            // 确定对该厂房进行的具体操作
            Integer current = enterpriseBasicInfo.getEnterpriseCurrentPeriod();
            if(current.equals(factoryHoldingInfo.getBeginPeriod())) {

                // holding-begin 修建完成   leasing-begin 租赁
                if(factoryHoldingInfo.getFactoryHoldingStatus() == FactoryHoldingStatus.HOLDING) {
                    factoryHistoryInfo.operation(ProduceOperationConstant.FACTORY_DEVELOPED).mount(1);
                } else {
                    factoryHistoryInfo.operation(ProduceOperationConstant.FACTORY_LEASE).mount(1);
                }

                factoryHistoryRepository.save(factoryHistoryInfo.build());

            } else if(current.equals(factoryHoldingInfo.getEndPeriod())) {

                // holding-end 确认出售     leasing-end 停租
                if(factoryHoldingInfo.getFactoryHoldingStatus() == FactoryHoldingStatus.HOLDING) {
                    factoryHistoryInfo.operation(ProduceOperationConstant.FACTORY_SOLD).mount(-1);
                } else {
                    factoryHistoryInfo.operation(ProduceOperationConstant.FACTORY_LEASE_PAUSE).mount(-1);
                }

                factoryHistoryRepository.save(factoryHistoryInfo.build());
            }
        }
    }


    /**
     * 记录生产线的历史数据
     * @param enterpriseBasicInfo
     */
    private void prodlineHistoryRecord(EnterpriseBasicInfo enterpriseBasicInfo) {

    }

}
