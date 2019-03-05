package edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity;

import com.google.common.base.Objects;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class EnterpriseProductStockInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个企业")
    private EnterpriseBasicInfo enterpriseBasicInfo;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一种产品")
    private ProductBasicInfo productBasicInfo;

    @Basic(optional = false)
    @Comment(comment = "产品的数量")
    private int productNumber;

    @Basic(optional = false)
    @Comment(comment = "哪一个周期的数据")
    private int period;

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null||getClass() != o.getClass())
            return false;
        EnterpriseProductStockInfo that = (EnterpriseProductStockInfo) o;
        return productNumber == that.productNumber&&period == that.period&&Objects.equal(id,that.id)&&Objects.equal(enterpriseBasicInfo,that.enterpriseBasicInfo)&&Objects.equal(productBasicInfo,that.productBasicInfo);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id,enterpriseBasicInfo,productBasicInfo,productNumber,period);
    }
}
