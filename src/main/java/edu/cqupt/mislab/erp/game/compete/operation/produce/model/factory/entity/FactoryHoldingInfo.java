package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;

import javax.persistence.*;

/**
 * @author yuanyiwen
 * @create 2019-03-10 23:33
 * @description
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class FactoryHoldingInfo {
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
}
