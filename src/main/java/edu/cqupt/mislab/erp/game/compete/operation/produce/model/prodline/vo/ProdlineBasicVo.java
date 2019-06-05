package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-06-04 21:51
 * @description
 */
@Data
@ApiModel("生产线基本数据视图")
public class ProdlineBasicVo {

    @ApiModelProperty(value = "代理主键")
    private Long id;

    @ApiModelProperty(value = "生产线类型")
    private String prodlineType;

    @ApiModelProperty(value = "生产线每个安装周期的金额")
    private double prodlineSetupPeriodPrice;

    @ApiModelProperty(value = "生产线的安装周期")
    private int prodlineSetupPeriod;

    @ApiModelProperty(value = "生产线的转产需要的周期")
    private int prodlineChangePeriod;

    @ApiModelProperty(value = "生产线的转产每期需要的费用")
    private double prodlineChangeCost;

    @ApiModelProperty(value = "生产线每期的维修费用")
    private double prodlineMainCost;

    @ApiModelProperty(value = "生产线建造后，每期折旧的价值。完工当期不折旧")
    private double prodlineDepreciation;

    @ApiModelProperty(value = "生产线的残值。即折旧到一定阶段后，无论怎么再折旧，都不再减少的价值")
    private double prodlineStumpcost;

    @ApiModelProperty(value = "不考虑残值时，生产线售卖价值")
    private double prodlineValue;

    @ApiModelProperty(value = "该生产线对产品每期生产价格的影响情况")
    private double extraValue;

    @ApiModelProperty(value = "该生产线对产品生产周期的影响情况")
    private double extraPeriod;

    @ApiModelProperty(value = "该条设置是否启用")
    private boolean enable;
}
