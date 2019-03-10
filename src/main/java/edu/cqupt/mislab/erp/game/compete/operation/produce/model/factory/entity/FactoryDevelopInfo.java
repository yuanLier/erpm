package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;

import javax.persistence.*;


/**
 * @author yuanyiwen
 * @create 2019-03-09 16:37
 * @description 企业厂房的修建情况表
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class FactoryDevelopInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个厂房信息")
    private FactoryHoldingInfo factoryHoldingInfo;

    @Basic
    @Comment(comment = "开始修建的周期数")
    private Integer beginPeriod;

    @Basic
    @Comment(comment = "修建完毕的周期数")
    private Integer endPeriod;

    @Basic
    @Comment(comment = "已经修建了多少个周期数，初始默认为0")
    private Integer developedPeriod;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Comment(comment = "修建的状态，需要在进行比赛初始化时手动进行转换设置")
    private FactoryDevelopStatus factoryDevelopStatus;
}
