package edu.cqupt.mislab.erp.game.compete.operation.finance.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-07-11 15:59
 * @description
 */
@Data
@ApiModel("企业贷款情况传输数据")
public class LoanEnterpriseDto {

    @ApiModelProperty("贷款类型的id")
    private Long loanBasicId;

    @ApiModelProperty("哪个企业")
    private Long enterpriseId;

    @ApiModelProperty("贷款数量")
    private Double loanAmount;
}
