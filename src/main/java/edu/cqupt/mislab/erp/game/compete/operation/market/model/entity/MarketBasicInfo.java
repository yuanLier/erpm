package edu.cqupt.mislab.erp.game.compete.operation.market.model.entity;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class MarketBasicInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//代理主键

    @Column(unique = true,nullable = false,updatable = false)
    private String marketName;//市场的名称

    @Column(nullable = false, updatable = false)
    private Integer marketResearchPeriod;//完成市场开发的周期数

    @Column(nullable = false, updatable = false)
    private Double marketResearchCost;//在市场开发过程中，每个周期需要支付的费用

    @Column(nullable = false, updatable = false)
    private Double marketMaintainCost;//市场开发完成后，维持该市场每个周期需要支付的费用

    @Column(nullable = false, updatable = false)
    private MarketStatusEnum marketStatus;//该认证的默认初始状态

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;

        if(o == null||getClass() != o.getClass())
            return false;

        MarketBasicInfo that = (MarketBasicInfo) o;

        return new EqualsBuilder().append(id,that.id).isEquals();
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17,37).append(id).toHashCode();
    }
}
