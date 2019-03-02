package edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity;


import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class IsoDevelopInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//代理主键

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    private IsoBasicInfo isoBasicInfo;//ISO认证的基本信息

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    private EnterpriseBasicInfo enterpriseBasicInfo;//哪一个企业的ISO认证信息

    @Basic
    private Integer developBeginPeriod ;//认证开始的周期

    @Basic
    private Integer developEndPeriod ;//认证完成的周期

    @Basic
    private Integer researchedPeriod ;//已经认证了多少个周期

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private IsoStatusEnum isoStatus;//当前认证状态（默认同iso基本认证中的初始认证状态，需要手动控制）

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IsoDevelopInfo that = (IsoDevelopInfo) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(isoBasicInfo, that.isoBasicInfo) &&
                Objects.equals(enterpriseBasicInfo, that.enterpriseBasicInfo) &&
                Objects.equals(developBeginPeriod, that.developBeginPeriod) &&
                Objects.equals(developEndPeriod, that.developEndPeriod) &&
                Objects.equals(researchedPeriod, that.researchedPeriod) &&
                isoStatus == that.isoStatus;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, isoBasicInfo, enterpriseBasicInfo, developBeginPeriod, developEndPeriod, researchedPeriod, isoStatus);
    }
}
