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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//代理主键

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    private EnterpriseBasicInfo enterpriseBasicInfo;//哪一个企业的产品研发信息

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    private ProductBasicInfo productBasicInfo;//产品研发的基本信息

    @Column(columnDefinition = "null",updatable = false)
    private Integer developBeginPeriod;//产品开始研发的周期

    @Column(columnDefinition = "null",updatable = false)
    private Integer developEndPeriod;//产品完成研发的周期

    @Column(nullable = false, columnDefinition = "0",updatable = false)
    private Integer researchedPeriod;//已经产品研发了多少个周期

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "UNDEVELOP",updatable = false)
    private ProductStatusEnum productStatus;//当前产品的研发状态

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;

        if(o == null||getClass() != o.getClass())
            return false;

        ProductDevelopInfo that = (ProductDevelopInfo) o;

        return new EqualsBuilder().append(id,that.id).isEquals();
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17,37).append(id).toHashCode();
    }
}
