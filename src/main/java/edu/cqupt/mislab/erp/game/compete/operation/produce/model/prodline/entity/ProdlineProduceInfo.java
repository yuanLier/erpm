package edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author yuanyiwen
 * @create 2019-03-10 17:00
 * @description 生产线的生产情况表
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class ProdlineProduceInfo implements Serializable {
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
    @Comment(comment = "产品的实际需要的生产时间（值为产品的生产时间*生产线的加速时间取整）")
    private Integer produceDuration;

    @Basic
    @Comment(comment = "开始生产的周期数")
    private Integer beginPeriod;

    @Basic
    @Comment(comment = "生产完毕的周期数")
    private Integer endPeriod;

    @Basic
    @Comment(comment = "已经生产了多少个周期数，初始默认为0")
    private Integer producedPeriod;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Comment(comment = "生产的状态，需要在进行比赛初始化时手动进行转换设置")
    private ProdlineProduceStatus prodlineProduceStatus;

}
