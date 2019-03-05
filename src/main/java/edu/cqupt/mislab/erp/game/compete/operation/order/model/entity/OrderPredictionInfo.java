package edu.cqupt.mislab.erp.game.compete.operation.order.model.entity;

import com.google.common.base.Objects;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class OrderPredictionInfo implements Serializable {

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/5 12:52
     * @Description: 订单的预测信息
     **/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一场比赛")
    private GameBasicInfo gameBasicInfo;

    @ManyToOne
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个产品")
    private ProductBasicInfo productBasicInfo;

    @ManyToOne
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个市场")
    private MarketBasicInfo marketBasicInfo;

    @Column(nullable = false,updatable = false,columnDefinition = "double(10,2) default 1.00")
    @Comment(comment = "产品价格")
    private double price;

    @Basic(optional = false)
    @Comment(comment = "市场需求量")
    private int mount;

    @Basic(optional = false)
    @Comment(comment = "那一年的预测信息")
    private int year;

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
