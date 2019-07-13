package edu.cqupt.mislab.erp.game.compete.operation.finance.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author yuanyiwen
 * @create 2019-07-13 19:45
 * @description
 */
@Data
@ApiModel("贷款基本信息传输数据")
public class LoanBasicDto {

    @ApiModelProperty("贷款类型")
    private String loanType;

    @ApiModelProperty("年利率")
    private double loanRate;

    @ApiModelProperty("贷款期限，即最长允许在多少周期后还款")
    private int maxDuration;
}
