package edu.cqupt.mislab.erp.game.compete.operation.product.model.entity;

import com.google.common.base.Objects;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: chuyunfei
 * @Date: 2019/3/4 22:06
 * @Description: 用于记录每一个企业的产品研发信息
 **/

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class ProductDevelopInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个企业的产品研发信息")
    private EnterpriseBasicInfo enterpriseBasicInfo;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "产品研发的基本信息")
    private ProductBasicInfo productBasicInfo;

    @Basic
    @Comment(comment = "开始研发的周期数")
    private Integer beginPeriod;

    @Basic
    @Comment(comment = "研发完毕的周期数")
    private Integer endPeriod;

    @Basic
    @Comment(comment = "已经研发了多少个周期数")
    private Integer developedPeriod;

    @NotNull
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Comment(comment = "研发的状态，需要在进行比赛初始化时手动进行转换设置")
    private ProductDevelopStatusEnum productDevelopStatus;

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null||getClass() != o.getClass())
            return false;
        ProductDevelopInfo that = (ProductDevelopInfo) o;
        return Objects.equal(id,that.id)&&Objects.equal(beginPeriod,that.beginPeriod)&&Objects.equal(endPeriod,that.endPeriod)&&Objects.equal(developedPeriod,that.developedPeriod)&&productDevelopStatus == that.productDevelopStatus;
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id,beginPeriod,endPeriod,developedPeriod,productDevelopStatus);
    }
}
