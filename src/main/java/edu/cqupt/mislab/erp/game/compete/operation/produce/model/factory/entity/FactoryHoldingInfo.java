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

    /**
     * @author yuanyiwen
     * @description todo FactoryHoldingInfo重构规划
     *
     *      beginPeriod 语义修改为 ：建造完成的周期数 / 开始租赁的周期数
     *      endPeriod   语义修改为 ：确认出售的周期数 / 停止租赁的周期数（默认均为null）
     *
     *      用户选择停止租赁，过段时间又继续租赁了的话，就会重置开始租赁的周期数
     *
     *      在每个时间推进时查询该周期的变动情况 还是要新建一张表的
     *          通过 findbybeginPeriod=currentPeriod 查询增加的（修建完成/开始租）
     *          通过 findbyendPeriod=currentPeriod 查询减少的厂房（出售/停租）
     *
     * @date 17:14 2019/5/6
     **/

    @Basic
    @Comment(comment = "开始建造的周期数 / 开始租赁的周期数")
    private Integer beginPeriod;

    @Basic
    @Comment(comment = "完成建造的周期数 / 已经租赁的总周期数")
    private Integer endPeriod;

    @Basic
    @Comment(comment = "该厂房是否可用（true可用false已出售） / 该厂房是否继续租赁（true是false否）")
    private boolean enable;

}
