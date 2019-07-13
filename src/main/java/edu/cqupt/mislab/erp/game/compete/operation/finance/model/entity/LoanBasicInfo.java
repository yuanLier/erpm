package edu.cqupt.mislab.erp.game.compete.operation.finance.model.entity;

import edu.cqupt.mislab.erp.commons.validators.annotations.DoubleMin;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author yuanyiwen
 * @create 2019-07-11 15:40
 * @description 贷款基本信息
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class LoanBasicInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @Column(nullable = false, updatable = false)
    @Comment(comment = "贷款类型")
    private String loanType;

    @DoubleMin(0.0001)
    @Column(nullable = false, updatable = false)
    @Comment(comment = "年利率")
    private double loanRate;

    @Comment(comment = "贷款期限，即最长允许在多少周期后还款")
    private int maxDuration;

    @Comment(comment = "该条设置是否被启用")
    private boolean enable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanBasicInfo that = (LoanBasicInfo) o;
        return Double.compare(that.loanRate, loanRate) == 0 &&
                maxDuration == that.maxDuration &&
                enable == that.enable &&
                Objects.equals(id, that.id) &&
                Objects.equals(loanType, that.loanType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, loanType, loanRate, maxDuration, enable);
    }
}
