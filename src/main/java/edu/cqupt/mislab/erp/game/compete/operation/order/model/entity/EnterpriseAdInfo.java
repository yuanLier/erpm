package edu.cqupt.mislab.erp.game.compete.operation.order.model.entity;

import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
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
public class EnterpriseAdInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//代理主键

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private EnterpriseBasicInfo enterpriseBasicInfo;//哪一个企业

    @ManyToOne
    @JoinColumn(nullable = false,updatable = false)
    private ProductBasicInfo productBasicInfo;//哪一个产品

    @ManyToOne
    @JoinColumn(nullable = false,updatable = false)
    private MarketBasicInfo marketBasicInfo;//哪一个市场

    @Basic(optional = false)
    private Integer year;//那一年投的广告费

    @Basic(optional = false)
    private Double money;//多少钱

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;//哪一个时间投的

    @Basic(optional = false)
    private Integer frequency;//该订单已经选择已经多少轮

    @Basic(optional = false)
    private Boolean finished;//该订单是否退出排序

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;

        if(o == null||getClass() != o.getClass())
            return false;

        EnterpriseAdInfo that = (EnterpriseAdInfo) o;

        return new EqualsBuilder().append(id,that.id).isEquals();
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17,37).append(id).toHashCode();
    }
}
