package edu.cqupt.mislab.erp.game.compete.operation.market.model.entity;

import com.google.common.base.Objects;
import edu.cqupt.mislab.erp.commons.validators.annotations.DoubleMin;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * @Author: chuyunfei
 * @Date: 2019/3/4 21:11
 * @Description: 应用市场的基本数据表，当该表不存在可用的数据时将无法正常创建比赛
 **/

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class MarketBasicInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @Size(min = 1, max = 50)
    @NotNull
    @Column(nullable = false,updatable = false)
    @Comment(comment = "市场的名称，相同市场名称的数据中enable为false的为历史信息")
    private String marketName;

    @Min(1)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "完成市场开拓的周期数，该值必须大于1")
    private int marketResearchPeriod;

    @DoubleMin(0.01)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "在市场开拓过程中，每个周期需要支付的费用，该值必须大于0")
    private double marketResearchCost;

    @DoubleMin(0.01)
    @Column(nullable = false, updatable = false,columnDefinition = "double(10,2) default 1.00")
    @Comment(comment = "市场开拓完成后，维持该市场每个周期需要支付的费用，该值必须大于0")
    private double marketMaintainCost;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "该认证的默认初始状态，该值只能是已研发或者未研发")
    private MarketStatusEnum marketStatus;

    @NotNull
    @Basic(optional = false)
    @Comment(comment = "该数据是否被启用，当前最新数据是启用，所有的历史数据均为未启用，必须保证同一个市场信息最多只有一个Enable = true")
    private boolean enable;

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null||getClass() != o.getClass())
            return false;
        MarketBasicInfo that = (MarketBasicInfo) o;
        return marketResearchPeriod == that.marketResearchPeriod&&Double.compare(that.marketResearchCost,marketResearchCost) == 0&&Double.compare(that.marketMaintainCost,marketMaintainCost) == 0&&enable == that.enable&&Objects.equal(id,that.id)&&Objects.equal(marketName,that.marketName)&&marketStatus == that.marketStatus;
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id,marketName,marketResearchPeriod,marketResearchCost,marketMaintainCost,marketStatus,enable);
    }
}