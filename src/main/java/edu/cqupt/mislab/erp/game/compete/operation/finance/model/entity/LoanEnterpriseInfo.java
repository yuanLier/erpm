package edu.cqupt.mislab.erp.game.compete.operation.finance.model.entity;

import edu.cqupt.mislab.erp.commons.validators.annotations.DoubleMin;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yuanyiwen
 * @create 2019-07-11 15:46
 * @description 记录企业的贷款情况
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class LoanEnterpriseInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪种贷款")
    private GameLoanBasicInfo gameLoanBasicInfo;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪个企业贷的款")
    private EnterpriseBasicInfo enterpriseBasicInfo;

    @DoubleMin(0.01)
    @Column(updatable = false)
    @Comment(comment = "贷款金额")
    private double loanAmount;

    @Min(1)
    @Basic
    @Comment(comment = "开始贷款的周期")
    private int beginPeriod;

    @Basic
    @Comment(comment = "还款的周期")
    private Integer endPeriod;

    @Basic
    @Comment(comment = "是否还款")
    private boolean repaid;

}
