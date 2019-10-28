package edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

/**
 * @author chuyunfei
 * @description
 **/

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class ProductStockInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个企业")
    private EnterpriseBasicInfo enterpriseBasicInfo;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一种产品")
    private ProductBasicInfo productBasicInfo;

    @Min(0)
    @Basic(optional = false)
    @Comment(comment = "产品的库存数")
    private int productNumber;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductStockInfo that = (ProductStockInfo) o;
        return productNumber == that.productNumber &&
                java.util.Objects.equals(id, that.id) &&
                java.util.Objects.equals(enterpriseBasicInfo, that.enterpriseBasicInfo) &&
                java.util.Objects.equals(productBasicInfo, that.productBasicInfo);
    }

    @Override
    public int hashCode() {

        return java.util.Objects.hash(id, enterpriseBasicInfo, productBasicInfo, productNumber);
    }
}
