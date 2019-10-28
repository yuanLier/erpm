package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-04-08 9:12
 * @description
 */
@Data
@ApiModel("新建生产线 选择视图")
public class ProdlineDevelopVo {

    @ApiModelProperty("代理主键，在生产计划中值同ProdlineHoldingInfo，在厂房管理时值同ProdlineBasicInfo")
    private Long id;

    @ApiModelProperty("生产线类型")
    private String prodlineType;

    @ApiModelProperty("生产线每个安装周期的金额")
    private Double prodlineSetupPeriodPrice;

    @ApiModelProperty("生产线的安装周期")
    private Integer prodlineSetupPeriod;

    @ApiModelProperty("生产线安装完成后每期的维修费用")
    private Double prodlineMainCost;

    @ApiModelProperty("生产线的残值。即折旧到一定阶段后，无论怎么再折旧，都不再减少的价值")
    private Double prodlineStumpcost;

    @ApiModelProperty("生产线投入使用后，每期折旧的价值。完工当期不折旧")
    private Double prodlineDepreciation;

    @ApiModelProperty("该生产线对产品每期生产价格的影响情况")
    private Double extraValue;

    @ApiModelProperty("该生产线对产品生产周期的影响情况")
    private Double extraPeriod;
}
