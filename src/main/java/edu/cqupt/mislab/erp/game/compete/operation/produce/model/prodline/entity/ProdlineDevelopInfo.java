package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author yuanyiwen
 * @create 2019-03-09 19:35
 * @description 企业生产线的修建情况表
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class ProdlineDevelopInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一条生产线信息")
    private ProdlineHoldingInfo prodlineHoldingInfo;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false)
    @Comment(comment = "生产的是哪一种产品")
    private ProductDevelopInfo productDevelopInfo;

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
    private ProdlineDevelopStatus prodlineDevelopStatus;
}
