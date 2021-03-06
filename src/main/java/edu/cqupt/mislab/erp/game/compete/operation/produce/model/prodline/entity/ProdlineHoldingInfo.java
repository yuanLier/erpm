package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author yuanyiwen
 * @create 2019-03-10 17:09
 * @description 企业生产线的拥有情况
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class ProdlineHoldingInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个企业的生产线信息")
    private EnterpriseBasicInfo enterpriseBasicInfo;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "生产线的基本信息")
    private GameProdlineBasicInfo prodlineBasicInfo;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "该生产线存在于哪个厂房当中")
    private FactoryHoldingInfo factoryHoldingInfo;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Comment(comment = "生产线的拥有状态")
    private ProdlineHoldingStatus prodlineHoldingStatus;

    @Basic
    @Comment(comment = "生产线投入使用的周期数（即安装完成的周期数）")
    private Integer beginPeriod;

    @Basic
    @Comment(comment = "确认出售的周期数")
    private Integer endPeriod;

}
