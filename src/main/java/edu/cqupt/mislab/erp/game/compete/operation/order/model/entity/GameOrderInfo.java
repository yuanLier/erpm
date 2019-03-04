package edu.cqupt.mislab.erp.game.compete.operation.order.model.entity;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
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
public class GameOrderInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//代理主键

    @ManyToOne
    @JoinColumn(nullable = false,updatable = false)
    private GameBasicInfo gameBasicInfo;//哪一场比赛

    @ManyToOne
    @JoinColumn(nullable = false,updatable = false)
    private ProductBasicInfo productBasicInfo;//哪一个产品

    @ManyToOne
    @JoinColumn(nullable = false,updatable = false)
    private MarketBasicInfo marketBasicInfo;//哪一个市场

    @Basic(optional = false)
    private Integer productNumber;//产品数量

    @Basic(optional = false)
    private Double price;//产品单价

    @Basic(optional = false)
    private Integer deliveryPeriod;//需要的交货的日期，为总第几期

    @Basic(optional = false)
    private Integer moneyTime;//订单交货后，需要几个账期，货款可以到账

    @Basic(optional = false)
    private Double penalPercent;//违约金比率，超过1期交纳1期的违约金，超过多期，进行累计

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private IsoBasicInfo isoBasicInfo;//所需要的质量认证信息

    @Basic(optional = false)
    private Integer year;//那一年的订单

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private EnterpriseBasicInfo enterpriseBasicInfo;//该订单被那个企业选择

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;

        if(o == null||getClass() != o.getClass())
            return false;

        GameOrderInfo that = (GameOrderInfo) o;

        return new EqualsBuilder().append(id,that.id).isEquals();
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17,37).append(id).toHashCode();
    }
}
