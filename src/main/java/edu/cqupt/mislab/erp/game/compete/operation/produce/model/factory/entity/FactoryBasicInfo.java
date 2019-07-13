package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity;

import edu.cqupt.mislab.erp.commons.validators.annotations.DoubleMin;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author yuanyiwen
 * @create 2019-06-05 21:17
 * @description 厂房基本信息
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class FactoryBasicInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "厂房类型")
    private String factoryType;

    @Min(1)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "建造这个厂房需要的周期")
    private int factoryMakePeriod;

    @DoubleMin(0.01)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "在建造过程中，每个周期需要支付的建造费用")
    private double factoryMakeCost;

    @DoubleMin(0.01)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "厂房建造后，每期折旧的价值。完工当期不折旧")
    private double factoryDepreciation;

    @DoubleMin(0.01)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "不考虑折旧时，厂房售卖价值")
    private double factoryValue;

    @DoubleMin(0.01)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "残值，即折旧到一定阶段后，无论怎么再折旧，都不再减少的价值")
    private double factoryStumpCost;

    @Min(1)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "厂房可以容纳的生产线数量")
    private int factoryCapacity;

    @DoubleMin(0.01)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "租用该厂房时，每期需要交纳的租金")
    private double factoryRentCost;

    @Min(0)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "出售厂房后延迟多少个账期买房资金到账")
    private int factoryDelayTime;

    @DoubleMin(0.01)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "厂房建好后每期的维护费用，完工当期不支付维护费用")
    private double factoryMaintainCost;

    @Basic(optional = false)
    @Comment(comment = "该条设置是否启用")
    private boolean enable;
}
