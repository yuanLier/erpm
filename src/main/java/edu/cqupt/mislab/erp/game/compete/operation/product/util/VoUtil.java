package edu.cqupt.mislab.erp.game.compete.operation.product.util;

import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductMaterialBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductMaterialVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoUtil {
    public static ProductDisplayVo castEntityToVo(ProductDevelopInfo productDevelopInfo) {

        ProductDisplayVo productDisplayVo = new ProductDisplayVo();

        productDisplayVo.setId(productDevelopInfo.getId());

        // 产品名称
        productDisplayVo.setProductName(productDevelopInfo.getProductBasicInfo().getProductName());
        // 每期研发费用
        productDisplayVo.setProductResearchCost(productDevelopInfo.getProductBasicInfo().getProductResearchCost());
        // 研发总期数
        productDisplayVo.setProductResearchPeriod(productDevelopInfo.getProductBasicInfo().getProductResearchPeriod());

        // 当前研发状态
        productDisplayVo.setProductStatus(productDevelopInfo.getProductStatus());
        // 已研发的周期数
        productDisplayVo.setResearchedPeriod(productDevelopInfo.getResearchedPeriod());

        return productDisplayVo;
    }
}
