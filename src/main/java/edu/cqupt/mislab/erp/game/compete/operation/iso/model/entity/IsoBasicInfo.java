package edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class IsoBasicInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//代理主键

    @Column(nullable = false, updatable = false)
    private String isoName;//ISO认证资格的名称

    @Column(nullable = false, updatable = false)
    private Integer isoResearchPeriod;//完成ISO认证所需的周期数

    @Column(nullable = false, updatable = false)
    private Double isoResearchCost;//在开拓ISO认证过程中，每个周期需要支付的费用

    @Column(nullable = false, updatable = false)
    private Double isoMaintainCost;//ISO认证完成后，维持该认证每个周期需要支付的费用

    @Column(nullable = false, updatable = false)
    private Double extraValue;//该认证对订单价格的影响程度，每一个产品价格影响

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IsoBasicInfo that = (IsoBasicInfo) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(isoName, that.isoName) &&
                Objects.equals(isoResearchPeriod, that.isoResearchPeriod) &&
                Objects.equals(isoResearchCost, that.isoResearchCost) &&
                Objects.equals(isoMaintainCost, that.isoMaintainCost) &&
                Objects.equals(extraValue, that.extraValue);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, isoName, isoResearchPeriod, isoResearchCost, isoMaintainCost, extraValue);
    }
}