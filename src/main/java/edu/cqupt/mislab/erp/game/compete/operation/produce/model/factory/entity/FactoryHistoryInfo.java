package edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author yuanyiwen
 * @create 2019-06-01 20:18
 * @description
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class FactoryHistoryInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪个企业")
    private EnterpriseBasicInfo enterpriseBasicInfo;

    @Min(1)
    @Comment(comment = "哪个周期")
    private int period;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "是对哪个厂房进行的操作")
    private FactoryBasicInfo factoryBasicInfo;

    @NotNull
    @Comment(comment = "具体操作")
    private String operation;

    @Basic
    @Comment(comment = "对厂房操作所造成的厂房数量变动")
    private int mount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FactoryHistoryInfo that = (FactoryHistoryInfo) o;
        return period == that.period &&
                mount == that.mount &&
                Objects.equals(id, that.id) &&
                Objects.equals(enterpriseBasicInfo, that.enterpriseBasicInfo) &&
                Objects.equals(factoryBasicInfo, that.factoryBasicInfo) &&
                Objects.equals(operation, that.operation);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, enterpriseBasicInfo, period, factoryBasicInfo, operation, mount);
    }
}
