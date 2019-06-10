package edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.MaterialBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yuanyiwen
 * @create 2019-03-31 22:47
 * @description 原材料采购信息表
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class MaterialOrderInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个企业")
    private EnterpriseBasicInfo enterpriseBasicInfo;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一种原料")
    private MaterialBasicInfo materialBasicInfo;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一种运输方式")
    private GameTransportBasicInfo transportMethod;

    @Min(1)
    @Basic(optional = false)
    @Comment(comment = "该种原料的采购数量")
    private int purchaseNumber;

    @Min(1)
    @Basic(optional = false)
    @Comment(comment = "采购时间")
    private int purchaseTime;

    @Min(1)
    @Basic
    @Comment(comment = "开始运货的时间")
    private Integer transportTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    @Comment(comment = "原材料运送状态")
    private TransportStatusEnum transportStatus;
}
