package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


/**
 * @author yuanyiwen
 * @create 2019-03-09 16:37
 * @description 企业厂房的建造情况临时记录表，建造完成后即转入holding表
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class FactoryDevelopInfo implements Serializable {

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
    private GameFactoryBasicInfo factoryBasicInfo;

    @Basic
    @Comment(comment = "开始建造的周期数，可以为空")
    private Integer beginPeriod;

    @Basic
    @Comment(comment = "建造完毕的周期数，可以为空")
    private Integer endPeriod;

    @Basic
    @Comment(comment = "已经建造了多少个周期数，初始默认为0")
    private Integer developedPeriod;

    @Basic
    @Comment(comment = "厂房的建造状态，true为建造中，false为暂停建造")
    private boolean enable;

    @Basic
    @Comment(comment = "是否修建完成")
    private boolean developed;
}
