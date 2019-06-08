package edu.cqupt.mislab.erp.game.compete.operation.product.model.entity;

import edu.cqupt.mislab.erp.commons.validators.annotations.DoubleMin;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: chuyunfei
 * @Date: 2019/3/4 21:52
 * @Description: 产品的基本数据信息表，若该表不存在可用数据，将无法进行比赛的初始化操作
 **/

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
    @Comment(comment = "代理主键")
    private Long id;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "产品的名称,所有相同产品名称数据中，enable=false标识历史数据，反之为最新数据，每一个name最多一个enable=true")
    private String productName;

    @Min(1)
    @Column(nullable = false,updatable = false)
    @Comment(comment = "产品研发的周期数，该值大于0")
    private int productResearchPeriod;

    @DoubleMin(0.01)
    @Column(nullable = false,updatable = false,columnDefinition = "double(10,2) default 1.00")
    @Comment(comment = "在产品研发过程中，每个周期需要支付的费用，该值大于0")
    private double productResearchCost;

    @Min(1)
    @Comment(comment = "生产x件该产品所需的基本周期数")
    private int produceProductPeriod;

    @Min(1)
    @Comment(comment = "这就是那个x")
    private int produceProductAmount;

    @DoubleMin(0.01)
    @Comment(comment = "在产品生产过程中，每个周期需要支付的费用")
    private double produceProductCost;

    @DoubleMin(0.01)
    @Comment(comment = "产品的基本售价")
    private double productSellingPrice;

    @Range(min = 0, max = 100)
    @Column(updatable = false)
    @Comment(comment = "市场需求量,该值大于0，百分制")
    private int mount;

    @Comment(comment = "市场之间的单价差异,该值大于0")
    private double priceDifference;

    @Comment(comment = "市场之间的需求量差异,该值大于0")
    private int mountDifference;

    @Column(columnDefinition = "double(10,2) default 0.00")
    @Comment(comment = "价格波动比例,该值大于0")
    private double priceFloat;

    @Column(columnDefinition = "double(10,2) default 0.00")
    @Comment(comment = "需求量波动比例,该值大于0")
    private double mountFloat;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,updatable = false)
    @Comment(comment = "默认的产品开发状态，该产品的默认研发状态，只能是未研发和已研发")
    private ProductDevelopStatusEnum productDevelopStatus;

    @Basic(optional = false)
    @Comment(comment = "该数据是否被启用，当前最新数据是启用，所有的历史数据均为未启用，必须保证同一个材料信息最多只有一个Enable = true")
    private boolean enable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductBasicInfo that = (ProductBasicInfo) o;
        return productResearchPeriod == that.productResearchPeriod &&
                Double.compare(that.productResearchCost, productResearchCost) == 0 &&
                mount == that.mount &&
                enable == that.enable &&
                java.util.Objects.equals(id, that.id) &&
                java.util.Objects.equals(productName, that.productName) &&
                java.util.Objects.equals(produceProductPeriod, that.produceProductPeriod) &&
                java.util.Objects.equals(produceProductCost, that.produceProductCost) &&
                java.util.Objects.equals(productSellingPrice, that.productSellingPrice) &&
                java.util.Objects.equals(priceDifference, that.priceDifference) &&
                java.util.Objects.equals(mountDifference, that.mountDifference) &&
                java.util.Objects.equals(priceFloat, that.priceFloat) &&
                java.util.Objects.equals(mountFloat, that.mountFloat) &&
                productDevelopStatus == that.productDevelopStatus;
    }

    @Override
    public int hashCode() {

        return java.util.Objects.hash(id, productName, productResearchPeriod, productResearchCost, produceProductPeriod, produceProductCost, productSellingPrice, mount, priceDifference, mountDifference, priceFloat, mountFloat, productDevelopStatus, enable);
    }
}
