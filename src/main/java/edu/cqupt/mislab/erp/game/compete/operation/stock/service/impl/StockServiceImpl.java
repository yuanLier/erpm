package edu.cqupt.mislab.erp.game.compete.operation.stock.service.impl;

import edu.cqupt.mislab.erp.game.compete.operation.stock.model.dto.MaterialOrderDto;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.TransportStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo.*;
import edu.cqupt.mislab.erp.game.compete.operation.stock.service.StockService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    @Override
    public List<MaterialStockDisplayVo> getMaterialStockVosOfEnterprise(Long enterpriseId) {
        return null;
    }

    @Override
    public List<TransportMethodDisplayVo> getAllTransportVos() {
        return null;
    }

    @Override
    public TransportMethodDisplayVo getTransportVoyId(Long transportBasicId) {
        return null;
    }

    @Override
    public List<MaterialOrderDisplayVo> submitMaterialOrder(List<MaterialOrderDto> materialOrderDtoList) {
        return null;
    }

    @Override
    public MaterialOrderDisplayVo updateTransportStatus(TransportStatusEnum transportStatusEnum) {
        return null;
    }

    @Override
    public List<MaterialOrderDetailVo> getMaterialOrderDetailVos(Long materialOrderId) {
        return null;
    }

    @Override
    public List<ProductStockDisplayVo> getProductStockVosOfEnterprise(Long enterpriseId) {
        return null;
    }

    @Override
    public String sellMaterial(Long materialStockId) {
        return null;
    }

    @Override
    public String sellProduct(Long productStockId) {
        return null;
    }
}
