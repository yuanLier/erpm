package edu.cqupt.mislab.erp.game.compete.operation.order.model.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
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

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/5 13:13
     * @Description: 用于记录所有的订单信息
     **/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一场比赛")
    private GameBasicInfo gameBasicInfo;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个产品")
    private ProductBasicInfo productBasicInfo;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个市场")
    private MarketBasicInfo marketBasicInfo;

    @Basic(optional = false)
    @Comment(comment = "产品数量")
    private int productNumber;

    @Column(nullable = false,updatable = false,columnDefinition = "double(10,2) default 1.00")
    @Comment(comment = "产品单价")
    private double price;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "需要的交货的日期，为总第几期")
    private int deliveryPeriod;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "订单交货后，需要几个账期，货款可以到账")
    private int moneyTime;

    @Column(nullable = false,updatable = false,columnDefinition = "double(10,2) default 1.00")
    @Comment(comment = "违约金比率，超过1期交纳1期的违约金，超过多期，进行累计")
    private double penalPercent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @Comment(comment = "所需要的质量认证信息")
    private IsoBasicInfo isoBasicInfo;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "那一年的订单")
    private int year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @Comment(comment = "该订单被那个企业选择")
    private EnterpriseBasicInfo enterpriseBasicInfo;

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