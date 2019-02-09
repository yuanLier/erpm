package edu.cqupt.mislab.erp.game.manage.model.entity;

import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
    private Long id;//代理主键

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private GameBasicInfo gameInfo;//这个企业在哪一场比赛里面

    @Column(nullable = false,updatable = false)
    private String enterpriseName;//企业的名称

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    private UserStudentInfo enterpriseCeo;//这个企业的创建者，创建者才具有删除成员的权利和删除企业的权利

    @Basic(optional = false)
    private Integer enterpriseMaxMemberNumber;//企业允许的最大成员个数

    @Basic(optional = false)
    private Integer enterpriseCurrentPeriod;//这个企业所处于的周期

    @Basic(optional = false)
    private Boolean enterpriseCostAd;//企业是否该年已经投过广告了，0标识没有，1标识投过了

    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    private EnterpriseStatus enterpriseStatus;//企业所处于的一个状态

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "enterprise")
    private Set<EnterpriseMemberInfo> memberInfos = new HashSet<>();//企业里面的成员信息

    @Basic(optional = false)
    private Boolean gameContributionRateSure;//成员的贡献度是否已经被确定，确定了贡献度才可以进行实验报告的打印

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;

        if(o == null||getClass() != o.getClass())
            return false;

        EnterpriseBasicInfo that = (EnterpriseBasicInfo) o;

        return new EqualsBuilder().append(id,that.id).isEquals();
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17,37).append(id).toHashCode();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "基本VO对象")
    public static class EnterpriseGameInfoVo {
        @ApiModelProperty("代理主键")
        Long id;

        @ApiModelProperty("这个企业在哪一场比赛里面")
        Long gameId;

        @ApiModelProperty("企业的名称")
        String enterpriseName;

        @ApiModelProperty("这个企业的创建者，创建者才具有删除成员的权利和删除企业的权利")
        Long enterpriseCeoId;

        @ApiModelProperty("创建者的姓名")
        String enterpriseCeoName;

        @ApiModelProperty("企业所允许的最大的人员个数，防止过多的成员主动加入企业但是 这个数值必须小于等于系统的企业最大人员个数，大于时将直接使用系统默认最大个数")
        Integer enterpriseMaxMemberNumber;

        @ApiModelProperty("实时记录各个阶段里面企业成员的个数")
        Integer enterpriseMemberNumber;

        @ApiModelProperty("这个企业所处于的周期")
        Integer enterpriseCurrentPeriod;

        @ApiModelProperty("当前企业处于那一年")
        Integer enterpriseCurrentYear;

        @ApiModelProperty("企业的某一个开始的年都需要先投广告才能够继续进行操作,0标识不是新的一年，1标识是")
        Boolean enterpriseNewYear;

        @ApiModelProperty("企业是否该年已经投过广告了，0标识没有，1标识投过了")
        Boolean enterpriseCostAd;

        @ApiModelProperty("企业所处于的一个状态，0标识企业创建并等待成员加入状态，1标识企业准备开始比赛， 2标识企业正常运营，3标识企业破产，4标识企业正常完成所有的周期完结")
        Integer enterpriseStatus;

        @ApiModelProperty("成员的贡献度是否已经被确定，确定了贡献度才可以进行实验报告的打印")
        Boolean gameContributionRateSure;
    }
}