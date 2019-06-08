package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.dto;

import edu.cqupt.mislab.erp.commons.validators.annotations.DoubleMin;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author yuanyiwen
 * @create 2019-06-05 21:50
 * @description
 */
@Data
@ApiModel("生产线基本数据传输对象")
public class ProdlineBasicDto {

    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线类型")
    private String prodlineType;

    @DoubleMin(0.01)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线每个安装周期的金额")
    private double prodlineSetupPeriodPrice;

    @Min(1)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线的安装周期")
    private int prodlineSetupPeriod;

    @Min(1)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线的转产需要的周期")
    private int prodlineChangePeriod;

    @DoubleMin(0.01)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线的转产每期需要的费用")
    private double prodlineChangeCost;

    @DoubleMin(0.01)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线每期的维修费用")
    private double prodlineMainCost;

    @DoubleMin(0.01)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线建造后，每期折旧的价值。完工当期不折旧")
    private double prodlineDepreciation;

    @DoubleMin(0.01)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线的残值。即折旧到一定阶段后，无论怎么再折旧，都不再减少的价值")
    private double prodlineStumpcost;

    @DoubleMin(0.01)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "不考虑残值时，生产线售卖价值")
    private double prodlineValue;

    @DoubleMin(0.01)
    @Max(1)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "该生产线对产品每期生产价格的影响情况，取值在0-1之间")
    private double extraValue;

    @DoubleMin(0.01)
    @Max(1)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "该生产线对产品生产周期的影响情况，取值在0-1之间")
    private double extraPeriod;
}
