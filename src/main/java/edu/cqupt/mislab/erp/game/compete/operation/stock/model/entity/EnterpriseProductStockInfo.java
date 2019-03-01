//package edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity;
//
//import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
//import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
//import lombok.*;
//import org.apache.commons.lang3.builder.EqualsBuilder;
//import org.apache.commons.lang3.builder.HashCodeBuilder;
//
//import javax.persistence.*;
//
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table
//public class EnterpriseProductStockInfo {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;//代理主键
//
//    @ManyToOne(fetch = FetchType.LAZY,optional = false)
//    @JoinColumn(nullable = false,updatable = false)
//    private EnterpriseBasicInfo enterpriseBasicInfo;//哪一个企业
//
//    @ManyToOne(fetch = FetchType.LAZY,optional = false)
//    @JoinColumn(nullable = false,updatable = false)
//    private ProductBasicInfo productBasicInfo;//哪一种产品
//
//    @Basic(optional = false)
//    private Integer productNumber;//产品的数量
//
//    @Basic(optional = false)
//    private Integer period;//哪一个周期的数据
//
//    @Override
//    public boolean equals(Object o){
//        if(this == o)
//            return true;
//
//        if(o == null||getClass() != o.getClass())
//            return false;
//
//        EnterpriseProductStockInfo that = (EnterpriseProductStockInfo) o;
//
//        return new EqualsBuilder().append(id,that.id).isEquals();
//    }
//
//    @Override
//    public int hashCode(){
//        return new HashCodeBuilder(17,37).append(id).toHashCode();
//    }
//}
