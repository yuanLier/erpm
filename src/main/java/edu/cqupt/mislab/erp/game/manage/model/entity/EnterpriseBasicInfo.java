package edu.cqupt.mislab.erp.game.manage.model.entity;

import com.google.common.base.Objects;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chuyunfei
 * @Date: 2019/3/5 19:15
 * @Description: 企业的基本数据信息
 */

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class EnterpriseBasicInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "这个企业在哪一场比赛里面")
    private GameBasicInfo gameBasicInfo;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "企业的名称")
    private String enterpriseName;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "这个企业的创建者，创建者才具有删除成员的权利和删除企业的权利")
    private UserStudentInfo userStudentInfo;

    @Min(1)
    @Column(nullable = false,updatable = false)
    @Comment(comment = "企业允许的最大成员个数")
    private Integer enterpriseMaxMemberNumber;

    @Min(1)
    @Basic(optional = false)
    @Comment(comment = "这个企业所处于的周期")
    private Integer enterpriseCurrentPeriod;

    @Basic(optional = false)
    @Comment(comment = "企业是否会投广告")
    private Boolean advertising;

    @Basic(optional = false)
    @Comment(comment = "企业是否投过了广告")
    private Boolean advertisingCost;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Comment(comment = "企业所处于的一个状态")
    private EnterpriseStatusEnum enterpriseStatus;

    @Basic(optional = false)
    @Comment(comment = "成员的贡献度是否已经被确定，确定了贡献度才可以进行实验报告的打印")
    private Boolean gameContributionRateSure;

    @OneToMany(mappedBy = "enterpriseBasicInfo",cascade = CascadeType.ALL)
    @Comment(comment = "企业的成员信息集合")
    private List<EnterpriseMemberInfo> enterpriseMemberInfos = new ArrayList<>();

    @Override
    public boolean equals(Object o){
        if(this == o) {
            return true;
        }
        if(o == null||getClass() != o.getClass()) {
            return false;
        }
        EnterpriseBasicInfo that = (EnterpriseBasicInfo) o;
        return Objects.equal(id,that.id)&&Objects.equal(enterpriseName,that.enterpriseName)&&Objects.equal(enterpriseMaxMemberNumber,that.enterpriseMaxMemberNumber)&&Objects.equal(enterpriseCurrentPeriod,that.enterpriseCurrentPeriod)&&Objects.equal(advertising,that.advertising)&&Objects.equal(advertisingCost,that.advertisingCost)&& enterpriseStatus == that.enterpriseStatus &&Objects.equal(gameContributionRateSure,that.gameContributionRateSure);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id,enterpriseName,enterpriseMaxMemberNumber,enterpriseCurrentPeriod,advertising,advertisingCost, enterpriseStatus,gameContributionRateSure);
    }
}