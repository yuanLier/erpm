package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author yuanyiwen
 * @create 2019-03-08 17:14
 * @description 生产线基本信息表
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class ProdlineBasicInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线类型")
    private String prodlineType;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线每个安装周期的金额")
    private Double prodlineSetupPeriodPrice;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线的安装周期")
    private Integer prodlineSetupPeriod;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线的转产需要的周期")
    private Integer prodlineChangePeriod;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线的转产每期需要的费用")
    private Double prodlineChangeCost;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线每期的维修费用")
    private Double prodlineMainCost;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线的残值。即折旧到一定阶段后，无论怎么再折旧，都不再减少的价值")
    private Double prodlineStumpcost;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线投入使用后，每期折旧的价值。完工当期不折旧")
    private Double prodlineDepreciation;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "卖掉生产线后，卖生产线的钱需要延长几个账期到账")
    private Integer prodlineSaleDelayTime;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "该生产线对产品每期生产价格的影响情况")
    private Double extraValue;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "该生产线对产品生产周期的影响情况")
    private Double extraPeriod;

    @Basic(optional = false)
    @Comment(comment = "该条设置是否启用")
    private boolean enable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdlineBasicInfo that = (ProdlineBasicInfo) o;
        return enable == that.enable &&
                Objects.equals(id, that.id) &&
                Objects.equals(prodlineType, that.prodlineType) &&
                Objects.equals(prodlineSetupPeriodPrice, that.prodlineSetupPeriodPrice) &&
                Objects.equals(prodlineSetupPeriod, that.prodlineSetupPeriod) &&
                Objects.equals(prodlineChangePeriod, that.prodlineChangePeriod) &&
                Objects.equals(prodlineChangeCost, that.prodlineChangeCost) &&
                Objects.equals(prodlineMainCost, that.prodlineMainCost) &&
                Objects.equals(prodlineStumpcost, that.prodlineStumpcost) &&
                Objects.equals(prodlineDepreciation, that.prodlineDepreciation) &&
                Objects.equals(prodlineSaleDelayTime, that.prodlineSaleDelayTime) &&
                Objects.equals(extraValue, that.extraValue) &&
                Objects.equals(extraPeriod, that.extraPeriod);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, prodlineType, prodlineSetupPeriodPrice, prodlineSetupPeriod, prodlineChangePeriod, prodlineChangeCost, prodlineMainCost, prodlineStumpcost, prodlineDepreciation, prodlineSaleDelayTime, extraValue, extraPeriod, enable);
    }
}
