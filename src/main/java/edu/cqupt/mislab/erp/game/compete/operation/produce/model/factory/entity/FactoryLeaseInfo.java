package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import lombok.*;

import javax.persistence.*;

/**
 * @author yuanyiwen
 * @create 2019-03-10 23:37
 * @description 厂房的租赁信息表
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class FactoryLeaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个厂房信息")
    private FactoryHoldingInfo factoryHoldingInfo;

    @Basic
    @Comment(comment = "开始租赁的周期数")
    private Integer beginPeriod;

    @Basic
    @Comment(comment = "租赁完毕的周期数")
    private Integer endPeriod;

    @Basic
    @Comment(comment = "已经租赁了多少个周期数，初始默认为0")
    private Integer developedPeriod;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Comment(comment = "租赁的状态，需要在进行比赛初始化时手动进行转换设置")
    private FactoryLeaseStatus factoryLeaseStatus;
}
