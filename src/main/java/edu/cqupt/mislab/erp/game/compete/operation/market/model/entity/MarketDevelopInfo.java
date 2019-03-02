package edu.cqupt.mislab.erp.game.compete.operation.market.model.entity;

import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class MarketDevelopInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//代理主键

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    private MarketBasicInfo marketBasicInfo;//市场开发的基本信息

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    private EnterpriseBasicInfo enterpriseBasicInfo;//哪一个企业的市场开发信息

    @Basic
    private Integer developBeginPeriod ;//市场开始开发的周期

    @Basic
    private Integer developEndPeriod ;//市场完成开发的周期

    @Basic
    private Integer researchedPeriod ;//已经市场开发了多少个周期

    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    private MarketStatusEnum marketStatus;//当前市场的开发状态（默认同iso基本认证中的初始认证状态，需要手动控制）

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;

        if(o == null||getClass() != o.getClass())
            return false;

        MarketDevelopInfo that = (MarketDevelopInfo) o;

        return new EqualsBuilder().append(id,that.id).isEquals();
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17,37).append(id).toHashCode();
    }
}
