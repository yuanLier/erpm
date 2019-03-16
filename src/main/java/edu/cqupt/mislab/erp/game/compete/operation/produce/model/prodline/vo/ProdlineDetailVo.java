package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuanyiwen
 * @create 2019-03-12 18:14
 * @description
 */
@Data
@ApiModel("生产线详情展示视图")
public class ProdlineDetailVo {

    @ApiModelProperty("代理主键，值同ProdlineProduceInfo")
    private Long id;

    @ApiModelProperty("展示为生产线名称，实际指生产线类型")
    private String prodlineType;

    @ApiModelProperty("展示为生产线当前生产的产品名称，实际指产品类型")
    private String productType;

    @ApiModelProperty("生产线安装周期")
    private Integer prodlineSetupPeriod;

    @ApiModelProperty("每期安装费用")
    private Double prodlineSetupPeriodPrice;

    @ApiModelProperty("生产线的转产需要的周期")
    private Integer prodlineChangePeriod;

    @ApiModelProperty("生产线的转产每期需要的费用")
    private Double prodlineChangeCost;

    @ApiModelProperty("该生产线对产品生产周期的影响情况")
    private Integer extraPeriod;

    @ApiModelProperty("生产线每期的维修费用")
    private Double prodlineMainCost;

    @ApiModelProperty("生产线投入使用后，每期折旧的价值。完工当期不折旧")
    private Double prodlineDepreciation;

    @ApiModelProperty("生产线的残值。即折旧到一定阶段后，无论怎么再折旧，都不再减少的价值")
    private Double prodlineStumpcost;
}
