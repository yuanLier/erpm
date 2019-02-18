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
public class ProductDevelopedInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//代理主键

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    private EnterpriseBasicInfo enterpriseBasicInfo;//哪一个企业的产品研发信息

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    private ProductBasicInfo productBasicInfo;//产品研发的基本信息

    @Column(nullable = false,updatable = false)
    private Integer developBeginPeriod;//产品研发开始的周期

    @Column(nullable = false,updatable = false)
    private Integer developEndPeriod;//产品研发完成的周期

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;

        if(o == null||getClass() != o.getClass())
            return false;

        ProductDevelopedInfo that = (ProductDevelopedInfo) o;

        return new EqualsBuilder().append(id,that.id).isEquals();
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17,37).append(id).toHashCode();
    }
}
