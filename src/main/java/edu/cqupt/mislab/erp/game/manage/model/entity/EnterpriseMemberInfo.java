package edu.cqupt.mislab.erp.game.manage.model.entity;

import edu.cqupt.mislab.erp.game.compete.basic.Comment;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Objects;

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

    /**
     * todo 检查成员贡献率
     */
    @Min(0)
    @Basic
    @Comment(comment = "成员的贡献率，按100分计算，只有企业CEO才可以进行修改")
    private Integer gameContributionRate;

    @Column
    @Comment(comment = "成员心得体会的存储路径的键值；" +
            "文件存储路径由文件所在空间与键值共同构成，展示给前端时需要通过FileUtil拼接")
    private String reportKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnterpriseMemberInfo that = (EnterpriseMemberInfo) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(enterpriseBasicInfo, that.enterpriseBasicInfo) &&
                Objects.equals(userStudentInfo, that.userStudentInfo) &&
                Objects.equals(gameEnterpriseRole, that.gameEnterpriseRole) &&
                Objects.equals(gameContributionRate, that.gameContributionRate) &&
                Objects.equals(reportKey, that.reportKey);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, enterpriseBasicInfo, userStudentInfo, gameEnterpriseRole, gameContributionRate, reportKey);
    }
}