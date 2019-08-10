package edu.cqupt.mislab.erp.game.compete.operation.stock.service.impl;

import edu.cqupt.mislab.erp.commons.constant.FinanceOperationConstant;
import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.commons.util.BeanCopyUtil;
import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.game.compete.operation.finance.service.FinanceService;
import edu.cqupt.mislab.erp.game.compete.operation.material.dao.MaterialBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.MaterialBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.dao.MaterialOrderInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.dao.MaterialStockInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.dao.ProductStockInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.dao.GameTransportBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.dto.MaterialOrderDto;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.*;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo.*;
import edu.cqupt.mislab.erp.game.compete.operation.stock.service.StockService;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toFailResponseVoWithMessage;
import static edu.cqupt.mislab.erp.commons.response.WebResponseUtil.toSuccessResponseVoWithNoData;

/**
 * @author yuanyiwen
 * @description 采购与仓库管理service
 * @date 19:25 2019/4/1
 **/

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private MaterialOrderInfoRepository materialOrderInfoRepository;
    @Autowired
    private MaterialStockInfoRepository materialStockInfoRepository;
    @Autowired
    private ProductStockInfoRepository productStockInfoRepository;
    @Autowired
    private GameTransportBasicInfoRepository transportBasicInfoRepository;

    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired
    private MaterialBasicInfoRepository materialBasicInfoRepository;

    @Autowired
    private FinanceService financeService;


    @Override
    public List<MaterialStockDisplayVo> getMaterialStockVosOfEnterprise(Long enterpriseId) {

        List<MaterialStockInfo> materialStockInfoList = materialStockInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId);

        List<MaterialStockDisplayVo> materialStockDisplayVoList = new ArrayList<>();
        for (MaterialStockInfo materialStockInfo : materialStockInfoList) {
            materialStockDisplayVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(materialStockInfo));
        }

        return materialStockDisplayVoList;
    }

    @Override
    public List<TransportMethodDisplayVo> getAllTransportVos(Long gameId) {

        List<GameTransportBasicInfo> transportBasicInfoList = transportBasicInfoRepository.findByGameBasicInfo_Id(gameId);

        List<TransportMethodDisplayVo> transportMethodDisplayVoList = new ArrayList<>();
        for (GameTransportBasicInfo transportBasicInfo : transportBasicInfoList) {
            transportMethodDisplayVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(transportBasicInfo));
        }

        return transportMethodDisplayVoList;
    }

    @Override
    public TransportMethodDisplayVo getTransportVoyId(Long transportBasicId) {

        GameTransportBasicInfo transportBasicInfo = transportBasicInfoRepository.findOne(transportBasicId);

        return EntityVoUtil.copyFieldsFromEntityToVo(transportBasicInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MaterialOrderDisplayVo> submitMaterialOrder(List<MaterialOrderDto> materialOrderDtoList) {
        List<MaterialOrderDisplayVo> materialOrderDisplayVoList = new ArrayList<>();

        for (MaterialOrderDto materialOrderDto : materialOrderDtoList) {
            MaterialOrderInfo materialOrderInfo = new MaterialOrderInfo();

            // 哪个企业
            EnterpriseBasicInfo enterpriseBasicInfo = enterpriseBasicInfoRepository.findOne(materialOrderDto.getEnterpriseId());
            materialOrderInfo.setEnterpriseBasicInfo(enterpriseBasicInfo);
            // 哪种材料
            MaterialBasicInfo materialBasicInfo = materialBasicInfoRepository.findOne(materialOrderDto.getMaterialBasicId());
            materialOrderInfo.setMaterialBasicInfo(materialBasicInfo);
            // 哪种运输方式
            materialOrderInfo.setTransportMethod(transportBasicInfoRepository.findOne(materialOrderDto.getTransportBasicId()));
            // 采购数量
            materialOrderInfo.setPurchaseNumber(materialOrderDto.getPurchaseNumber());
            // 采购时间，即是哪个周期采购的
            materialOrderInfo.setPurchaseTime(enterpriseBasicInfo.getEnterpriseCurrentPeriod());
            // 原材料运输状态（初始为待审核状态
            materialOrderInfo.setTransportStatus(TransportStatusEnum.TOCHECK);

            // 将该原料订单信息存入MaterialOrderInfo表
            materialOrderInfo = materialOrderInfoRepository.saveAndFlush(materialOrderInfo);

            materialOrderDisplayVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(materialOrderInfo));

            // 当场扣钱
            String changeOperating = FinanceOperationConstant.MATERIAL_PURCHASE;
            Double changeAmount = materialOrderDto.getPurchaseNumber()*materialBasicInfo.getMaterialPrice();
            financeService.updateFinanceInfo(enterpriseBasicInfo.getId(), changeOperating, changeAmount, true, false);
        }

        return materialOrderDisplayVoList;
    }

    @Override
    public List<MaterialOrderDisplayVo> getAllMaterialOrdersOfEnterprise(Long enterpriseId) {
        List<MaterialOrderInfo> materialOrderInfoList = materialOrderInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId);

        List<MaterialOrderDisplayVo> materialOrderDisplayVoList = new ArrayList<>();
        for (MaterialOrderInfo materialOrderInfo : materialOrderInfoList) {
            materialOrderDisplayVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(materialOrderInfo));
        }

        return materialOrderDisplayVoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaterialOrderDisplayVo updateTransportStatus(Long materialOrderId, TransportStatusEnum transportStatusEnum) {
        MaterialOrderInfo materialOrderInfo = materialOrderInfoRepository.findOne(materialOrderId);

        // 修改运输状态并进行持久化
        materialOrderInfo.setTransportStatus(transportStatusEnum);
        materialOrderInfoRepository.save(materialOrderInfo);

        return EntityVoUtil.copyFieldsFromEntityToVo(materialOrderInfo);
    }

    @Override
    public MaterialOrderDetailVo getMaterialOrderDetailVos(Long materialOrderId) {
        MaterialOrderInfo materialOrderInfo = materialOrderInfoRepository.findOne(materialOrderId);

        MaterialOrderDisplayVo materialOrderDisplayVo = EntityVoUtil.copyFieldsFromEntityToVo(materialOrderInfo);

        // 将materialOrderDisplayVo中对应的属性赋给materialOrderDetailVo
        MaterialOrderDetailVo materialOrderDetailVo = new MaterialOrderDetailVo();
        BeanCopyUtil.copyPropertiesSimple(materialOrderDisplayVo, materialOrderDetailVo);

        // 然后给materialOrderDetailVo加上总额和库存数
        materialOrderDetailVo.setTotalPrice(materialOrderDetailVo.getMaterialPrice()*materialOrderDetailVo.getPurchaseNumber());
        // 库存数
        Long enterpriseId = materialOrderInfo.getEnterpriseBasicInfo().getId();
        Long materialBasicId = materialOrderInfo.getMaterialBasicInfo().getId();
        MaterialStockInfo materialStockInfo = materialStockInfoRepository.findByEnterpriseBasicInfo_IdAndMaterialBasicInfo_Id(enterpriseId, materialBasicId);
        materialOrderDetailVo.setMaterialNumber(materialStockInfo.getMaterialNumber());

        return materialOrderDetailVo;
    }

    @Override
    public List<ProductStockDisplayVo> getProductStockVosOfEnterprise(Long enterpriseId) {
        List<ProductStockInfo> productStockInfoList = productStockInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId);

        List<ProductStockDisplayVo> productStockDisplayVoList = new ArrayList<>();
        for (ProductStockInfo productStockInfo : productStockInfoList) {
            productStockDisplayVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(productStockInfo));
        }

        return productStockDisplayVoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WebResponseVo<String> sellMaterial(Long materialStockId, Integer sellNumber) {
        MaterialStockInfo materialStockInfo = materialStockInfoRepository.findOne(materialStockId);

        if(materialStockInfo.getMaterialNumber() < sellNumber) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "原材料存储不足，请减少售出数量");
        }

        // 更新库存量并进行持久化
        materialStockInfo.setMaterialNumber(materialStockInfo.getMaterialNumber()-sellNumber);
        materialStockInfoRepository.save(materialStockInfo);

        // 当场收钱
        String changeOperating = FinanceOperationConstant.MATERIAL_SELL;
        MaterialBasicInfo materialBasicInfo = materialStockInfo.getMaterialBasicInfo();
        Double changeAmount = materialBasicInfo.getMaterialPrice()*materialBasicInfo.getSellRate()*sellNumber;
        financeService.updateFinanceInfo(materialStockInfo.getEnterpriseBasicInfo().getId(), changeOperating, changeAmount, false, false);

        return toSuccessResponseVoWithNoData();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WebResponseVo<String> sellProduct(Long productStockId, Integer sellNumber) {
        ProductStockInfo productStockInfo = productStockInfoRepository.findOne(productStockId);

        if(productStockInfo.getProductNumber() < sellNumber) {
            return toFailResponseVoWithMessage(WebResponseVo.ResponseStatus.BAD_REQUEST, "产品存储不足，请减少售出数量");
        }

        // 更新库存量并进行持久化
        productStockInfo.setProductNumber(productStockInfo.getProductNumber()-sellNumber);
        productStockInfoRepository.save(productStockInfo);

        // 当场收钱
        String changeOperating = FinanceOperationConstant.PRODUCT_SELL;
        ProductBasicInfo productBasicInfo = productStockInfo.getProductBasicInfo();
        Double changeAmount = productBasicInfo.getProductSellingPrice()*sellNumber;
        financeService.updateFinanceInfo(productStockInfo.getEnterpriseBasicInfo().getId(), changeOperating, changeAmount, false, false);

        return toSuccessResponseVoWithNoData();
    }
}
