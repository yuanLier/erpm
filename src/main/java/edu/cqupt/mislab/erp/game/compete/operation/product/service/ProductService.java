package edu.cqupt.mislab.erp.game.compete.operation.product.service;

import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatus;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductEnterpriseBasicVo;

import java.util.List;

public interface ProductService {

    //获取企业某个状态的全部产品研发信息
    List<ProductEnterpriseBasicVo> getProductDevelopInfoOfStatus(Long enterpriseId,ProductDevelopStatus status);
}
