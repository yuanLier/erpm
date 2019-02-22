package edu.cqupt.mislab.erp.game.compete.operation.product.util;

import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductStatusEnum;

public class EnumUtil {

    public static boolean isInclude(String key) {
        boolean include = false;
        for(ProductStatusEnum productStatus : ProductStatusEnum.values()) {
            if(productStatus.toString().equals(key)) {
                include = true;
                break;
            }
        }
        return include;
    }

}
