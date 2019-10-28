package edu.cqupt.mislab.erp.game.compete.operation.finance.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-05-22 8:58
 * @description
 */
@Data
@ApiModel("企业财务记录视图")
public class FinanceEnterpriseVo {

    @ApiModelProperty("代理主键")
    private Long id;

    @ApiModelProperty("哪个企业的账户余额")
    private Long enterpriseId;

    @ApiModelProperty("当前账户余额")
    private double currentAccount;

    @ApiModelProperty("造成改变的操作")
    private String changeOperating;

    @ApiModelProperty("造成改变的金额")
    private double changeAmount;

    @ApiModelProperty("是否为当前（最新）余额")
    private boolean current;
}
