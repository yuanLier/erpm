package edu.cqupt.mislab.erp.game.manage.model.entity;

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
    @Comment(comment = "这个企业所处于的周期；企业破产后该字段值即为破产周期")
    private Integer enterpriseCurrentPeriod;

    @Basic(optional = false)
    @Comment(comment = "企业是否会投广告，每年更新")
    private Boolean advertising;

    @Basic(optional = false)
    @Comment(comment = "企业是否投过了广告，每年更新")
    private Boolean finishAdvertising;

    @Min(0)
    @Basic
    @Comment(comment = "企业订单的选取顺序，每年更新")
    private Integer sequence;

    @Basic
    @Comment(comment = "企业是否退出订单会，每年更新")
    private Boolean finishChoice;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnterpriseBasicInfo that = (EnterpriseBasicInfo) o;
        return java.util.Objects.equals(id, that.id) &&
                java.util.Objects.equals(gameBasicInfo, that.gameBasicInfo) &&
                java.util.Objects.equals(enterpriseName, that.enterpriseName) &&
                java.util.Objects.equals(userStudentInfo, that.userStudentInfo) &&
                java.util.Objects.equals(enterpriseMaxMemberNumber, that.enterpriseMaxMemberNumber) &&
                java.util.Objects.equals(enterpriseCurrentPeriod, that.enterpriseCurrentPeriod) &&
                java.util.Objects.equals(advertising, that.advertising) &&
                java.util.Objects.equals(finishAdvertising, that.finishAdvertising) &&
                java.util.Objects.equals(finishChoice, that.finishChoice) &&
                enterpriseStatus == that.enterpriseStatus &&
                java.util.Objects.equals(gameContributionRateSure, that.gameContributionRateSure) &&
                java.util.Objects.equals(enterpriseMemberInfos, that.enterpriseMemberInfos);
    }

    @Override
    public int hashCode() {

        return java.util.Objects.hash(id, gameBasicInfo, enterpriseName, userStudentInfo, enterpriseMaxMemberNumber, enterpriseCurrentPeriod, advertising, finishAdvertising, finishChoice, enterpriseStatus, gameContributionRateSure, enterpriseMemberInfos);
    }
}