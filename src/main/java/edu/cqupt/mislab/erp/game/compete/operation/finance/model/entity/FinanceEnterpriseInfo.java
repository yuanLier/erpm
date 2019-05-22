package edu.cqupt.mislab.erp.game.compete.operation.finance.model.entity;

import edu.cqupt.mislab.erp.commons.validators.annotations.DoubleMin;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yuanyiwen
 * @create 2019-05-15 11:41
 * @description 记录企业的账户余额
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class FinanceEnterpriseInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪个企业的账户余额")
    private EnterpriseBasicInfo enterpriseBasicInfo;

    @DoubleMin(0.01)
    @Column(updatable = false)
    @Comment(comment = "当前账户余额")
    private double currentAccount;

    @NotNull
    @Column(updatable = false)
    @Comment(comment = "造成改变的操作")
    private String changeOperating;

    @DoubleMin(0.01)
    @Column(updatable = false)
    @Comment(comment = "造成改变的金额")
    private double changeAmount;

    @NotNull
    @Basic
    @Comment(comment = "是否为当前（最新）余额")
    private boolean current;

}
