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
    private ProdlineBasicInfo prodlineBasicInfo;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "该生产线存在于哪个厂房当中")
    private FactoryHoldingInfo factoryHoldingInfo;


    /**
     * @author yuanyiwen
     * @description todo 生产线模块重构（划掉）重写
     *
     *      考虑将 HoldingInfo 和 ProduceInfo 整合的可能性及必要性
     *
     *      生产线变动情况 ：
     *          新建、售卖、没了    所以要考虑一下必要性和整合所花费的代价是否值得
     *
     *
     *      如果能处理成厂房那样，就基本和厂房相同
     *      如果权衡之后决定不处理，就到时候再造作
     *
     * @date 18:41 2019/5/6
     **/


    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Comment(comment = "生产线的拥有状态")
    private ProdlineHoldingStatus prodlineHoldingStatus;

}
