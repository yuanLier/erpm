package edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity;

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
 * @Date: 2019/3/4 20:33
 * @Description: 用于记录应用的ISO基本认证信息，比赛初始化是获取最新的一条数据，而修改将使用到历史数据。
 * 在这张表里面没有一条可用的认证信息的时候比赛将无法进行初始化操作。
 **/

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class IsoBasicInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @Size(min = 1, max = 50)
    @NotNull
    @Column(nullable = false, updatable = false)
    @Comment(comment = "ISO认证资格的名称，这个名称用于区分不同ISO认证信息，相同的isoName非最大值ID数据为历史信息")
    private String isoName;

    @Min(1)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "ISO认证信息需要认证的周期，该值必须大于等于1")
    private int isoResearchPeriod;

    @DoubleMin(0.01)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "ISO认证过程中每个周期需要支付的费用，该值必须大于0")
    private double isoResearchCost;

    @DoubleMin(0.01)
    @Column(nullable = false, updatable = false,columnDefinition = "double(10,2) default 1.00")
    @Comment(comment = "ISO认证完成后维持该认证每个周期需要支付的费用，该值必须大于0")
    private double  isoMaintainCost;

    @DoubleMin(0.01)
    @Column(nullable = false, updatable = false,columnDefinition = "double(10,2) default 1.00")
    @Comment(comment = "该认证对订单的每一个产品单价的影响程度，该值必须大于0")
    private double extraValue;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "该认证的默认初始状态，标识该ISO在比赛初始化时将以什么状态出现在比赛默认信息里面，只能是研发成功或者未研发")
    private IsoStatusEnum isoStatus;

    @NotNull
    @Basic(optional = false)
    @Comment(comment = "该数据是否被启用，当前最新数据是启用，所有的历史数据均为未启用，必须保证同一个ISO认证信息最多只有一个Enable = true")
    private boolean enable;

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null||getClass() != o.getClass())
            return false;
        IsoBasicInfo that = (IsoBasicInfo) o;
        return isoResearchPeriod == that.isoResearchPeriod&&Double.compare(that.isoResearchCost,isoResearchCost) == 0&&Double.compare(that.isoMaintainCost,isoMaintainCost) == 0&&Double.compare(that.extraValue,extraValue) == 0&&enable == that.enable&&Objects.equal(id,that.id)&&Objects.equal(isoName,that.isoName)&&isoStatus == that.isoStatus;
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id,isoName,isoResearchPeriod,isoResearchCost,isoMaintainCost,extraValue,isoStatus,enable);
    }
}