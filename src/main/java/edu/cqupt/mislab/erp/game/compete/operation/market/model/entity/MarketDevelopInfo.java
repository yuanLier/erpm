package edu.cqupt.mislab.erp.game.compete.operation.market.model.entity;

import com.google.common.base.Objects;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: chuyunfei
 * @Date: 2019/3/4 21:21
 * @Description: 用于存储在比赛过程中每个企业的市场开发信息，在比赛初始化时由MarketBasicInfo生成
 **/

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class MarketDevelopInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "市场开发的基本信息，对应基本市场信息表里面的最新或历史数据")
    private MarketBasicInfo marketBasicInfo;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个企业的市场开发信息")
    private EnterpriseBasicInfo enterpriseBasicInfo;

    @Basic
    @Comment(comment = "市场开始开发的周期，默认为null")
    private Integer developBeginPeriod ;

    @Basic
    @Comment(comment = "市场完成开发的周期，默认为null")
    private Integer developEndPeriod ;

    @Basic
    @Comment(comment = "已经市场开发了多少个周期，默认为开发状态的值为0")
    private Integer researchedPeriod ;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    @Comment(comment = "当前市场的开发状态（默认同基本市场信息的初始状态，需要在比赛初始化时手动控制转换）")
    private MarketStatusEnum marketStatus;

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null||getClass() != o.getClass())
            return false;
        MarketDevelopInfo that = (MarketDevelopInfo) o;
        return Objects.equal(id,that.id)&&Objects.equal(developBeginPeriod,that.developBeginPeriod)&&Objects.equal(developEndPeriod,that.developEndPeriod)&&Objects.equal(researchedPeriod,that.researchedPeriod)&&marketStatus == that.marketStatus;
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id,developBeginPeriod,developEndPeriod,researchedPeriod,marketStatus);
    }
}
