package edu.cqupt.mislab.erp.game.manage.model.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
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

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/5 19:19
     * @Description: 企业成员数据信息表
     **/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个企业")
    private EnterpriseBasicInfo enterprise;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个学生")
    private UserStudentInfo studentInfo;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "成员企业角色")
    private String gameEnterpriseRole;

    @Basic
    @Comment(comment = "成员的贡献率，按100分计算，只有企业CEO才可以进行修改")
    private Integer gameContributionRate;

    @Basic
    @Comment(comment = "该成员在这场比赛中的心得体会，支持实验心得个性化")
    private String gameExperience;

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
}