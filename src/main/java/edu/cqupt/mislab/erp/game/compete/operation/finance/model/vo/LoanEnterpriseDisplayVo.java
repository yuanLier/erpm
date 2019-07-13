package edu.cqupt.mislab.erp.game.compete.operation.finance.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-07-11 16:22
 * @description
 */
@Data
@ApiModel("企业贷款情况展示视图")
public class LoanEnterpriseDisplayVo {

    @ApiModelProperty("贷款单id")
    private Long loanId;

    @ApiModelProperty("贷款单编号")
    private String loanNumber;

    @ApiModelProperty("贷款金额")
    private double loanAmount;

    @ApiModelProperty("贷款时间")
    private int beginPeriod;

    @ApiModelProperty("还款时间")
    private Integer endPeriod;

    @ApiModelProperty("是否还款")
    private boolean repaid;
}
