package edu.cqupt.mislab.erp.game.compete.operation.product.model.vo;

import edu.cqupt.mislab.erp.game.compete.operation.material.model.vo.MaterialTypeVo;
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

    @ApiModelProperty(value = "新生成的产品原料id")
    private Long id;

    @ApiModelProperty(value = "哪一个产品")
    private ProductBasicVo productBasicVo;

    @ApiModelProperty(value = "哪一种原料")
    private MaterialTypeVo materialTypeVo;

    @ApiModelProperty(value = "需要该种原料多少种")
    private Integer number;

    @ApiModelProperty(value = "是否启用")
    private boolean enable;
}
