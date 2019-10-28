package edu.cqupt.mislab.erp.game.manage.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-07-31 11:40
 * @description
 */
@Data
@ApiModel("企业当前所处年份与周期展示视图")
public class EnterprisePeriodVo {

    @ApiModelProperty("哪一年")
    Integer year;

    @ApiModelProperty("这一年的哪个周期")
    Integer period;
}
