package edu.cqupt.mislab.erp.game.compete.operation.order.model.entity;

import com.google.common.base.Objects;
import edu.cqupt.mislab.erp.commons.validators.annotations.DoubleMin;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

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
    @Comment(comment = "那一年投的广告费")
    private Integer year;

    @DoubleMin(0.01D)
    @Column(nullable = false,updatable = false)
    @Comment(comment = "多少钱")
    private Double money;

    @Column(nullable = false,updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Comment(comment = "哪一个时间投的")
    private Date timeStamp;

    @Min(0)
    @Column(columnDefinition = "int default 0")
    @Comment(comment = "该订单已经选择已经多少轮")
    private Integer frequency;

    @Column(nullable = false)
    @Comment(comment = "该订单是否退出排序")
    private Boolean finished;

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null||getClass() != o.getClass())
            return false;
        EnterpriseAdInfo that = (EnterpriseAdInfo) o;
        return frequency == that.frequency&&finished == that.finished&&Objects.equal(id,that.id)&&Objects.equal(year,that.year)&&Objects.equal(money,that.money)&&Objects.equal(timeStamp,that.timeStamp);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id,year,money,timeStamp,frequency,finished);
    }
}
