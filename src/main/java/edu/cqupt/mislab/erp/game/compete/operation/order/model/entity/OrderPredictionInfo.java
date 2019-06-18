package edu.cqupt.mislab.erp.game.compete.operation.order.model.entity;

import com.google.common.base.Objects;
import edu.cqupt.mislab.erp.commons.validators.annotations.DoubleMin;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @Author: chuyunfei
 * @Date: 2019/3/5 12:52
 * @Description: 订单的预测信息
 **/

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class OrderPredictionInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一场比赛")
    private GameBasicInfo gameBasicInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个产品")
    private ProductBasicInfo productBasicInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个市场")
    private MarketBasicInfo marketBasicInfo;

    @DoubleMin(0.01D)
    @Column(nullable = false,columnDefinition = "double(10,2) default 1.00")
    @Comment(comment = "产品价格")
    private Double price;

    @Min(1)
    @Basic(optional = false)
    @Comment(comment = "市场需求量")
    private Integer mount;

    @Min(1)
    @Basic(optional = false)
    @Comment(comment = "哪一年的预测信息")
    private Integer year;

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null||getClass() != o.getClass())
            return false;
        OrderPredictionInfo that = (OrderPredictionInfo) o;
        return Double.compare(that.price,price) == 0&&mount == that.mount&&year == that.year&&Objects.equal(id,that.id);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id,price,mount,year);
    }
}
