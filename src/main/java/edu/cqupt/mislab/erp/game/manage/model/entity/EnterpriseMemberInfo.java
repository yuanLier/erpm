package edu.cqupt.mislab.erp.game.manage.model.entity;

import com.google.common.base.Objects;
import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @Author: chuyunfei
 * @Date: 2019/3/5 19:19
 * @Description: 企业成员数据信息表
 */

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
    @Comment(comment = "代理主键")
    private Long id;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个企业")
    private EnterpriseBasicInfo enterpriseBasicInfo;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,updatable = false)
    @Comment(comment = "哪一个学生")
    private UserStudentInfo userStudentInfo;

    @Column(nullable = false,updatable = false)
    @Comment(comment = "成员企业角色")
    private String gameEnterpriseRole;

    @Min(0)
    @Basic
    @Comment(comment = "成员的贡献率，按100分计算，只有企业CEO才可以进行修改")
    private Integer gameContributionRate;

    @Basic
    @Comment(comment = "该成员在这场比赛中的心得体会，支持实验心得个性化")
    private String gameExperience;

    @Override
    public boolean equals(Object o){
        if(this == o) {
            return true;
        }
        if(o == null||getClass() != o.getClass()) {
            return false;
        }
        EnterpriseMemberInfo that = (EnterpriseMemberInfo) o;
        return Objects.equal(id,that.id)&&Objects.equal(gameEnterpriseRole,that.gameEnterpriseRole);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id,gameEnterpriseRole);
    }
}