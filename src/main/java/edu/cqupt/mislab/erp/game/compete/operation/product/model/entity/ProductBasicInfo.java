package edu.cqupt.mislab.erp.game.compete.operation.product.model.entity;

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
public class ProductBasicInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//代理主键

    @Column(unique = true,nullable = false,updatable = false)
    private String productName;//产品的名称

    @Column(nullable = false,updatable = false)
    private Integer productResearchPeriod;//产品研发的周期数

    @Column(nullable = false,updatable = false)
    private Double productResearchCost;//在产品研发过程中，每个周期需要支付的费用

    @Column(nullable = false,updatable = false)
    private Integer produceProductPeriod;//生产该产品所需的基本周期数

    @Column(nullable = false,updatable = false)
    private Double produceProductCost;//在产品生产过程中，每个周期需要支付的费用

    @Column(nullable = false,updatable = false)
    private Double productSellingPrice;//产品的基本售价

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;

        if(o == null||getClass() != o.getClass())
            return false;

        ProductBasicInfo that = (ProductBasicInfo) o;

        return new EqualsBuilder().append(id,that.id).isEquals();
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17,37).append(id).toHashCode();
    }
}
