package edu.cqupt.mislab.erp.game.compete.operation.order.model.entity;

import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
@Entity @Table
public class OrderModelInfoBasicInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//代理主键

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private GameBasicInfo gameBasicInfo;//那一场比赛

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private MarketBasicInfo marketBasicInfo;//那一场市场

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private ProductBasicInfo productBasicInfo;//哪一个产品

    @Basic(optional = false)
    private Integer year;//表示订单是在第几年上才可以供用户选择

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;

        if(o == null||getClass() != o.getClass())
            return false;

        OrderModelInfoBasicInfo that = (OrderModelInfoBasicInfo) o;

        return new EqualsBuilder().append(id,that.id).isEquals();
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17,37).append(id).toHashCode();
    }
}
