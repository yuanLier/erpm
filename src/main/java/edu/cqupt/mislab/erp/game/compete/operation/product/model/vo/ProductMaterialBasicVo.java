package edu.cqupt.mislab.erp.game.compete.operation.product.model.vo;

import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.MaterialBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @description 
 **/

@Data
@ApiModel("（管理员端修改）产品原料构成视图")
public class ProductMaterialBasicVo {

    @ApiModelProperty(value = "新生成的产品id")
    private Long id;

    @ApiModelProperty(value = "哪一个产品")
    private ProductBasicInfo productBasicInfo;

    @ApiModelProperty(value = "哪一种原料")
    private MaterialBasicInfo materialBasicInfo;

    @ApiModelProperty(value = "需要该种原料多少种")
    private Integer number;

    @ApiModelProperty(value = "是否启用")
    private boolean enable;
}
