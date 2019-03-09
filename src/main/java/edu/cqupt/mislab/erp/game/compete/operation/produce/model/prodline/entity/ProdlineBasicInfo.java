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
    private String ProdlineType;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线每个安装周期的金额")
    private Double product_line_setup_period_price;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线的安装周期")
    private Integer product_line_setup_period;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线的转产需要的周期")
    private Integer product_line_change_period;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线的转产每期需要的费用")
    private Double product_line_change_cost;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线每期的维修费用")
    private Double product_line_main_cost;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线的残值。即折旧到一定阶段后，无论怎么再折旧，都不再减少的价值")
    private Double product_line_stumpcost;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "生产线投入使用后，每期折旧的价值。完工当期不折旧")
    private Double product_line_depreciation;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "卖掉生产线后，卖生产线的钱需要延长几个账期到账")
    private Integer product_line_sale_delay_time;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "该生产线对产品每期生产价格的影响情况")
    private Double extraValue;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "该生产线对产品生产周期的影响情况")
    private Integer extraPeriod;

    @Basic(optional = false)
    @Comment(comment = "该条设置是否启用")
    private boolean enable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdlineBasicInfo that = (ProdlineBasicInfo) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(ProdlineType, that.ProdlineType) &&
                Objects.equals(product_line_setup_period_price, that.product_line_setup_period_price) &&
                Objects.equals(product_line_setup_period, that.product_line_setup_period) &&
                Objects.equals(product_line_change_period, that.product_line_change_period) &&
                Objects.equals(product_line_change_cost, that.product_line_change_cost) &&
                Objects.equals(product_line_main_cost, that.product_line_main_cost) &&
                Objects.equals(product_line_stumpcost, that.product_line_stumpcost) &&
                Objects.equals(product_line_depreciation, that.product_line_depreciation) &&
                Objects.equals(product_line_sale_delay_time, that.product_line_sale_delay_time) &&
                Objects.equals(extraValue, that.extraValue) &&
                Objects.equals(extraPeriod, that.extraPeriod) &&
                Objects.equals(enable, that.enable);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, ProdlineType, product_line_setup_period_price, product_line_setup_period, product_line_change_period, product_line_change_cost, product_line_main_cost, product_line_stumpcost, product_line_depreciation, product_line_sale_delay_time, extraValue, extraPeriod, enable);
    }
}
