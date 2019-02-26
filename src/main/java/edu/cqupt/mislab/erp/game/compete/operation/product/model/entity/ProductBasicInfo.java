package edu.cqupt.mislab.erp.game.compete.operation.product.model.entity;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class ProductBasicInfo implements Serializable {

    /**
     * 产品的基本数据信息表
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//代理主键

    @Column(nullable = false,updatable = false)
    private String productName;//产品的名称

    @Column(nullable = false,updatable = false)
    private Integer productResearchPeriod;//产品研发的周期数

    @Column(nullable = false,updatable = false)
    private Double productResearchCost;//在产品研发过程中，每个周期需要支付的费用

    @Column(nullable = false,updatable = false)
    private Double price;//产品价格

    @Column(nullable = false,updatable = false)
    private Integer mount;//市场需求量

    @Column(nullable = false,updatable = false)
    private Double priceDifference;//市场之间的单价差异

    @Column(nullable = false,updatable = false)
    private Integer mountDifference;//市场之间的需求量差异

    @Column(nullable = false,updatable = false)
    private Double priceFloat;//价格波动比例

    @Column(nullable = false,updatable = false)
    private Double mountFloat;//需求量波动比例

    @ElementCollection
    @CollectionTable(name="productMaterialInfo")
    @MapKeyJoinColumn(name="materialId")
    @Column(name = "number")
    private Map<MaterialBasicInfo,Integer> materialBasicInfoIntegerMap = new HashMap<>();//产品每一种材料的数量

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductDevelopStatus productDevelopStatus = ProductDevelopStatus.TODEVELOP;//默认的产品开发状态

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false,updatable = false)
    private Date timeStamp = new Date();//时间戳

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;

        if(o == null||getClass() != o.getClass())
            return false;

        ProductBasicInfo that = (ProductBasicInfo) o;

        return new EqualsBuilder().append(id,that.id).append(productName,that.productName).append(productResearchPeriod,that.productResearchPeriod).append(productResearchCost,that.productResearchCost).append(price,that.price).append(mount,that.mount).append(priceDifference,that.priceDifference).append(mountDifference,that.mountDifference).append(priceFloat,that.priceFloat).append(mountFloat,that.mountFloat).append(timeStamp,that.timeStamp).isEquals();
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17,37).append(id).append(productName).append(productResearchPeriod).append(productResearchCost).append(price).append(mount).append(priceDifference).append(mountDifference).append(priceFloat).append(mountFloat).append(timeStamp).toHashCode();
    }
}
