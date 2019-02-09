package edu.cqupt.mislab.erp.game.manage.model.entity;

import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.JoinColumnOrFormula;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class EnterpriseMemberInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//代理主键

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false,insertable = false)
    private EnterpriseBasicInfo enterprise;//哪一个企业

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    private UserStudentInfo studentInfo;//哪一个学生

    @Basic
    private String gameEnterpriseRole;//成员企业角色

    @Basic
    private Integer gameContributionRate;//成员的贡献率，按100分计算，只有企业CEO才可以进行修改

    @Basic
    private String gameExperience;//该成员在这场比赛中的心得体会，支持实验心得个性化

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;

        if(o == null||getClass() != o.getClass())
            return false;

        EnterpriseMemberInfo that = (EnterpriseMemberInfo) o;

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
    public static class EnterpriseGameMemberInfoVo {
        @ApiModelProperty("代理主键")
        Long id;

        @ApiModelProperty("那一场比赛")
        Long gameId;

        @ApiModelProperty("哪一个企业")
        Long gameEnterpriseId;

        @ApiModelProperty("哪一个学生")
        Long userId;

        @ApiModelProperty("成员企业角色")
        String gameEnterpriseRole;

        @ApiModelProperty("成员的贡献率，按100分计算，只有企业CEO才可以进行修改")
        Integer gameContributionRate;

        @ApiModelProperty("该成员在这场比赛中的心得体会，支持实验心得个性化")
        String gameExperience;
    }
}