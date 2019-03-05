package edu.cqupt.mislab.erp.game.compete.operation.order.model.entity;

import com.google.common.base.Objects;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class EnterpriseAdInfo implements Serializable {

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/5 13:02
     * @Description: 企业广告信息数据表
     **/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(updatable = false,nullable = false)
    @Comment(comment = "哪一个企业")
    private EnterpriseBasicInfo enterpriseBasicInfo;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个产品")
    private ProductBasicInfo productBasicInfo;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个市场")
    private MarketBasicInfo marketBasicInfo;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "那一年投的广告费")
    private int year;

    @Column(nullable = false,updatable = false,columnDefinition = "double(10,2) default 1.00")
    @Comment(comment = "多少钱")
    private double money;

    @Column(nullable = false,updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Comment(comment = "哪一个时间投的")
    private Date timeStamp;

    @Column(columnDefinition = "int default 0")
    @Comment(comment = "该订单已经选择已经多少轮")
    private int frequency;

    @Column(columnDefinition = "tinyint(1) default 0")
    @Comment(comment = "该订单是否退出排序")
    private boolean finished;

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null||getClass() != o.getClass())
            return false;
        EnterpriseAdInfo that = (EnterpriseAdInfo) o;
        return year == that.year&&Double.compare(that.money,money) == 0&&frequency == that.frequency&&finished == that.finished&&Objects.equal(id,that.id)&&Objects.equal(timeStamp,that.timeStamp);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id,year,money,timeStamp,frequency,finished);
    }
}
