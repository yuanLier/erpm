package edu.cqupt.mislab.erp.game.compete.operation.stock.model.dto;

import edu.cqupt.mislab.erp.commons.validators.annotations.Exist;
import edu.cqupt.mislab.erp.game.compete.operation.material.dao.MaterialBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author yuanyiwen
 * @create 2019-03-31 22:02
 * @description
 */
@Data
@ApiModel("材料订单基本数据视图")
public class MaterialOrderDto {

    @Exist(repository = MaterialBasicInfoRepository.class)
    @ApiModelProperty(value = "原材料id",required = true)
    private Long materialBasicId;

    @Exist(repository = EnterpriseBasicInfoRepository.class)
    @ApiModelProperty(value = "企业id",required = true)
    private Long enterpriseId;

    @Min(1)
    @NotNull
    @ApiModelProperty(value = "该材料对应的采购数量",required = true)
    private Integer purchaseNumber;

    @NotNull
    @ApiModelProperty(value = "运输方式的id",required = true)
    private Long transportBasicId;


}
