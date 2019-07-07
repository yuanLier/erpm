package edu.cqupt.mislab.erp.game.compete.operation.order.model.entity;

import edu.cqupt.mislab.erp.commons.validators.annotations.DoubleMin;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @Author: chuyunfei
 * @Date: 2019/3/5 13:02
 * @Description: 企业广告信息数据表
 **/

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
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(updatable = false,nullable = false)
    @Comment(comment = "哪一个企业")
    private EnterpriseBasicInfo enterpriseBasicInfo;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个产品")
    private ProductBasicInfo productBasicInfo;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个市场")
    private MarketBasicInfo marketBasicInfo;

    @Min(1L)
    @Column(nullable = false,updatable = false)
    @Comment(comment = "哪一年投的广告费")
    private Integer year;

    @DoubleMin(0.01D)
    @Column(nullable = false,updatable = false)
    @Comment(comment = "投了多少钱")
    private Double money;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnterpriseAdInfo that = (EnterpriseAdInfo) o;
        return java.util.Objects.equals(id, that.id) &&
                java.util.Objects.equals(enterpriseBasicInfo, that.enterpriseBasicInfo) &&
                java.util.Objects.equals(productBasicInfo, that.productBasicInfo) &&
                java.util.Objects.equals(marketBasicInfo, that.marketBasicInfo) &&
                java.util.Objects.equals(year, that.year) &&
                java.util.Objects.equals(money, that.money);
    }

    @Override
    public int hashCode() {

        return java.util.Objects.hash(id, enterpriseBasicInfo, productBasicInfo, marketBasicInfo, year, money);
    }
}
