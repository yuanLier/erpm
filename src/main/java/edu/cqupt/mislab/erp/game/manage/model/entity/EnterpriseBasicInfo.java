package edu.cqupt.mislab.erp.game.manage.model.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
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

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/5 19:15
     * @Description: 企业的基本数据信息
     **/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "这个企业在哪一场比赛里面")
    private GameBasicInfo gameInfo;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "企业的名称")
    private String enterpriseName;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "这个企业的创建者，创建者才具有删除成员的权利和删除企业的权利")
    private UserStudentInfo enterpriseCeo;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "企业允许的最大成员个数")
    private int enterpriseMaxMemberNumber;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "这个企业所处于的周期")
    private int enterpriseCurrentPeriod;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "企业是否会投广告")
    private boolean advertising;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "企业是否投过了广告")
    private boolean advertisingCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,updatable = false)
    @Comment(comment = "企业所处于的一个状态")
    private EnterpriseStatus enterpriseStatus;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "成员的贡献度是否已经被确定，确定了贡献度才可以进行实验报告的打印")
    private boolean gameContributionRateSure;

    @OneToMany(mappedBy = "enterprise",cascade = CascadeType.ALL)
    private List<EnterpriseMemberInfo> enterpriseMemberInfos;//企业成员

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
}