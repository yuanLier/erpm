package edu.cqupt.mislab.erp.game.compete.operation.finance.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-07-13 17:31
 * @description
 */
@Data
@ApiModel("贷款信息筛选传输数据")
public class LoanSelectDto {

    @ApiModelProperty("贷款类型所对应的id")
    private Long loanBasicId;

    @ApiModelProperty("是否还款")
    private Boolean repaid;
}
