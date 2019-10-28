package edu.cqupt.mislab.erp.game.compete.operation.finance.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-07-11 17:07
 * @description
 */
@Data
@ApiModel("贷款基本信息视图")
public class LoanBasicVo {

    @ApiModelProperty("代理主键")
    private Long loanBasicId;

    @ApiModelProperty("贷款类型")
    private String loanType;

    @ApiModelProperty("年利率")
    private double loanRate;

    @ApiModelProperty("贷款期限，即最长允许在多少周期后还款")
    private int maxDuration;

    @ApiModelProperty("该条设置是否被启用")
    private boolean enable;

}
