package edu.cqupt.mislab.erp.game.compete.operation.product.model.entity;

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
public class ProductDevelopInfo implements Serializable {

    /**
     * 用于记录每一个企业的产品研发信息
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//代理主键

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    private EnterpriseBasicInfo enterpriseBasicInfo;//哪一个企业的产品研发信息

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    private ProductBasicInfo productBasicInfo;//产品研发的基本信息

    @Basic(optional = false)
    private Integer beginPeriod;//开始研发的周期数

    @Basic(optional = false)
    private Integer endPeriod;//研发完毕的周期数

    @Basic(optional = false)
    private Integer developedPeriod;//已经研发了多少个周期数

    @Enumerated(EnumType.STRING)
    private ProductDevelopStatus productDevelopStatus;//研发的状态

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;

        if(o == null||getClass() != o.getClass())
            return false;

        ProductDevelopInfo that = (ProductDevelopInfo) o;

        return new EqualsBuilder().append(id,that.id).append(beginPeriod,that.beginPeriod).append(endPeriod,that.endPeriod).append(developedPeriod,that.developedPeriod).append(productDevelopStatus,that.productDevelopStatus).isEquals();
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17,37).append(id).append(beginPeriod).append(endPeriod).append(developedPeriod).append(productDevelopStatus).toHashCode();
    }
}
