package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author yuanyiwen
 * @create 2019-03-21 21:41
 * @description 企业中厂房的拥有情况，包括自建的和租来的
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class FactoryHoldingInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个企业的厂房信息")
    private EnterpriseBasicInfo enterpriseBasicInfo;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "厂房的基本信息")
    private FactoryBasicInfo factoryBasicInfo;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Comment(comment = "厂房的拥有状态，对应到下面三个属性为 ：自建的 / 租赁的")
    private FactoryHoldingStatus factoryHoldingStatus;

    @Basic
    @Comment(comment = "开始建造的周期数 / 开始租赁的周期数")
    private Integer beginPeriod;

    @Basic
    @Comment(comment = "建造花费的总周期数 / 已经租赁的总周期数")
    private Integer developedPeriod;

    @Basic
    @Comment(comment = "该厂房是否可用（true可用false已出售） / 该厂房是否继续租赁（true是false否）")
    private boolean enable;

}
