package edu.cqupt.mislab.erp.game.compete.operation.product.model.dto;

import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.material.dao.MaterialBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductBasicInfoRepository;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * @author chuyunfei
 * @description 
 **/

@Data
@ApiModel("产品原料构成视图")
public class ProductMaterialBasicDto {

    @Min(1L)
    @Exist(repository = ProductBasicInfoRepository.class)
    @ApiModelProperty(value = "哪一个产品",required = true)
    private Long productBasicInfoId;

    @Min(1L)
    @Exist(repository = MaterialBasicInfoRepository.class)
    @ApiModelProperty(value = "哪一种原料",required = true)
    private Long materialBasicInfoId;

    @Min(1)
    @ApiModelProperty(value = "需要该种原料多少种",required = true)
    private Integer number;
}
