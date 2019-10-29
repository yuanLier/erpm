package edu.cqupt.mislab.erp.game.compete.operation.finance.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-07-11 16:57
 * @description
 */
@Data
@ApiModel("贷款基本信息展示视图")
public class LoanBasicDisplayVo {

    @ApiModelProperty("值同LoanBasicId")
    private Long id;

    @ApiModelProperty(value = "贷款类型")
    private String loanType;

    @ApiModelProperty(value = "年利率")
    private double loanRate;

    @ApiModelProperty(value = "超过最后还款期限仍未还款时，需要按比例支付赔偿金；这是那个比例")
    private double penaltyRate;

    @ApiModelProperty(value = "贷款期限，即最长允许在多少周期后还款")
    private int maxDuration;
}
